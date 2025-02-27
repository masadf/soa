package itmo.utils.dto;

public record SpaceMarineResponse(
        int id,
        String name,
        Coordinates coordinates,
        String creationDate,
        double health,
        AstartesCategory category,
        Weapon weaponType,
        MeleeWeapon meleeWeapon,
        Chapter chapter
){}
