package itmo.spacemarineservice.controllers;


import itmo.spacemarineservice.controllers.request.SpaceMarineCreateRequest;
import itmo.spacemarineservice.controllers.request.SpaceMarineUpdateRequest;
import itmo.spacemarineservice.controllers.response.*;
import itmo.spacemarineservice.domain.dto.SpaceMarineQueryParams;
import itmo.spacemarineservice.domain.exceptions.*;
import itmo.spacemarineservice.domain.services.SpaceMarineService;
import itmo.spacemarineservice.domain.utils.SpaceMarineFilterParamsParser;
import itmo.spacemarineservice.domain.utils.SpaceMarineSortParamsParser;
import itmo.spacemarineservice.domain.utils.SpaceMarineValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SpaceMarineController {
    @Autowired
    private SpaceMarineService spaceMarineService;

    @GetMapping("/marines")
    PaginatedSpaceMarinesResponse getSpaceMarines(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) List<String> sortBy, @RequestParam(required = false) List<String> filterBy) {
        var paginatedDto = spaceMarineService.findSpaceMarines(new SpaceMarineQueryParams(page, pageSize, SpaceMarineSortParamsParser.parse(sortBy), SpaceMarineFilterParamsParser.parse(filterBy)));
        return new PaginatedSpaceMarinesResponse(
                paginatedDto.page(),
                paginatedDto.totalPage(),
                paginatedDto.size(),
                paginatedDto.items().stream().map((el) ->
                        new SpaceMarineResponse(
                                el.id(),
                                el.name(),
                                el.coordinates(),
                                el.creationDate(),
                                el.health(),
                                el.category(),
                                el.weaponType(),
                                el.meleeWeapon(),
                                el.chapter()
                        )
                ).toList()
        );
    }

    @GetMapping("/marines/{id}")
    SpaceMarineResponse getSpaceMarine(@PathVariable Integer id) {
        var spaceMarine = spaceMarineService.findSpaceMarine(id);

        return new SpaceMarineResponse(
                spaceMarine.id(),
                spaceMarine.name(),
                spaceMarine.coordinates(),
                spaceMarine.creationDate(),
                spaceMarine.health(),
                spaceMarine.category(),
                spaceMarine.weaponType(),
                spaceMarine.meleeWeapon(),
                spaceMarine.chapter()
        );
    }

    @DeleteMapping("/marines/{id}")
    void deleteSpaceMarines(@PathVariable Integer id, HttpServletResponse response) {
        spaceMarineService.deleteSpaceMarine(id);
        response.setStatus(204);
    }

    @PutMapping("/marines/{id}")
    SpaceMarineResponse updateSpaceMarine(@RequestBody SpaceMarineUpdateRequest request, @PathVariable Integer id) {
        SpaceMarineValidator.validate(request);
        var spaceMarine = spaceMarineService.updateSpaceMarine(id, request);

        return new SpaceMarineResponse(
                spaceMarine.id(),
                spaceMarine.name(),
                spaceMarine.coordinates(),
                spaceMarine.creationDate(),
                spaceMarine.health(),
                spaceMarine.category(),
                spaceMarine.weaponType(),
                spaceMarine.meleeWeapon(),
                spaceMarine.chapter()
        );
    }

    @PostMapping("/marines")
    ElementCreatedResponse createSpaceMarine(@RequestBody SpaceMarineCreateRequest request, HttpServletResponse response) {
        SpaceMarineValidator.validate(request);
        var id = spaceMarineService.createSpaceMarine(request);
        response.setStatus(201);
        return new ElementCreatedResponse(id);
    }

    @PostMapping("/marines/health/avg")
    CalculationResponse calculateSpaceMarineAvg() {
        var result = spaceMarineService.calculateSpaceMarineHealthAvg();
        return new CalculationResponse(result);
    }

    @GetMapping("/marines/name/grouping/size")
    List<GroupingByNameResponse> groupByName() {
        var result = spaceMarineService.groupByName();
        return result.stream().map(el -> new GroupingByNameResponse(el.field(), el.marines().size())).toList();
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({FilterParamsInvalidException.class})
    public ErrorResponse handleException(FilterParamsInvalidException exception) {
        return new ErrorResponse(exception.getMessage(), "filter.param.invalid");
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({SortParamsInvalidException.class})
    public ErrorResponse handleException(SortParamsInvalidException exception) {
        return new ErrorResponse(exception.getMessage(), "sort.param.invalid");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({SpaceMarineNotFoundException.class})
    public ErrorResponse handleException(SpaceMarineNotFoundException exception) {
        return new ErrorResponse(exception.getMessage(), "not.found.marine");
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({SpaceMarineValidationException.class})
    public ErrorResponse handleException(SpaceMarineValidationException exception) {
        return new ErrorResponse(exception.getMessage(), "marine.invalid");
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({UnknownFieldException.class})
    public ErrorResponse handleException(UnknownFieldException exception) {
        return new ErrorResponse(exception.getMessage(), "field.invalid");
    }
}
