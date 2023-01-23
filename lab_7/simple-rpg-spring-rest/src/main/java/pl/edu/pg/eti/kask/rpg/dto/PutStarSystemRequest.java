package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.function.BiFunction;

/**
 * PUT starSystem request. Contains all fields that can be updated by the user. .How starSystem is described is defined in
 * {@link StarSystem}
 * classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutStarSystemRequest {

    /**
     * StarSystem's name.
     */
    private String name;

    /**
     * StarSystem's type.
     */
    private Integer starCount;

    /**
     * @return updater for convenient updating entity object using dto object
     */
    public static BiFunction<StarSystem, PutStarSystemRequest, StarSystem> dtoToEntityUpdater() {
        return (starSystem, request) -> {
            starSystem.setName(request.getName());
            starSystem.setStarCount(request.getStarCount());
            return starSystem;
        };
    }
}
