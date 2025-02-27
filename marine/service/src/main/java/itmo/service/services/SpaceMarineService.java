package itmo.service.services;


import itmo.utils.dto.*;

import java.util.List;

public interface SpaceMarineService {
    PaginatedSpaceMarines findSpaceMarines(SpaceMarineQueryParams queryParams);

    SpaceMarine findSpaceMarine(Integer id);

    Integer createSpaceMarine(SpaceMarineCreateRequest spaceMarineCreateRequest);

    Double calculateSpaceMarineHealthAvg();

    List<GroupingByNameSpaceMarine> groupByName();

    SpaceMarine updateSpaceMarine(Integer id, SpaceMarineUpdateRequest spaceMarineUpdateRequest);

    void deleteSpaceMarine(Integer id);
}
