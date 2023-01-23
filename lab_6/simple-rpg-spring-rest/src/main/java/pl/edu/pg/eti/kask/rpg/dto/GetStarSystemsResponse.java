package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * GET starSystems response. Contains list of available starSystems. Can be used to list particular user's starSystems as
 * well as all starSystems in the game.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetStarSystemsResponse {

    /**
     * Represents single starSystem in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class StarSystemEntry {

        /**
         * Unique id identifying starSystem.
         */
        private Long id;

        /**
         * Name of the starSystem.
         */
        private String name;

    }

    /**
     * Name of the selected starSystems.
     */
    @Singular
    private List<StarSystemEntry> starSystems;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<StarSystem>, GetStarSystemsResponse> entityToDtoMapper() {
        return starSystems -> {
            GetStarSystemsResponseBuilder response = GetStarSystemsResponse.builder();
            starSystems.stream()
                    .map(starSystem -> StarSystemEntry.builder()
                            .id(starSystem.getId())
                            .name(starSystem.getName())
                            .build())
                    .forEach(response::starSystem);
            return response.build();
        };
    }

}
