package en.sd.chefmgmt.dto;

import java.util.UUID;

public record ChefResponseDTO(UUID id, String name, double numberOfStars) { }