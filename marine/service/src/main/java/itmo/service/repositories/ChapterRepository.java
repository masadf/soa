package itmo.service.repositories;


import itmo.service.repositories.entities.ChapterEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChapterRepository extends CrudRepository<ChapterEntity, String> {
    @Query("select c from ChapterEntity c where c.name = ?1")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ChapterEntity> findByNameForUpdate(String name);
}
