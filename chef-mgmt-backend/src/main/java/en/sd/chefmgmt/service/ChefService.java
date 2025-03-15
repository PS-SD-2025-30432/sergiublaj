package en.sd.chefmgmt.service;

import en.sd.chefmgmt.dto.ChefRequestDTO;
import en.sd.chefmgmt.dto.ChefResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ChefService {

    List<ChefResponseDTO> findAll(String name);

    ChefResponseDTO findById(UUID id);

    ChefResponseDTO save(ChefRequestDTO chefRequestDTO);

    ChefResponseDTO update(UUID id, ChefRequestDTO chefRequestDTO);

    void delete(UUID id);
}
