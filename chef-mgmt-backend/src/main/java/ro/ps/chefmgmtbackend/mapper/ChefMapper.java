package ro.ps.chefmgmtbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ro.ps.chefmgmtbackend.dto.ChefRequestDTO;
import ro.ps.chefmgmtbackend.dto.ChefResponseDTO;
import ro.ps.chefmgmtbackend.model.ChefEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChefMapper {

    @Mapping(target = "numberOfStars", source = "rating")
    ChefResponseDTO chefEntityToChefResponseDTO(ChefEntity chefEntity);

    List<ChefResponseDTO> chefEntityListToChefResponseDTOList(List<ChefEntity> chefEntityList);

    ChefEntity chefRequestDTOToChefEntity(ChefRequestDTO chefRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateChefEntity(@MappingTarget ChefEntity chefEntity, ChefRequestDTO chefRequestDTO);
}
