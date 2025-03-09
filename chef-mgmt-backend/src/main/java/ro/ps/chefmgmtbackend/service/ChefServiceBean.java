package ro.ps.chefmgmtbackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.ps.chefmgmtbackend.dto.ChefRequestDTO;
import ro.ps.chefmgmtbackend.dto.ChefResponseDTO;
import ro.ps.chefmgmtbackend.mapper.ChefMapper;
import ro.ps.chefmgmtbackend.model.ChefEntity;
import ro.ps.chefmgmtbackend.repository.ChefRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChefServiceBean implements ChefService {

    private final ChefRepository chefRepository;
    private final ChefMapper chefMapper;

    @Override
    public List<ChefResponseDTO> findAll(String name) {
        List<ChefEntity> chefEntityList = chefRepository.findAllByNameContaining(name);

        return chefMapper.chefEntityListToChefResponseDTOList(chefEntityList);
    }

    @Override
    public ChefResponseDTO findById(UUID id) {
        ChefEntity chefEntity = chefRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chef with ID " + id + " not found"));

        return chefMapper.chefEntityToChefResponseDTO(chefEntity);
    }

    @Override
    public ChefResponseDTO save(ChefRequestDTO chefRequestDTO) {
        ChefEntity chefToBeAdded = chefMapper.chefRequestDTOToChefEntity(chefRequestDTO);
        ChefEntity chefAdded = chefRepository.save(chefToBeAdded);

        return chefMapper.chefEntityToChefResponseDTO(chefAdded);
    }

    @Override
    public ChefResponseDTO update(UUID id, ChefRequestDTO chefRequestDTO) {
        ChefEntity chefEntityToBeUpdated = chefRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chef with ID " + id + " not found"));
        chefMapper.updateChefEntity(chefEntityToBeUpdated, chefRequestDTO);
        ChefEntity chefEntitySaved = chefRepository.save(chefEntityToBeUpdated);

        return chefMapper.chefEntityToChefResponseDTO(chefEntitySaved);
    }

    @Override
    public void delete(UUID id) {
        ChefEntity chefEntity = chefRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chef with ID " + id + " not found"));

        chefRepository.deleteById(chefEntity.getId());
    }
}
