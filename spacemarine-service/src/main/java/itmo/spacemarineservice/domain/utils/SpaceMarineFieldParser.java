package itmo.spacemarineservice.domain.utils;

import itmo.spacemarineservice.domain.dto.SpaceMarineField;
import itmo.spacemarineservice.domain.exceptions.UnknownFieldException;

import static itmo.spacemarineservice.domain.dto.SpaceMarineField.*;

public class SpaceMarineFieldParser {

    public static SpaceMarineField parseField(String field) {
        return switch (field) {
            case "id" -> ID;
            case "name" -> NAME;
            case "coordinates.x" -> COORDINATES_X;
            case "coordinates.y" -> COORDINATES_Y;
            case "creationDate" -> CREATION_DATE;
            case "health" -> HEALTH;
            case "category" -> CATEGORY;
            case "weaponType" -> WEAPON_TYPE;
            case "meleeWeapon" -> MELEE_WEAPON;
            case "chapter.name" -> CHAPTER_NAME;
            case "chapter.marinesCount" -> CHAPTER_MARINES_COUNT;

            default -> throw new UnknownFieldException("Неизвестное поле " + field);
        };
    }
}
