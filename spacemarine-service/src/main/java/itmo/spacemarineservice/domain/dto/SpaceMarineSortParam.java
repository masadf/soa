package itmo.spacemarineservice.domain.dto;

public record SpaceMarineSortParam(
        SpaceMarineField field,
        SortingType type
){}