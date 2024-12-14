package itmo.spacemarineservice.domain.dto;

import java.util.List;

public record GroupingByNameSpaceMarine(
        String field,
        List<SpaceMarine> marines
) {
}
