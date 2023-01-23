package pl.edu.pg.eti.kask.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.kask.rpg.dto.*;
import pl.edu.pg.eti.kask.rpg.entity.Planet;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;
import pl.edu.pg.eti.kask.rpg.service.PlanetService;
import pl.edu.pg.eti.kask.rpg.service.StarSystemService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * REST controller for starSystem resource. It does not return or receive entity objects but dto objects which present
 * only those fields which are converted to JSON.
 */
@RestController
@RequestMapping("api/starSystems")
public class StarSystemController {

    /**
     * Service for managing starSystems.
     */
    private StarSystemService starSystemService;
    private PlanetService planetService;

    /**
     * @param starSystemService  service for managing starSystems
     */
    @Autowired
    public StarSystemController(StarSystemService starSystemService, PlanetService planetService) {
        this.starSystemService = starSystemService;
        this.planetService = planetService;
    }

    /**
     * @return list of starSystems which will be converted to JSON
     */
    @GetMapping
    public ResponseEntity<GetStarSystemsResponse> getStarSystems() {
        List<StarSystem> all = starSystemService.findAll();
        Function<Collection<StarSystem>, GetStarSystemsResponse> mapper = GetStarSystemsResponse.entityToDtoMapper();
        GetStarSystemsResponse response = mapper.apply(all);
        return ResponseEntity.ok(response);
    }

    /**
     * @return single starSystem in JSON format or 404 when starSystem does not exist
     */
    @GetMapping("{starSystemName}")
    public ResponseEntity<GetStarSystemResponse> getStarSystem(@PathVariable("starSystemName") String starSystemName) {
        return  starSystemService.findByName(starSystemName)
                .map(value -> ResponseEntity.ok(GetStarSystemResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * @param request new starSystem parsed from JSON
     * @param builder URI builder
     * @return response with location header
     */
    @PostMapping
    public ResponseEntity<Void> postStarSystem(@RequestBody PostStarSystemRequest request, UriComponentsBuilder builder) {
        StarSystem starSystem = PostStarSystemRequest
                .dtoToEntityMapper(name -> starSystemService.findByName(name).orElseThrow())
                .apply(request);
        starSystem = starSystemService.create(starSystem);
        return ResponseEntity.created(builder.pathSegment("api", "starSystems", "{id}")
                .buildAndExpand(starSystem.getId()).toUri()).build();
    }

    /**
     * Deletes selected starSystem.
     * @return accepted for not found if starSystem does not exist
     */
    @DeleteMapping("{starSystemName}")
    public ResponseEntity<Void> deleteStarSystem(@PathVariable("starSystemName") String starSystemName) {
        Optional<StarSystem> starSystem = starSystemService.findByName(starSystemName);
        if (starSystem.isPresent()) {
            starSystemService.delete(starSystem.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{starSystemName}/planets")
    public ResponseEntity<GetPlanetsResponse> getPlanets(@PathVariable("starSystemName") String starSystemName) {
        Optional<StarSystem> user = starSystemService.findByName(starSystemName);
        return user.map(value -> ResponseEntity.ok(GetPlanetsResponse.entityToDtoMapper().apply(planetService.findAll(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("{starSystemName}/planets")
    public ResponseEntity<Void> postPlanet(@PathVariable("starSystemName") String starSystemName,
                                              @RequestBody PostStarSystemPlanetRequest request,
                                              UriComponentsBuilder builder) {
        Optional<StarSystem> starSystem = starSystemService.findByName(starSystemName);
        if (starSystem.isPresent()) {
            Planet planet = PostStarSystemPlanetRequest
                    .dtoToEntityMapper(starSystem::get)
                    .apply(request);
            planet = planetService.create(planet);
            return ResponseEntity.created(builder.pathSegment("api", "users", "{username}", "characters", "{id}")
                    .buildAndExpand(starSystem.get().getName(), planet.getId()).toUri()).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates existing starSystem.
     *
     * @param request starSystem's data parsed from JSON
     * @return accepted or not found if starSystem does not exist
     */
    @PutMapping("{starSystemName}")
    public ResponseEntity<Void> putStarSystem(@RequestBody PutStarSystemRequest request, @PathVariable("starSystemName") String starSystemName) {
        Optional<StarSystem> starSystem = starSystemService.findByName(starSystemName);
        if (starSystem.isPresent()) {
            PutStarSystemRequest.dtoToEntityUpdater().apply(starSystem.get(), request);
            starSystemService.update(starSystem.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
