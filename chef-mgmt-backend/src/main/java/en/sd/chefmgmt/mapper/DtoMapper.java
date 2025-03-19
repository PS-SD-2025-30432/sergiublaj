package en.sd.chefmgmt.mapper;

import java.util.List;

import org.mapstruct.Mapping;

public interface DtoMapper<Entity, RequestDto, ResponseDto> {

    @Mapping(target = "id", ignore = true)
    Entity convertRequestDtoToEntity(RequestDto requestDto);

    ResponseDto convertEntityToResponseDto(Entity entity);

    List<ResponseDto> convertEntitiesToResponseDtos(List<Entity> entities);
}
