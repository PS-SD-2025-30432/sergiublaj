package en.sd.chefmgmt.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import en.sd.chefmgmt.model.dto.chef.ChefFilterDTO;
import en.sd.chefmgmt.model.dto.chef.ChefRequestDTO;
import en.sd.chefmgmt.model.dto.chef.ChefResponseDTO;
import en.sd.chefmgmt.model.entity.ChefEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@UtilityClass
public class ServiceTestData {

    private static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final ZonedDateTime BIRTH_DATE = ZonedDateTime.parse(
            "1995-05-10T11:07:20.619769100+03:00[Europe/Bucharest]");
    private static final ZonedDateTime UPDATED_BIRTH_DATE = ZonedDateTime.parse(
            "1985-03-20T08:15:10.345678900+03:00[Europe/Bucharest]");

    public UUID chefId() {
        return ID;
    }

    public ChefFilterDTO chefFilter() {
        return ChefFilterDTO.builder()
                .name("John")
                .build();
    }

    public ChefEntity chefEntity() {
        return ChefEntity.builder()
                .id(ID)
                .name("John Doe")
                .cnp("1234567890123")
                .birthDate(BIRTH_DATE)
                .rating(4.5)
                .build();
    }

    public ChefEntity updatedChefEntity() {
        return ChefEntity.builder()
                .id(ID)
                .name("Jane Smith")
                .cnp("9876543210987")
                .birthDate(UPDATED_BIRTH_DATE)
                .rating(5.0)
                .build();
    }

    public ChefRequestDTO chefRequestDto() {
        return ChefRequestDTO.builder()
                .name("John Doe")
                .cnp("1234567890123")
                .birthDate(BIRTH_DATE)
                .rating(4.5)
                .build();
    }

    public ChefRequestDTO updatedChefRequestDto() {
        return ChefRequestDTO.builder()
                .name("Jane Smith")
                .cnp("9876543210987")
                .birthDate(UPDATED_BIRTH_DATE)
                .rating(5.0)
                .build();
    }

    public ChefResponseDTO chefResponseDto() {
        return ChefResponseDTO.builder()
                .id(ID)
                .name("John Doe")
                .cnp("1234567890123")
                .birthDate(BIRTH_DATE)
                .numberOfStars(4.5)
                .build();
    }

    public ChefResponseDTO updatedChefResponseDto() {
        return ChefResponseDTO.builder()
                .id(ID)
                .name("Jane Smith")
                .cnp("9876543210987")
                .birthDate(UPDATED_BIRTH_DATE)
                .numberOfStars(5.0)
                .build();
    }

    public List<ChefEntity> chefEntityList() {
        return List.of(chefEntity(), updatedChefEntity());
    }

    public List<ChefResponseDTO> chefResponseDtoList() {
        return List.of(chefResponseDto(), updatedChefResponseDto());
    }

    public Page<ChefEntity> chefPage() {
        return new PageImpl<>(chefEntityList());
    }
}
