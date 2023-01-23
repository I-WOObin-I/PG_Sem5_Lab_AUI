package pl.edu.pg.eti.kask.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.eti.kask.rpg.entity.File;

@org.springframework.stereotype.Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
