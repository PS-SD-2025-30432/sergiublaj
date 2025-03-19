package en.sd.chefmgmt.service.chef;

import java.util.UUID;

import en.sd.chefmgmt.dto.CollectionResponseDTO;
import en.sd.chefmgmt.dto.chef.ChefFilterDTO;
import en.sd.chefmgmt.dto.chef.ChefRequestDTO;
import en.sd.chefmgmt.dto.chef.ChefResponseDTO;
import en.sd.chefmgmt.exception.DataNotFoundException;
import en.sd.chefmgmt.exception.DuplicateDataException;
import en.sd.chefmgmt.exception.ExceptionCode;
import en.sd.chefmgmt.mapper.ChefMapper;
import en.sd.chefmgmt.model.ChefEntity;
import en.sd.chefmgmt.repository.chef.ChefRepository;
import en.sd.chefmgmt.repository.chef.ChefSpec;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ChefServiceBean implements ChefService {

    private final ChefRepository chefRepository;
    private final ChefSpec chefSpec;
    private final ChefMapper chefMapper;

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
                .elements(chefMapper.convertEntitiesToResponseDtos(chefs.getContent()))
                .build();
    }

    @Override
    public ChefResponseDTO findById(UUID id) {
        ChefEntity chefEntity = chefRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionCode.CHEF_NOT_FOUND, id));

        return chefMapper.convertEntityToResponseDto(chefEntity);
    }

    @Override
    @Transactional
    public ChefResponseDTO save(ChefRequestDTO chefRequestDTO) {
        if (chefRepository.existsByEmail(chefRequestDTO.email())) {
            throw new DuplicateDataException(ExceptionCode.EMAIL_TAKEN, chefRequestDTO.email());
        }

        ChefEntity chefToBeAdded = chefMapper.convertRequestDtoToEntity(chefRequestDTO);
        ChefEntity chefAdded = chefRepository.save(chefToBeAdded);

        return chefMapper.convertEntityToResponseDto(chefAdded);
    }

    @Override
    @Transactional
    public ChefResponseDTO update(UUID id, ChefRequestDTO chefRequestDTO) {
        ChefEntity existingChef = chefRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionCode.CHEF_NOT_FOUND, id));
        if (chefRepository.existsByEmailAndIdIsNot(chefRequestDTO.email(), id)) {
            throw new DuplicateDataException(ExceptionCode.EMAIL_TAKEN, chefRequestDTO.email());
        }

        chefMapper.updateChefEntity(existingChef, chefRequestDTO);
        ChefEntity chefEntitySaved = chefRepository.save(existingChef);

        return chefMapper.convertEntityToResponseDto(chefEntitySaved);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ChefEntity chefEntity = chefRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ExceptionCode.CHEF_NOT_FOUND, id));

        chefRepository.deleteById(chefEntity.getId());
    }
}
