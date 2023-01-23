package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetFilesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class File {
        private Long id;

        private String title;
    }

    @Singular
    private List<File> files;

    public static Function<Collection<pl.edu.pg.eti.kask.rpg.entity.File>, GetFilesResponse> entityToDtoMapper() {
        return cars -> {
            GetFilesResponseBuilder response = GetFilesResponse.builder();
            cars.stream()
                    .map(file -> File.builder()
                            .id(file.getId())
                            .title(file.getTitle())
                            .build())
                    .forEach(response::file);
            return response.build();
        };
    }
}