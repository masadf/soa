package itmo.utils.mapper;

import itmo.utils.dto.SpaceMarineField;
import itmo.utils.exceptions.UnknownFieldException;

import static itmo.utils.dto.SpaceMarineField.*;

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
