package pl.edu.pg.eti.kask.rpg.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity
@Table(name = "star_systems")
public class StarSystem implements Serializable {

    /**
     * Id of the system
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the system.
     */
    private String name;

    /**
     * Number of stars in the system.
     */
    private Integer starCount;

    @OneToMany(mappedBy = "starSystem", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Planet> planets;

    @Override
    public String toString() {
        return "StarSystem(id: " + id + ", name: " + name + ")";
    }

}
