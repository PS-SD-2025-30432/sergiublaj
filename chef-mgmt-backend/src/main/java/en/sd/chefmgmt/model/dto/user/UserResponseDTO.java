package en.sd.chefmgmt.model.dto.user;

import java.time.ZonedDateTime;
import java.util.UUID;

import en.sd.chefmgmt.model.entity.Role;

public record UserResponseDTO(UUID id, String email, Role role, String name, String cnp, ZonedDateTime birthDate,
                              Double rating) { }