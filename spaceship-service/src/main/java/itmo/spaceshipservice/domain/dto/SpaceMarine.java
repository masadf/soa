package itmo.spaceshipservice.domain.dto;

public record SpaceMarine(
        int id,
        String name,
        AstartesCategory category,
        Double health
) {
}

