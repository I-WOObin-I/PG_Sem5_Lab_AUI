package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.function.Function;

/**
 * POST starSystem request. Contains only fields that can be set up byt the user while creating a new starSystem.How
 * starSystem is described is defined in {@link StarSystem} and
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostStarSystemRequest {

    /**
     * Name of the starSystem.
     */
    private String name;

    /**
     * StarSystem's type.
     */
    private Integer starCount;

    /**
     * @param starSystemFunction function for converting starSystem name to starSystem entity object
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<PostStarSystemRequest, StarSystem> dtoToEntityMapper(Function<String, StarSystem> starSystemFunction) {
        return request -> StarSystem.builder()
                .name(request.getName())
                .starCount(request.getStarCount())
                .build();
    }

}
