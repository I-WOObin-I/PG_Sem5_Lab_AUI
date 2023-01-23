package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import pl.edu.pg.eti.kask.rpg.entity.File;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetFileResponse {
    private Long id;
    private String title;
    private String author;

    public static Function<File, GetFileResponse> entityToDtoMapper() {
        return file -> GetFileResponse.builder()
                .id(file.getId())
                .title(file.getTitle())
                .author(file.getAuthor())
                .build();
    }
}
