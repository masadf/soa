package itmo.utils.dto;

public record SpaceMarineFilterParam(
        SpaceMarineField field,
        Object extraValue,
        FilterType type
){
}
