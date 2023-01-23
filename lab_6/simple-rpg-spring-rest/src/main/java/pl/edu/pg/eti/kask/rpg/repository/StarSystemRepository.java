package pl.edu.pg.eti.kask.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.Optional;

/**
 * Repository for profession entity. Repositories should be used in business layer (e.g.: in services).
 */
@Repository
public interface StarSystemRepository extends JpaRepository<StarSystem, Long> {

    Optional<StarSystem> findByName(String name);

}
