package pl.edu.pg.eti.kask.rpg.configuration;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.kask.rpg.entity.File;
import pl.edu.pg.eti.kask.rpg.entity.Planet;
import pl.edu.pg.eti.kask.rpg.entity.StarSystem;
import pl.edu.pg.eti.kask.rpg.service.FileService;
import pl.edu.pg.eti.kask.rpg.service.PlanetService;
import pl.edu.pg.eti.kask.rpg.service.StarSystemService;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * Listener started automatically on CDI application context initialized. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@Component
public class InitializedData {

    /**
     * Service for characters operations.
     */
    private final StarSystemService starSystemService;


    /**
     * Service for professions operations.
     */
    private final PlanetService planetService;


    private final FileService fileService;
    private final Environment environment;





    /**
     *
     * @param starSystemService service for managing characters
     * @param planetService service for managing professions
     */
    @Autowired
    public InitializedData(StarSystemService starSystemService, PlanetService planetService, FileService fileService, Environment environment) {
        this.starSystemService = starSystemService;
        this.planetService = planetService;
        this.fileService = fileService;
        this.environment = environment;
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should
     * be created only once.
     */
    @PostConstruct
    private synchronized void init() {

        StarSystem tasale = StarSystem.builder()
                .name("Tasale")
                .starCount(1)
                .build();
        starSystemService.create(tasale);

        Planet beregale = Planet.builder()
                .name("Beregale")
                .type("rock")
                .starSystem(tasale)
                .image(getResourceAsByteArray("avatar/Beregale.png"))
                .build();
        Planet illium = Planet.builder()
                .name("Illium")
                .type("rock")
                .starSystem(tasale)
                .image(getResourceAsByteArray("avatar/Illium.png"))
                .build();
        Planet naxell = Planet.builder()
                .name("Naxell")
                .type("ice giant")
                .starSystem(tasale)
                .image(getResourceAsByteArray("avatar/Naxell.png"))
                .build();

        planetService.create(beregale);
        planetService.create(illium);
        planetService.create(naxell);


        StarSystem pax = StarSystem.builder()
                .name("Pax")
                .starCount(1)
                .build();
        starSystemService.create(pax);

        Planet svarog = Planet.builder()
                .name("Svarog")
                .type("gas giant")
                .starSystem(pax)
                .image(getResourceAsByteArray("avatar/Svarog.png"))
                .build();
        Planet noveria = Planet.builder()
                .name("Noveria")
                .type("rock")
                .starSystem(pax)
                .image(getResourceAsByteArray("avatar/Noveria.png"))
                .build();
        Planet morana = Planet.builder()
                .name("Morana")
                .type("planetoid")
                .starSystem(pax)
                .image(getResourceAsByteArray("avatar/Morana.png"))
                .build();

        planetService.create(svarog);
        planetService.create(noveria);
        planetService.create(morana);

        String fileStorage = environment.getProperty("fileTransfer.fileStorage.path");
        System.out.println(fileStorage);

        File normandy = File.builder()
                .title("Normandy")
                .author("Admin")
                .filePath(fileStorage + "/normandy.jpg")
                .build();

        fileService.create(normandy);

    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }

}
