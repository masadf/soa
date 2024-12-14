package itmo.spacemarineservice.domain.utils;

import itmo.spacemarineservice.domain.dto.SortingType;
import itmo.spacemarineservice.domain.dto.SpaceMarineSortParam;
import itmo.spacemarineservice.domain.exceptions.SortParamsInvalidException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static itmo.spacemarineservice.domain.dto.SortingType.ASCENDING;
import static itmo.spacemarineservice.domain.dto.SortingType.DESCENDING;

public class SpaceMarineSortParamsParser {
    private static final Pattern REQUEST_REGEX = Pattern.compile("([A-Za-z.]*)(\\((\\S*)\\))?");

    public static List<SpaceMarineSortParam> parse(List<String> sortBy) {
        if (sortBy == null) return new ArrayList<>();
        return sortBy.stream().map((el) -> {
            var matcher = REQUEST_REGEX.matcher(el);
            if (matcher.find()) {
                var field = SpaceMarineFieldParser.parseField(matcher.group(1));
                var typeParam = matcher.group(3);
                var type = parseAndValidateType(typeParam);
                return new SpaceMarineSortParam(field, type);
            }
            throw new SortParamsInvalidException("Невалидные параметры сортировки в строке: " + el);
        }).toList();
    }

    private static SortingType parseAndValidateType(String origin) {
        if (origin == null) return DESCENDING;
        return switch (origin) {
            case "desc" -> DESCENDING;
            case "asc" -> ASCENDING;
            default -> throw new SortParamsInvalidException("Невалидный тип сортировки " + origin);
        };
    }
}
