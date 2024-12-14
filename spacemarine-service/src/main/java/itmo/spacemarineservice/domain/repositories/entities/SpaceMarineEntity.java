package itmo.spacemarineservice.domain.repositories.entities;

import itmo.spacemarineservice.domain.dto.AstartesCategory;
import itmo.spacemarineservice.domain.dto.MeleeWeapon;
import itmo.spacemarineservice.domain.dto.Weapon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "marine")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpaceMarineEntity {
    @Id
    @GeneratedValue(generator = "marine_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "marine_generator", sequenceName = "marine_sequence", allocationSize = 1)
    Integer id;
    String name;
    float x;
    long y;
    ZonedDateTime creationDate;
    double health;
    AstartesCategory category;
    Weapon weaponType;
    MeleeWeapon meleeWeapon;
    String chapter;
}
