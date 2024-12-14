package itmo.spaceshipservice.domain.repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "starship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StarshipEntity {
    @Id
    @GeneratedValue(generator = "starship_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "starship_generator", sequenceName = "starship_sequence", allocationSize = 1)
    Integer id;
    String name;
    @Convert(converter = MarinesIdConverter.class)
    Set<Integer> marines;
}
