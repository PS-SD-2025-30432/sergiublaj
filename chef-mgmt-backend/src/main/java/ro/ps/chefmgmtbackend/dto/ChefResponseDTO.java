package ro.ps.chefmgmtbackend.dto;

import java.util.UUID;

public record ChefResponseDTO(UUID id, String name, double numberOfStars) { }