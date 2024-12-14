package itmo.spacemarineservice.controllers.request;

import itmo.spacemarineservice.domain.dto.*;

public record SpaceMarineCreateRequest(
        String name,
        Coordinates coordinates,
        Double health,
        AstartesCategory category,
        Weapon weaponType,
        MeleeWeapon meleeWeapon,
        String chapterName
){}
