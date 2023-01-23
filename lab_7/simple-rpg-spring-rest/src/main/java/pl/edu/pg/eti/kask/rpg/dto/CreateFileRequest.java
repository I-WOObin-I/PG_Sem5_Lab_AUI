package pl.edu.pg.eti.kask.rpg.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pg.eti.kask.rpg.entity.File;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateFileRequest {
    private String title;

    private String author;

    private MultipartFile content;

    public static Function<CreateFileRequest, File> dtoToEntityMapper() {
        return request -> File.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .build();
    }
}
