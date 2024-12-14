package itmo.spacemarineservice.controllers.response;

import java.util.List;

public record PaginatedSpaceMarinesResponse(
        int page,
        int totalPage,
        int size,
        List<SpaceMarineResponse> items
) {
}
