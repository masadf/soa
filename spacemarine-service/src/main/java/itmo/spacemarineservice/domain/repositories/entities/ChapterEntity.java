package itmo.spacemarineservice.domain.repositories.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chapter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChapterEntity {
    @Id
    String name;
    int marinesCount;
}
