package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.Planet;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.function.BiFunction;

/**
 * PUT planet request. Contains all fields that can be updated by the user. .How planet is described is defined in
 * {@link Planet}
 * classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutPlanetRequest {

    /**
     * Planet's name.
     */
    private String name;

    /**
     * Planet's type.
     */
    private String type;

    /**
     * planets's starSystem.
     */
    private StarSystem starSystem;

    /**
     * @return updater for convenient updating entity object using dto object
     */
    public static BiFunction<Planet, PutPlanetRequest, Planet> dtoToEntityUpdater() {
        return (planet, request) -> {
            planet.setName(request.getName());
            planet.setType(request.getType());
            return planet;
        };
    }
}
