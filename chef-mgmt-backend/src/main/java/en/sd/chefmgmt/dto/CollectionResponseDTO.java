package en.sd.chefmgmt.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record CollectionResponseDTO<T>(
        int pageNumber,
        int pageSize,
        long totalPages,
        long totalElements,
        List<T> elements
) { }