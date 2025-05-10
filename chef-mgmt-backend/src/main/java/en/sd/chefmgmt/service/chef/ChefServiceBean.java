package en.sd.chefmgmt.service.chef;

import java.util.UUID;

import en.sd.chefmgmt.model.dto.CollectionResponseDTO;
import en.sd.chefmgmt.model.dto.chef.ChefFilterDTO;
import en.sd.chefmgmt.model.dto.chef.ChefRequestDTO;
import en.sd.chefmgmt.model.dto.chef.ChefResponseDTO;
import en.sd.chefmgmt.exception.model.DataNotFoundException;
import en.sd.chefmgmt.exception.model.DuplicateDataException;
import en.sd.chefmgmt.exception.model.ExceptionCode;
import en.sd.chefmgmt.model.mapper.ChefEntityMapper;
import en.sd.chefmgmt.model.entity.ChefEntity;
import en.sd.chefmgmt.repository.chef.ChefRepository;
import en.sd.chefmgmt.repository.chef.ChefSpec;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChefServiceBean implements ChefService {

    private final ChefRepository chefRepository;
    private final ChefSpec chefSpec;
    private final ChefEntityMapper chefEntityMapper;

    @Override
    public CollectionResponseDTO<ChefResponseDTO> findAll(ChefFilterDTO filter) {
        Specification<ChefEntity> spec = chefSpec.createSpecification(filter);
        PageRequest page = PageRequest.of(filter.pageNumber(), filter.pageSize());
        Page<ChefEntity> chefs = chefRepository.findAll(spec, page);

        return CollectionResponseDTO.<ChefResponseDTO>builder()
                .pageNumber(page.getPageNumber())
                .pageSize(page.getPageSize())
                .totalPages(chefs.getTotalPages())
                .totalElements(chefs.getTotalElements())
                .elements(chefEntityMapper.convertEntitiesToResponseDtos(chefs.getContent()))
                .build();
    }

    @Override
    public ChefResponseDTO findById(UUID id) {
        ChefEntity chefEntity = chefRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionCode.CHEF_NOT_FOUND, id));

        return chefEntityMapper.convertEntityToResponseDto(chefEntity);
    }

    @Override
    @Transactional
    public ChefResponseDTO save(ChefRequestDTO chefRequestDTO) {
        if (chefRepository.existsByCnp(chefRequestDTO.cnp())) {
            throw new DuplicateDataException(ExceptionCode.CNP_TAKEN, chefRequestDTO.cnp());
        }

        ChefEntity chefToBeAdded = chefEntityMapper.convertRequestDtoToEntity(chefRequestDTO);
        ChefEntity chefAdded = chefRepository.save(chefToBeAdded);

        return chefEntityMapper.convertEntityToResponseDto(chefAdded);
    }

    @Override
    @Transactional
    public ChefResponseDTO update(UUID id, ChefRequestDTO chefRequestDTO) {
        ChefEntity existingChef = chefRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionCode.CHEF_NOT_FOUND, id));
        if (chefRepository.existsByCnpAndIdIsNot(chefRequestDTO.cnp(), id)) {
            throw new DuplicateDataException(ExceptionCode.CNP_TAKEN, chefRequestDTO.cnp());
        }

        chefEntityMapper.updateChefEntity(existingChef, chefRequestDTO);
        ChefEntity chefEntitySaved = chefRepository.save(existingChef);

        return chefEntityMapper.convertEntityToResponseDto(chefEntitySaved);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ChefEntity chefEntity = chefRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionCode.CHEF_NOT_FOUND, id));

        chefRepository.deleteById(chefEntity.getId());
    }
}
