package peaksoft.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@Table(name = "cheques")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cheque {
    @Id
    @GeneratedValue(strategy = SEQUENCE,generator = "cheque_gen")
    @SequenceGenerator(name = "cheque_gen",sequenceName = "cheque_seq",allocationSize = 1)
    private Long id;
    private LocalDate createdAt;
    private BigDecimal priceAvg;
    @ManyToMany(cascade = {MERGE,PERSIST,DETACH,REFRESH})
    private List<Menu> menuList;
    @ManyToOne(cascade = {ALL})
    private User user;
}