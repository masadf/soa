package itmo.spacemarineservice.domain.utils;

import itmo.spaceshipservice.domain.exceptions.MarineNotFoundException;
import itmo.spaceshipservice.domain.utils.SpaceMarineFilterParamsParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SpaceMarineFilterParamsParserTest {

    @Test
    public void parseWithEqual() {
        assertThat(SpaceMarineFilterParamsParser.parse(List.of("health=12"))).isEqualTo(List.of(
                new SpaceMarineFilterParam(
                        "health",
                        "12",
                        EQUAL
        ));
    }

    @Test
    public void parseWithGreaterEqual() {
        assertThat(SpaceMarineFilterParamsParser.parse(List.of("health>=12"))).isEqualTo(List.of(
                new SpaceMarineFilterParam(
                        "health",
                        "12",
                        GREATER_EQUAL
                )
        ));
    }

    @Test
    public void parseWithGreater() {
        assertThat(SpaceMarineFilterParamsParser.parse(List.of("health>12"))).isEqualTo(List.of(
                new SpaceMarineFilterParam(
                        "health",
                        "12",
                        GREATER
                )
        ));
    }

    @Test
    public void parseWithLessEqual() {
        assertThat(SpaceMarineFilterParamsParser.parse(List.of("health<=12"))).isEqualTo(List.of(
                new SpaceMarineFilterParam(
                        "health",
                        "12",
                        LESS_EQUAL
                )
        ));
    }

    @Test
    public void parseWithLess() {
        assertThat(SpaceMarineFilterParamsParser.parse(List.of("health<12"))).isEqualTo(List.of(
                new SpaceMarineFilterParam(
                        "health",
                        "12",
                        LESS
                )
        ));
    }

    @Test
    public void parseWithFloat() {
        assertThat(SpaceMarineFilterParamsParser.parse(List.of("coordinates.x<12.324"))).isEqualTo(List.of(
                new SpaceMarineFilterParam(
                        "coordinates.x",
                        "12.324",
                        LESS
                )
        ));
    }

    @Test
    public void parseWithError() {
        assertThatThrownBy(() -> SpaceMarineFilterParamsParser.parse(List.of("coordinates.x<12s324")))
                .isInstanceOf(MarineNotFoundException.class)
                .hasMessage("Invalid filter param value=12s324 for field=coordinates.x");
    }
}