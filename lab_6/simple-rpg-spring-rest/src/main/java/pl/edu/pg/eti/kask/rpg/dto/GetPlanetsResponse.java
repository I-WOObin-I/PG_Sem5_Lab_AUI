package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.Planet;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * GET planets response. Contains list of available planets. Can be used to list particular user's planets as
 * well as all planets in the game.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPlanetsResponse {

    /**
     * Represents single planet in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class PlanetEntry {

        /**
         * Unique id identifying planet.
         */
        private Long id;

        /**
         * Name of the planet.
         */
        private String name;

    }

    /**
     * Name of the selected planets.
     */
    @Singular
    private List<PlanetEntry> planets;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<Planet>, GetPlanetsResponse> entityToDtoMapper() {
        return planets -> {
            GetPlanetsResponseBuilder response = GetPlanetsResponse.builder();
            planets.stream()
                    .map(planet -> PlanetEntry.builder()
                            .id(planet.getId())
                            .name(planet.getName())
                            .build())
                    .forEach(response::planet);
            return response.build();
        };
    }

}
