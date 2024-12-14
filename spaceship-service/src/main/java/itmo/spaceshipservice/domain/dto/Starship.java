package itmo.spaceshipservice.domain.dto;

import java.util.List;

public record Starship(
        Integer id,
        String name,
        List<SpaceMarine> marines
) {
}
