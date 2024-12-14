package itmo.spacemarineservice.domain.dto;

import java.time.ZonedDateTime;

public record SpaceMarine(
        int id,
        String name,
        Coordinates coordinates,
        ZonedDateTime creationDate,
        double health,
        AstartesCategory category,
        Weapon weaponType,
        MeleeWeapon meleeWeapon,
        Chapter chapter
){}
