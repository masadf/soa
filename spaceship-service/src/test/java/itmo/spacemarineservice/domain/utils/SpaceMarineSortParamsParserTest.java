package itmo.spacemarineservice.domain.utils;

import itmo.spaceshipservice.domain.exceptions.SortParamsInvalidException;
import itmo.spaceshipservice.domain.utils.SpaceMarineSortParamsParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static itmo.spaceshipservice.domain.dto.SortingType.ASCENDING;
import static itmo.spaceshipservice.domain.dto.SortingType.DESCENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SpaceMarineSortParamsParserTest {

    @Test
    public void parse() {
        assertThat(SpaceMarineSortParamsParser.parse(List.of("health(desc)"))).isEqualTo(List.of(
                new SpaceMarineSortParam(
                        "health",
                        DESCENDING
                )
        ));
    }

    @Test
    public void parseWithDefaultType() {
        assertThat(SpaceMarineSortParamsParser.parse(List.of("health"))).isEqualTo(List.of(
                new SpaceMarineSortParam(
                        "health",
                        DESCENDING
                )
        ));
    }

    @Test
    public void parseWithAscType() {
        assertThat(SpaceMarineSortParamsParser.parse(List.of("health(asc)"))).isEqualTo(List.of(
                new SpaceMarineSortParam(
                        "health",
                        ASCENDING
                )
        ));
    }

    @Test
    public void parseWithUnknownField() {
        assertThatThrownBy(() ->
                SpaceMarineSortParamsParser.parse(List.of("hello"))
        ).isInstanceOf(SortParamsInvalidException.class)
                .hasMessage("Invalid sort param field=hello");
    }

    @Test
    public void parseWithUnknownSortingType() {
        assertThatThrownBy(() ->
                SpaceMarineSortParamsParser.parse(List.of("health(type1)"))
        ).isInstanceOf(SortParamsInvalidException.class)
                .hasMessage("Invalid sort param type=type1");
    }
}