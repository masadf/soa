package itmo.spacemarineservice.domain.dto;

public record SpaceMarineFilterParam(
        SpaceMarineField field,
        Object extraValue,
        FilterType type
){
}
