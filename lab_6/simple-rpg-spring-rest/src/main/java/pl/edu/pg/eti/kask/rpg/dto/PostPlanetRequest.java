package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.Planet;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * POST planet request. Contains only fields that can be set up byt the user while creating a new planet.How
 * planet is described is defined in {@link Planet} and
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostPlanetRequest {

    /**
     * Name of the planet.
     */
    private String name;

    /**
     * Planet's type.
     */
    private String type;

    /**
     * planets's starSystem.
     */
    private String starSystem;

    /**
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<PostPlanetRequest, Planet> dtoToEntityMapper(Function<String, StarSystem> starSystemFunction) {
        return request -> Planet.builder()
                .name(request.getName())
                .type(request.getType())
                .starSystem(starSystemFunction.apply(request.getStarSystem()))
                .build();
    }

}
