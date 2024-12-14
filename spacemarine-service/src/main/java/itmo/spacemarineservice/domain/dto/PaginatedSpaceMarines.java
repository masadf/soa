package itmo.spacemarineservice.domain.dto;

import java.util.List;

public record PaginatedSpaceMarines(
        int page,
        int totalPage,
        int size,
        List<SpaceMarine> items
) {
}
