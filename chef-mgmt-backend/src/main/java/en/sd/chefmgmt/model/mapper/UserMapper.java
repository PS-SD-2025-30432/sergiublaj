package en.sd.chefmgmt.model.mapper;

import en.sd.chefmgmt.model.dto.user.UserResponseDTO;
import en.sd.chefmgmt.model.entity.ChefEntity;
import en.sd.chefmgmt.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ChefMapper.class)
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    UserResponseDTO userEntityToUserResponseDTO(UserEntity user, ChefEntity chef);
}
