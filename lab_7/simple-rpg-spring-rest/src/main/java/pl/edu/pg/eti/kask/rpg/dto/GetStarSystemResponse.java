package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.Planet;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * GET starSystem response. Described details about selected starSystem. Can be used to present description while
 * planet creation or on planet's stat page. How starSystem is described is defined in
 * {@link StarSystem}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetStarSystemResponse {

    /**
     * Unique id identifying starSystem.
     */
    private Long id;

    /**
     * Name of the starSystem.
     */
    private String name;

    /**
     * StarSystem's background story.
     */
    private Integer starCount;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<StarSystem, GetStarSystemResponse> entityToDtoMapper() {
        return starSystem -> GetStarSystemResponse.builder()
                .id(starSystem.getId())
                .name(starSystem.getName())
                .starCount(starSystem.getStarCount())
                .build();
    }

}
