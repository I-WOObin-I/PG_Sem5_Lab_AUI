package pl.edu.pg.eti.kask.rpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pg.eti.kask.rpg.dto.CreateFileRequest;
import pl.edu.pg.eti.kask.rpg.dto.GetFileResponse;
import pl.edu.pg.eti.kask.rpg.dto.GetFilesResponse;
import pl.edu.pg.eti.kask.rpg.entity.File;
import pl.edu.pg.eti.kask.rpg.service.FileService;
import pl.edu.pg.eti.kask.rpg.utils.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RestController
@RequestMapping("api/files")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping()
    public ResponseEntity<GetFilesResponse> getFiles() {
        return ResponseEntity
                .ok(GetFilesResponse.entityToDtoMapper().apply(fileService.findAll()));
    }

    @GetMapping(value = "{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getFile(@PathVariable("id") Long id) {
        Optional<File> file = fileService.find(id);

        if (file.isEmpty()) {
            System.out.println("file was empty");
            return ResponseEntity.notFound().build();
        }

        File existingFile = file.get();
        System.out.printf("File path: %s\n", existingFile.getFilePath());
        if (!Files.exists(Path.of(existingFile.getFilePath()))) {
            System.out.println("File does not exist");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(FileUtils.readAllBytes(existingFile.getFilePath()));
    }

    @GetMapping("{id}/details")
    public ResponseEntity<GetFileResponse> getFileDetail(@PathVariable("id") Long id) {
        Optional<File> file = fileService.find(id);

        return file.map(value -> ResponseEntity.ok(GetFileResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createFile(@ModelAttribute CreateFileRequest request, UriComponentsBuilder builder) {
        File newFile = CreateFileRequest
                .dtoToEntityMapper()
                .apply(request);

        Optional<File> createdFile = fileService.create(newFile, request.getContent());

        return createdFile.map(file -> ResponseEntity
                .created(builder
                        .pathSegment("api", "files", "{id}").buildAndExpand(file.getId()).toUri())
                .body(file.getId())).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
