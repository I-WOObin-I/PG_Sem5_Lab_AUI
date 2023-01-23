package pl.edu.pg.eti.kask.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.kask.rpg.entity.Planet;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.List;
import java.util.Optional;

/**
 * Repository for planet entity. Repositories should be used in business layer (e.g.: in services).
 */
@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {


    Optional<Planet> findByName(String name);

    List<Planet> findAllByStarSystem(StarSystem starSystem);
}
