package itmo.utils.dto;

import java.util.List;

public record SpaceMarineQueryParams(
        Integer page,
        Integer pageSize,
        List<SpaceMarineSortParam> sortParams,
        List<SpaceMarineFilterParam> filterParams
) {
}

