package pl.edu.pg.eti.kask.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.kask.rpg.dto.GetPlanetResponse;
import pl.edu.pg.eti.kask.rpg.dto.GetPlanetsResponse;
import pl.edu.pg.eti.kask.rpg.dto.PostPlanetRequest;
import pl.edu.pg.eti.kask.rpg.dto.PutPlanetRequest;
import pl.edu.pg.eti.kask.rpg.service.PlanetService;
import pl.edu.pg.eti.kask.rpg.service.StarSystemService;
import pl.edu.pg.eti.kask.rpg.entity.Planet;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * REST controller for planet resource. It does not return or receive entity objects but dto objects which present
 * only those fields which are converted to JSON.
 */
@RestController
@RequestMapping("api/planets")
public class PlanetController {

    /**
     * Service for managing planets.
     */
    private PlanetService planetService;

    /**
     * Service for managing starSystems.
     */
    private StarSystemService starSystemService;

    /**
     * @param planetService  service for managing planets
     * @param starSystemService service for managing starSystems
     */
    @Autowired
    public PlanetController(PlanetService planetService, StarSystemService starSystemService) {
        this.planetService = planetService;
        this.starSystemService = starSystemService;
    }

    /**
     * @return list of planets which will be converted to JSON
     */
    @GetMapping
    public ResponseEntity<GetPlanetsResponse> getPlanets() {
//        return ResponseEntity.ok(GetPlanetsResponse.entityToDtoMapper().apply(planetService.findAll()));
        List<Planet> all = planetService.findAll();
        Function<Collection<Planet>, GetPlanetsResponse> mapper = GetPlanetsResponse.entityToDtoMapper();
        GetPlanetsResponse response = mapper.apply(all);
        return ResponseEntity.ok(response);
    }

    /**
     * @param id id of the planet
     * @return single planet in JSON format or 404 when planet does not exist
     */
    @GetMapping("{id}")
    public ResponseEntity<GetPlanetResponse> getPlanet(@PathVariable("id") long id) {
        return  planetService.find(id)
                .map(value -> ResponseEntity.ok(GetPlanetResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * @param request new planet parsed from JSON
     * @param builder URI builder
     * @return response with location header
     */
    @PostMapping
    public ResponseEntity<Void> postPlanet(@RequestBody PostPlanetRequest request, UriComponentsBuilder builder) {
        Planet planet = PostPlanetRequest
                .dtoToEntityMapper(name -> starSystemService.findByName(name).orElseThrow())
                .apply(request);
        planet = planetService.create(planet);
        return ResponseEntity.created(builder.pathSegment("api", "planets", "{id}")
                .buildAndExpand(planet.getId()).toUri()).build();
    }

    /**
     * Deletes selected planet.
     *
     * @param id planet's id
     * @return accepted for not found if planet does not exist
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable("id") long id) {
        Optional<Planet> planet = planetService.find(id);
        if (planet.isPresent()) {
            planetService.delete(planet.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates existing planet.
     *
     * @param request planet's data parsed from JSON
     * @param id      planet's id
     * @return accepted or not found if planet does not exist
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> putPlanet(@RequestBody PutPlanetRequest request, @PathVariable("id") long id) {
        Optional<Planet> planet = planetService.find(id);
        if (planet.isPresent()) {
            PutPlanetRequest.dtoToEntityUpdater().apply(planet.get(), request);
            planetService.update(planet.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param id planet's id
     * @return planet's image or not found if planet does not exist
     */
    @GetMapping(value = "{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPlanetImage(@PathVariable("id") long id) {
        Optional<Planet> planet = planetService.find(id);
        return planet.map(value -> ResponseEntity.ok(value.getImage()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates planets image.
     *
     * @param id       planet's id
     * @param image named multipart form part (parameter)
     * @return accepted or not found if planet does not exist
     * @throws IOException on any I/O error
     */
    @PutMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> putPlanetImage(@PathVariable("id") long id,
                                                     @RequestParam("image") MultipartFile image) throws IOException {
        Optional<Planet> planet = planetService.find(id);
        if (planet.isPresent()) {
            planetService.updateImage(planet.get().getId(), image.getInputStream());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
