package itmo.utils.mapper;

import itmo.utils.dto.SortingType;
import itmo.utils.dto.SpaceMarineSortParam;
import itmo.utils.exceptions.SortParamsInvalidException;
import jakarta.ejb.Stateless;
import org.jboss.ejb3.annotation.Pool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static itmo.utils.dto.SortingType.ASCENDING;
import static itmo.utils.dto.SortingType.DESCENDING;

@Stateless
@Pool("slsb-strict-max-pool")
public class SpaceMarineSortParamsParserImpl implements SpaceMarineSortParamsParser {
    private static final Pattern REQUEST_REGEX = Pattern.compile("([A-Za-z.]*)(\\((\\S*)\\))?");

    @Override
    public List<SpaceMarineSortParam> parse(List<String> sortBy) {
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

    private SortingType parseAndValidateType(String origin) {
        if (origin == null) return DESCENDING;
        return switch (origin) {
            case "desc" -> DESCENDING;
            case "asc" -> ASCENDING;
            default -> throw new SortParamsInvalidException("Невалидный тип сортировки " + origin);
        };
    }
}
