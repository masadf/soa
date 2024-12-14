package itmo.spaceshipservice.controllers;


import itmo.spaceshipservice.controllers.request.StarshipCreateRequest;
import itmo.spaceshipservice.controllers.request.StarshipUpdateRequest;
import itmo.spaceshipservice.controllers.response.ElementCreatedResponse;
import itmo.spaceshipservice.controllers.response.ErrorResponse;
import itmo.spaceshipservice.controllers.response.StarshipResponse;
import itmo.spaceshipservice.domain.exceptions.MarineNotFoundException;
import itmo.spaceshipservice.domain.exceptions.StarshipNotFoundException;
import itmo.spaceshipservice.domain.services.StarshipService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StarshipController {
    @Autowired
    private StarshipService starshipService;

    @GetMapping("/")
    List<StarshipResponse> getSpaceships() {
        return starshipService.getStarships()
                .stream()
                .map(el -> new StarshipResponse(el.id(), el.name(), el.marines())).toList();
    }

    @PostMapping("/")
    ElementCreatedResponse addSpaceship(@RequestBody StarshipCreateRequest request) {
        return new ElementCreatedResponse(starshipService.addStarship(request.name()));
    }

    @GetMapping("/{id}")
    StarshipResponse getSpaceship(@PathVariable Integer id) {
        var starship = starshipService.getStarship(id);
        return new StarshipResponse(starship.id(), starship.name(), starship.marines());
    }

    @DeleteMapping("/{id}")
    void deleteSpaceship(@PathVariable Integer id, HttpServletResponse response) {
        starshipService.deleteStarship(id);
        response.setStatus(204);
    }

    @PutMapping("/{id}")
    StarshipResponse updateSpaceship(@PathVariable Integer id, @RequestBody StarshipUpdateRequest request) {
        var starship = starshipService.updateStarship(id, request.name());
        return new StarshipResponse(starship.id(), starship.name(), starship.marines());
    }

    @PatchMapping("/{starship-id}/load/{space-marine-id}")
    void loadToSpaceship(@PathVariable("starship-id") Integer starshipId, @PathVariable("space-marine-id") Integer spaceMarineId, HttpServletResponse response) {
        starshipService.loadMarineToStarship(starshipId, spaceMarineId);
        response.setStatus(204);
    }

    @PatchMapping("/{starship-id}/unload-all")
    void unloadFromSpaceship(@PathVariable("starship-id") Integer starshipId, HttpServletResponse response) {
        starshipService.unloadAllFromStarship(starshipId);
        response.setStatus(204);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({StarshipNotFoundException.class})
    public ErrorResponse handleException(StarshipNotFoundException exception) {
        return new ErrorResponse(exception.getMessage(), "not.found.starship");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({MarineNotFoundException.class})
    public ErrorResponse handleException(MarineNotFoundException exception) {
        return new ErrorResponse(exception.getMessage(), "not.found.marine");
    }
}
