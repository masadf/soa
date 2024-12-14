package itmo.spaceshipservice.domain.services;

import itmo.spaceshipservice.domain.clients.SpaceMarineClient;
import itmo.spaceshipservice.domain.dto.SpaceMarine;
import itmo.spaceshipservice.domain.dto.Starship;
import itmo.spaceshipservice.domain.exceptions.MarineNotFoundException;
import itmo.spaceshipservice.domain.exceptions.StarshipNotFoundException;
import itmo.spaceshipservice.domain.repositories.StarshipRepository;
import itmo.spaceshipservice.domain.repositories.entities.StarshipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StarshipService {
    @Autowired
    private StarshipRepository starshipRepository;

    @Autowired
    private SpaceMarineClient spaceMarineClient;

    public List<Starship> getStarships() {
        var starships = starshipRepository.findAll();

        return starships.stream().map(
                el -> new Starship(
                        el.getId(),
                        el.getName(),
                        findSpaceMarines(el.getMarines())
                )
        ).sorted(Comparator.comparing(Starship::id)).toList();
    }

    public Starship getStarship(Integer starshipId) {
        var starshipOptional = starshipRepository.findById(starshipId);
        if (starshipOptional.isEmpty()) throw new StarshipNotFoundException("Корабль с id=" + starshipId + " не найден");
        var starship = starshipOptional.get();

        return new Starship(
                starship.getId(),
                starship.getName(),
                findSpaceMarines(starship.getMarines())
        );
    }

    public Integer addStarship(String name) {
        var starshipEntity = new StarshipEntity();
        starshipEntity.setName(name);
        starshipEntity.setMarines(new HashSet<>());
        var starship = starshipRepository.save(starshipEntity);

        return starship.getId();
    }

    public void deleteStarship(Integer starshipId) {
        starshipRepository.deleteById(starshipId);
    }

    public Starship updateStarship(Integer starshipId, String name) {
        var starshipOptional = starshipRepository.findById(starshipId);
        if (starshipOptional.isEmpty()) throw new StarshipNotFoundException("Корабль с id=" + starshipId + " не найден");
        var starship = starshipOptional.get();

        starship.setName(name);
        starshipRepository.save(starship);

        return new Starship(
                starship.getId(),
                starship.getName(),
                findSpaceMarines(starship.getMarines())
        );
    }

    public void loadMarineToStarship(Integer starshipId, Integer marineId) {
        var starshipOptional = starshipRepository.findById(starshipId);
        if (starshipOptional.isEmpty()) throw new StarshipNotFoundException("Корабль с id=" + starshipId + " не найден");
        var starship = starshipOptional.get();
        var marine = spaceMarineClient.getSpaceMarine(marineId);
        if (marine == null) throw new MarineNotFoundException("Десантник с id=" + marineId + " не найден");
        Collections.addAll(starship.getMarines(), marine.id());

        starshipRepository.save(starship);
    }

    public void unloadAllFromStarship(Integer starshipId) {
        var starship = starshipRepository.findById(starshipId).get();
        starship.setMarines(new HashSet<>());

        starshipRepository.save(starship);
    }

    private List<SpaceMarine> findSpaceMarines(Set<Integer> ids) {
        return ids.stream().map(marineId -> spaceMarineClient.getSpaceMarine(marineId)).filter(Objects::nonNull).toList();
    }
}
