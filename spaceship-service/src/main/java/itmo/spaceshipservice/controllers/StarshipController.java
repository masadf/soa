package itmo.spaceshipservice.controllers;


import itmo.spaceshipservice.domain.dto.Starship;
import itmo.spaceshipservice.domain.services.StarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import itmo.starshipservice.*;

@Endpoint
public class StarshipController {
    @Autowired
    private StarshipService starshipService;

    private static final String NAMESPACE_URI = "http://itmo/starshipservice";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStarshipsRequest")
    @ResponsePayload
    GetStarshipsResponse getSpaceships() {
        var response = new GetStarshipsResponse();
        starshipService.getStarships()
                .forEach(el -> response.getStarships().add(mapToStarshipDto(el)));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addStarshipRequest")
    @ResponsePayload
    ElementCreatedResponse addSpaceship(@RequestPayload AddStarshipRequest request) {
        var response = new ElementCreatedResponse();
        response.setId(starshipService.addStarship(request.getName()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStarshipRequest")
    @ResponsePayload
    GetStarshipResponse getSpaceship(@RequestPayload GetStarshipRequest request) {
        var starship = starshipService.getStarship(request.getId());
        var response = new GetStarshipResponse();
        response.setStarship(mapToStarshipDto(starship));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteStarshipRequest")
    void deleteSpaceship(@RequestPayload DeleteStarshipRequest request) {
        starshipService.deleteStarship(request.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateStarshipRequest")
    @ResponsePayload
    GetStarshipResponse updateSpaceship(@RequestPayload UpdateStarshipRequest request) {
        var starship = starshipService.updateStarship(request.getId(), request.getName());
        var response = new GetStarshipResponse();
        response.setStarship(mapToStarshipDto(starship));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loadToStarshipRequest")
    void loadToSpaceship(@RequestPayload LoadToStarshipRequest request) {
        starshipService.loadMarineToStarship(request.getStarshipId(), request.getSpaceMarineId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "unloadFromStarshipRequest")
    void unloadFromSpaceship(@RequestPayload UnloadFromStarshipRequest request) {
        starshipService.unloadAllFromStarship(request.getStarshipId());
    }

    private StarshipDto mapToStarshipDto(Starship starship) {
        var starshipDto = new StarshipDto();
        starshipDto.setId(starship.id());
        starshipDto.setName(starship.name());
        starshipDto.getMarines().addAll(starship.marines().stream().map(el -> {
            var marine = new SpaceMarineDto();
            marine.setId(el.id());
            marine.setName(el.name());
            marine.setCategory(AstartesCategory.fromValue(el.category().toString()));
            marine.setHealth(el.health());
            return marine;
        }).toList());
        return starshipDto;
    }
}
