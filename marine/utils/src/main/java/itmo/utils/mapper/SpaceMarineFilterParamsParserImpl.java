package itmo.utils.mapper;

import itmo.utils.dto.*;
import itmo.utils.exceptions.FilterParamsInvalidException;
import itmo.utils.exceptions.SortParamsInvalidException;
import jakarta.ejb.Stateless;
import org.jboss.ejb3.annotation.Pool;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static itmo.utils.dto.FilterType.*;


@Stateless
@Pool("slsb-strict-max-pool")
public class SpaceMarineFilterParamsParserImpl implements SpaceMarineFilterParamsParser{
    private static final Pattern REQUEST_REGEX = Pattern.compile("([A-Za-z.]*)([=><]*)([A-Za-zА-Яа-я0-9_. ]*)");

    @Override
    public List<SpaceMarineFilterParam> parse(List<String> filterBy) {
        if (filterBy == null) return new ArrayList<>();
        return filterBy.stream().map((el) -> {
            var matcher = REQUEST_REGEX.matcher(el);
            if (matcher.find()) {
                var field = SpaceMarineFieldParser.parseField(matcher.group(1));
                var expression = parseExpression(matcher.group(2));
                var value = parseAndValidateValue(field, matcher.group(3));
                return new SpaceMarineFilterParam(field, value, expression);
            }
            throw new FilterParamsInvalidException("Невалидные параметры фильтрации в строке: " + el);
        }).toList();
    }

    private FilterType parseExpression(String origin) {
        return switch (origin) {
            case ">" -> GREATER;
            case ">=" -> GREATER_EQUAL;
            case "=" -> EQUAL;
            case "<" -> LESS;
            case "<=" -> LESS_EQUAL;
            default -> throw new SortParamsInvalidException("Невалидный тип операции " + origin);
        };
    }

    private Object parseAndValidateValue(SpaceMarineField field, String originValue) {
        try {
            return switch (field) {
                case ID, CHAPTER_MARINES_COUNT -> Integer.parseInt(originValue);
                case COORDINATES_X -> Float.parseFloat(originValue);
                case COORDINATES_Y -> Long.parseLong(originValue);
                case CREATION_DATE -> ZonedDateTime.parse(originValue);
                case HEALTH -> Double.parseDouble(originValue);
                case CATEGORY -> AstartesCategory.valueOf(originValue);
                case WEAPON_TYPE -> Weapon.valueOf(originValue);
                case MELEE_WEAPON -> MeleeWeapon.valueOf(originValue);
                case NAME, CHAPTER_NAME -> originValue;
            };
        } catch (Exception e) {
            throw new FilterParamsInvalidException("Невалидный параметр " + originValue + " для поля " + field);
        }
    }
}
