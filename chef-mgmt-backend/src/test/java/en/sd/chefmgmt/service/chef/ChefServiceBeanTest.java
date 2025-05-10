package en.sd.chefmgmt.service.chef;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import en.sd.chefmgmt.exception.model.DataNotFoundException;
import en.sd.chefmgmt.exception.model.DuplicateDataException;
import en.sd.chefmgmt.model.entity.ChefEntity;
import en.sd.chefmgmt.model.mapper.ChefEntityMapper;
import en.sd.chefmgmt.repository.chef.ChefRepository;
import en.sd.chefmgmt.repository.chef.ChefSpec;
import en.sd.chefmgmt.service.ServiceTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class ChefServiceBeanTest {

    @InjectMocks
    private ChefServiceBean underTest;

    @Mock
    private ChefRepository chefRepository;

    @Mock
    private ChefSpec chefSpec;

    @Mock
    private ChefEntityMapper chefEntityMapper;

    @Test
    void givenFilter_whenFindAll_thenReturnCollectionResponse() {
        // given
        final var filter = ServiceTestData.chefFilter();
        final var page = ServiceTestData.chefPage();
        final var responseList = ServiceTestData.chefResponseDtoList();
        final var specification = (Specification<ChefEntity>) (_, _, cb) -> cb.conjunction();

        when(chefSpec.createSpecification(filter)).thenReturn(specification);
        when(chefRepository.findAll(eq(specification), any(Pageable.class))).thenReturn(page);
        when(chefEntityMapper.convertEntitiesToResponseDtos(anyList())).thenReturn(responseList);

        // when
        final var result = underTest.findAll(filter);

        // then
        assertThat(result.elements()).hasSize(2);
        assertThat(result.totalElements()).isEqualTo(2);
    }

    @Test
    void givenValidId_whenFindById_thenReturnChefResponseDTO() {
        // given
        final var id = ServiceTestData.chefId();
        final var entity = ServiceTestData.chefEntity();
        final var response = ServiceTestData.chefResponseDto();

        when(chefRepository.findById(id)).thenReturn(Optional.of(entity));
        when(chefEntityMapper.convertEntityToResponseDto(entity)).thenReturn(response);

        // when
        final var result = underTest.findById(id);

        // then
        assertThat(result).isEqualTo(response);
    }

    @Test
    void givenInvalidId_whenFindById_thenThrowDataNotFoundException() {
        // given
        final var id = ServiceTestData.chefId();

        when(chefRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThatThrownBy(() -> underTest.findById(id)).isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void givenValidRequest_whenSave_thenReturnSavedChef() {
        // given
        final var request = ServiceTestData.chefRequestDto();
        final var entity = ServiceTestData.chefEntity();
        final var response = ServiceTestData.chefResponseDto();

        when(chefRepository.existsByCnp(request.cnp())).thenReturn(false);
        when(chefEntityMapper.convertRequestDtoToEntity(request)).thenReturn(entity);
        when(chefRepository.save(entity)).thenReturn(entity);
        when(chefEntityMapper.convertEntityToResponseDto(entity)).thenReturn(response);

        // when
        final var result = underTest.save(request);

        // then
        assertThat(result).isEqualTo(response);
    }

    @Test
    void givenDuplicateCnp_whenSave_thenThrowDuplicateDataException() {
        // given
        final var request = ServiceTestData.chefRequestDto();

        when(chefRepository.existsByCnp(request.cnp())).thenReturn(true);

        // when + then
        assertThatThrownBy(() -> underTest.save(request)).isInstanceOf(DuplicateDataException.class);
    }

    @Test
    void givenValidRequest_whenUpdate_thenReturnUpdatedChef() {
        // given
        final var id = ServiceTestData.chefId();
        final var request = ServiceTestData.updatedChefRequestDto();
        final var entity = ServiceTestData.chefEntity();
        final var updated = ServiceTestData.updatedChefEntity();
        final var response = ServiceTestData.updatedChefResponseDto();

        when(chefRepository.findById(id)).thenReturn(Optional.of(entity));
        when(chefRepository.existsByCnpAndIdIsNot(request.cnp(), id)).thenReturn(false);
        when(chefRepository.save(any(ChefEntity.class))).thenReturn(updated);
        when(chefEntityMapper.convertEntityToResponseDto(updated)).thenReturn(response);

        doAnswer(invocation -> {
            final var _ = invocation.getArgument(0, ChefEntity.class);
            return null;
        }).when(chefEntityMapper).updateChefEntity(eq(entity), eq(request));

        // when
        final var result = underTest.update(id, request);

        // then
        assertThat(result).isEqualTo(response);
    }

    @Test
    void givenDuplicateCnp_whenUpdate_thenThrowDuplicateDataException() {
        // given
        final var id = ServiceTestData.chefId();
        final var request = ServiceTestData.updatedChefRequestDto();
        final var entity = ServiceTestData.chefEntity();

        when(chefRepository.findById(id)).thenReturn(Optional.of(entity));
        when(chefRepository.existsByCnpAndIdIsNot(request.cnp(), id)).thenReturn(true);

        // when + then
        assertThatThrownBy(() -> underTest.update(id, request)).isInstanceOf(DuplicateDataException.class);
    }

    @Test
    void givenValidId_whenDelete_thenRepositoryDeleteCalled() {
        // given
        final var id = ServiceTestData.chefId();
        final var entity = ServiceTestData.chefEntity();

        when(chefRepository.findById(id)).thenReturn(Optional.of(entity));

        // when
        underTest.delete(id);

        // then
        verify(chefRepository).deleteById(id);
    }

    @Test
    void givenInvalidId_whenDelete_thenThrowDataNotFoundException() {
        // given
        final var id = ServiceTestData.chefId();

        when(chefRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThatThrownBy(() -> underTest.delete(id)).isInstanceOf(DataNotFoundException.class);
    }
}
