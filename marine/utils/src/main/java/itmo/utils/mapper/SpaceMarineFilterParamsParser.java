package itmo.utils.mapper;

import itmo.utils.dto.SpaceMarineFilterParam;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface SpaceMarineFilterParamsParser {
    List<SpaceMarineFilterParam> parse(List<String> filterBy);
}
