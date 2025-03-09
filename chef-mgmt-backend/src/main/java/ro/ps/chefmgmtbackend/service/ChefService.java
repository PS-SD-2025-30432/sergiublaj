package ro.ps.chefmgmtbackend.service;

import ro.ps.chefmgmtbackend.dto.ChefRequestDTO;
import ro.ps.chefmgmtbackend.dto.ChefResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ChefService {

    List<ChefResponseDTO> findAll(String name);

    ChefResponseDTO findById(UUID id);

    ChefResponseDTO save(ChefRequestDTO chefRequestDTO);

    ChefResponseDTO update(UUID id, ChefRequestDTO chefRequestDTO);

    void delete(UUID id);
}
