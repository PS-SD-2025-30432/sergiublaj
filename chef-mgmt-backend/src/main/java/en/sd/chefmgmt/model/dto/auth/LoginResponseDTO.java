package en.sd.chefmgmt.model.dto.auth;

import java.util.UUID;

import en.sd.chefmgmt.model.entity.Role;

public record LoginResponseDTO(UUID id, String email, Role role) { }