package pl.edu.pg.eti.kask.rpg.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity
@Table(name = "planets")
public class Planet {

    /**
     * ID of the planet
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the planet.
     */
    private String name;

    /**
     * Composition type of the planet.
     */
    private String type;

    /**
     * Star system in which the planet is located.
     */
    @ManyToOne
    @JoinColumn(name = "starSystem")
    private StarSystem starSystem;

    /**/
    public String toStringShort() {
        return "Planet(name: " + name + ")";
    }

    @Override
    public String toString() {
        if(starSystem == null) { return "Planet(name: " + name + ",\ttype: " + type + ",  \tstarSystem: " + "no system" + ")"; }
        else { return "Planet(name: " + name + ",\ttype: " + type + ",  \tstarSystem: " + starSystem.getName() + ")"; }
    }
    /**/

    /**
     * Creature's portrait. Images in database are stored as blobs (binary large objects). There is not need to fetch
     * binary data in eager way but FetchType.LAZY is only a hint for JPA, not requirement.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    private byte[] image;


}
