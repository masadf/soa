package itmo.utils.dto;

public record SpaceMarineUpdateRequest(
        String name,
        Coordinates coordinates,
        Double health,
        AstartesCategory category,
        Weapon weaponType,
        MeleeWeapon meleeWeapon,
        String chapterName
){}
