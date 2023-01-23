package pl.edu.pg.eti.kask.rpg.dto;


import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.Planet;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.function.Function;

/**
 * GET planet response. It contains all field that can be presented (but not necessarily changed) to the used. How
 * planet is described is defined in {@link Planet} and
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPlanetResponse {

    /**
     * Unique id identifying planet.
     */
    private Long id;

    /**
     * Name of the planet.
     */
    private String name;

    /**
     * Planet's background story.
     */
    private String type;

    /**
     * planets's starSystem.
     */
    private String starSystem;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Planet, GetPlanetResponse> entityToDtoMapper() {
        return planet -> GetPlanetResponse.builder()
                .id(planet.getId())
                .name(planet.getName())
                .type(planet.getType())
                .starSystem(planet.getStarSystem().getName())
                .build();
    }

}
