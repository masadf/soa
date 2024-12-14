package itmo.spaceshipservice.controllers.response;

import itmo.spaceshipservice.domain.dto.SpaceMarine;

import java.util.List;

public record StarshipResponse(
        Integer id,
        String name,
        List<SpaceMarine> marines
) {
}
