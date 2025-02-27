package itmo.utils.mapper;

import itmo.utils.dto.SpaceMarineSortParam;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface SpaceMarineSortParamsParser {
    List<SpaceMarineSortParam> parse(List<String> sortBy);
}
