package en.sd.chefmgmt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import en.sd.chefmgmt.dto.ChefRequestDTO;
import en.sd.chefmgmt.dto.ChefResponseDTO;
import en.sd.chefmgmt.model.ChefEntity;

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
