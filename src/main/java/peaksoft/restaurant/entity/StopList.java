package peaksoft.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@Table(name = "stopLists")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StopList {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "stopList_gen")
    @SequenceGenerator(name = "stopList_gen", sequenceName = "stopList_seq", allocationSize = 1)
    private Long id;
    private LocalDate date;
    private String reason;
    @OneToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH})
    private Menu menu;

}
