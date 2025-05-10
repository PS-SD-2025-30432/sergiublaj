package en.sd.chefmgmt.model.dto.chef;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record ChefResponseDTO(UUID id, String name, String cnp, ZonedDateTime birthDate, double numberOfStars) { }