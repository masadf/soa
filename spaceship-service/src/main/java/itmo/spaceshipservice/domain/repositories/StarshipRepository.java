package itmo.spaceshipservice.domain.repositories;


import itmo.spaceshipservice.domain.repositories.entities.StarshipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarshipRepository extends CrudRepository<StarshipEntity, Integer> {
    List<StarshipEntity> findAll();
}
