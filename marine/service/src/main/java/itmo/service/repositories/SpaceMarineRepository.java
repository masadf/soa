package itmo.service.repositories;


import itmo.service.repositories.entities.SpaceMarineEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceMarineRepository extends CrudRepository<SpaceMarineEntity, Integer> {
    List<SpaceMarineEntity> findAll();
}
