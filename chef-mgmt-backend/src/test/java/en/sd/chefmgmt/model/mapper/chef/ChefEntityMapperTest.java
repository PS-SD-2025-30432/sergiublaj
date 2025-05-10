package en.sd.chefmgmt.model.mapper.chef;

import static org.assertj.core.api.Assertions.assertThat;

import en.sd.chefmgmt.model.mapper.ChefEntityMapper;
import en.sd.chefmgmt.model.mapper.MapperTestData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class ChefEntityMapperTest {

    private final ChefEntityMapper mapper = Mappers.getMapper(ChefEntityMapper.class);

    @Test
    void givenChefEntity_whenConvertEntityToResponseDto_thenMappedCorrectly() {
        // given
        final var entity = MapperTestData.chefEntity();
        final var expected = MapperTestData.chefResponseDto();

        // when
        final var result = mapper.convertEntityToResponseDto(entity);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void givenChefRequestDto_whenConvertRequestDtoToEntity_thenMappedCorrectly() {
        // given
        final var request = MapperTestData.chefRequestDto();
        final var expected = MapperTestData.chefEntity();

        // when
        final var result = mapper.convertRequestDtoToEntity(request);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void givenChefRequestDtoAndChefEntity_whenUpdateChefEntity_thenEntityUpdatedCorrectly() {
        // given
        final var entity = MapperTestData.chefEntity();
        final var request = MapperTestData.updatedChefRequestDto();
        final var expected = MapperTestData.updatedChefEntity();

        // when
        mapper.updateChefEntity(entity, request);

        // then
        assertThat(entity)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void givenChefEntityList_whenConvertEntitiesToResponseDtos_thenMappedCorrectly() {
        // given
        final var entities = MapperTestData.chefEntityList();
        final var expected = MapperTestData.chefResponseDtoList();

        // when
        final var result = mapper.convertEntitiesToResponseDtos(entities);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
