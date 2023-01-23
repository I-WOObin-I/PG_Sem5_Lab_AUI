package pl.edu.pg.eti.kask.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.kask.rpg.entity.Planet;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;
import pl.edu.pg.eti.kask.rpg.repository.PlanetRepository;
import pl.edu.pg.eti.kask.rpg.repository.StarSystemRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding planet entity.
 */
@Service
public class PlanetService {

    /**
     * Repository for planet entity.
     */
    private PlanetRepository planetRepository;

    /**
     * Repository for planet entity.
     */
    private StarSystemRepository starSystemRepository;

    /**
     * @param planetRepository repository for planet entity
     * @param starSystemRepository repository for starSystem entity
     */
    @Autowired
    public PlanetService(PlanetRepository planetRepository, StarSystemRepository starSystemRepository) {
        this.planetRepository = planetRepository;
        this.starSystemRepository = starSystemRepository;
    }

    /**
     * Finds single planet.
     *
     * @param id planet's id
     * @return container with planet
     */
    public Optional<Planet> find(Long id) {
        return planetRepository.findById(id);
    }

    public Optional<Planet> findByName(String name) { return planetRepository.findByName(name); }

    /**
     * @return all available planets
     */
    public List<Planet> findAll() {
        return planetRepository.findAll();
    }

    /**
     * @param starSystem existing starSystem, planet's location
     * @return all available planets of the selected starSystem
     */
    public List<Planet> findAll(StarSystem starSystem) {
        return planetRepository.findAllByStarSystem(starSystem);
    }

    /**
     * Creates new planet.
     *
     * @param planet new planet
     * @return updated entity
     */
    @Transactional
    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

    /**
     * Updates existing planet.
     *
     * @param planet planet to be updated
     */
    @Transactional
    public void update(Planet planet) {
        planetRepository.save(planet);
    }

    /**
     * Deletes existing planet.
     *
     * @param planet existing planet's id to be deleted
     */
    @Transactional
    public void delete(Long planet) {
        planetRepository.deleteById(planet);
    }

    /**
     * Updates portrait of the planet.
     *
     * @param id planet's id
     * @param is input stream containing new portrait
     */
    @Transactional
    public void updateImage(Long id, InputStream is) {
        planetRepository.findById(id).ifPresent(planet -> {
            try {
                planet.setImage(is.readAllBytes());
//                repository.update(planet);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public String planetsToString() {
        List list = planetRepository.findAll();
        String ret = "";
        for(int i=0; i<list.size(); i++) {
            ret += list.get(i);
            ret += "\n";
        }
        return ret;
    }

}
