package itmo.service.controllers;

import itmo.service.consul.ConsulRegistrationService;
import itmo.service.services.SpaceMarineService;
import itmo.utils.dto.*;
import itmo.utils.exceptions.RequestParamInvalidException;
import itmo.utils.mapper.SpaceMarineFilterParamsParser;
import itmo.utils.mapper.SpaceMarineSortParamsParser;
import itmo.utils.mapper.SpaceMarineValidator;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static java.time.temporal.ChronoUnit.MILLIS;

@RestController
@CrossOrigin(origins = "*")
public class SpaceMarineController {
    @Autowired
    private SpaceMarineService spaceMarineService;
    @Autowired
    private ConsulRegistrationService consulRegistrationService;

    SpaceMarineFilterParamsParser filterParamsParser = JNDIConfig.spaceMarineFilterParamsParser();
    SpaceMarineSortParamsParser sortParamsParser = JNDIConfig.spaceMarineSortParamsParser();
    SpaceMarineValidator spaceMarineValidator = JNDIConfig.spaceMarineValidator();

    @GetMapping("/")
    void healthCheck(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("/marines")
    PaginatedSpaceMarinesResponse getSpaceMarines(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) List<String> sortBy, @RequestParam(required = false) List<String> filterBy) {
        if (page <= 0) throw new RequestParamInvalidException("Номер страницы должен быть больше 0");
        if (pageSize <= 0) throw new RequestParamInvalidException("Размер страницы должен быть больше 0");
        var paginatedDto = spaceMarineService.findSpaceMarines(new SpaceMarineQueryParams(page, pageSize, sortParamsParser.parse(sortBy), filterParamsParser.parse(filterBy)));
        return new PaginatedSpaceMarinesResponse(
                paginatedDto.page(),
                paginatedDto.totalPage(),
                paginatedDto.size(),
                paginatedDto.items().stream().map((el) ->
                        new SpaceMarineResponse(
                                el.id(),
                                el.name(),
                                el.coordinates(), ISO_INSTANT.format(el.creationDate().truncatedTo(MILLIS)),
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
                spaceMarine.coordinates(), ISO_INSTANT.format(spaceMarine.creationDate().truncatedTo(MILLIS)),
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
        spaceMarineValidator.validate(request);
        var spaceMarine = spaceMarineService.updateSpaceMarine(id, request);

        return new SpaceMarineResponse(
                spaceMarine.id(),
                spaceMarine.name(),
                spaceMarine.coordinates(), ISO_INSTANT.format(spaceMarine.creationDate().truncatedTo(MILLIS)),
                spaceMarine.health(),
                spaceMarine.category(),
                spaceMarine.weaponType(),
                spaceMarine.meleeWeapon(),
                spaceMarine.chapter()
        );
    }

    @PostMapping("/marines")
    ElementCreatedResponse createSpaceMarine(@RequestBody SpaceMarineCreateRequest request, HttpServletResponse response) {
        spaceMarineValidator.validate(request);
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

    @PostConstruct
    public void registerService() {
        consulRegistrationService.registerService("marines" + new Random().nextInt(), "localhost", 8443);
    }
}
