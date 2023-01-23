package pl.edu.pg.eti.kask.rpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;
import pl.edu.pg.eti.kask.rpg.repository.StarSystemRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding character's profession entity.
 */
@Service
public class StarSystemService {

    /**
     * Repository for starSystem entity.
     */
    private StarSystemRepository repository;

    /**
     * @param repository repository for starSystem entity
     */
    @Autowired
    public StarSystemService(StarSystemRepository repository) {
        this.repository = repository;
    }

    /**
     * @return all available characters of the selected user
     */
    public List<StarSystem> findAll() {
        return repository.findAll();
    }

    /**
     * @param id id of the starSystem
     * @return container with starSystem entity
     */
    public Optional<StarSystem> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * @param name id of the starSystem
     * @return container with starSystem entity
     */
    public Optional<StarSystem> findByName(String name) {
        return repository.findByName(name);
    }


    /**
     * Stores new starSystem in the data store.
     *
     * @param starSystem new starSystem to be saved
     */
    @Transactional
    public StarSystem create(StarSystem starSystem) {
        return repository.save(starSystem);
    }

    /**
     * Updates existing starSystem.
     *
     * @param starSystem starSystem to be updated
     */
    public void update(StarSystem starSystem) {
        //repository.update(starSystem);
    }

    /**
     * Deletes existing starSystem.
     *
     * @param starSystem existing starSystem's id to be deleted
     */
    public void delete(Long starSystem) {
        repository.delete(repository.findById(starSystem).orElseThrow());
    }

    public String starSystemsToString() {
        List list = repository.findAll();
        String ret = "";
        for(int i=0; i<list.size(); i++) {
            ret += list.get(i);
            ret += "\n";
        }
        return ret;
    }

}
