package en.sd.chefmgmt.dto.chef;

import java.time.ZonedDateTime;
import java.util.Objects;

import en.sd.chefmgmt.util.RestUtil;
import jakarta.validation.constraints.Min;

public record ChefFilterDTO(
        String name,
        Double rating,
        String email,
        ZonedDateTime birthDate,

        @Min(value = 0, message = "Page number must be at least 0.")
        Integer pageNumber,

        @Min(value = 1, message = "Page size must be at least 1.")
        Integer pageSize
) {

    public ChefFilterDTO {
        pageNumber = Objects.requireNonNullElse(pageNumber, RestUtil.DEFAULT_PAGE_NUMBER);
        pageSize = Objects.requireNonNullElse(pageSize, RestUtil.DEFAULT_PAGE_SIZE);
    }
}