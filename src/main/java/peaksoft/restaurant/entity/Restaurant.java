package peaksoft.restaurant.entity;

import peaksoft.restaurant.enums.RestType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant  {
    @Id
    @GeneratedValue(strategy = SEQUENCE,generator = "restaurant_gen")
    @SequenceGenerator(name = "restaurant_gen",sequenceName = "restaurant_seq",allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private RestType restType;
    private int numberOfEmployees;
    private BigDecimal service;
    @OneToMany(mappedBy = "restaurant",cascade = {ALL})
    private List<User> userList;
    @OneToMany(mappedBy = "restaurant",cascade = {DETACH,PERSIST,MERGE,REFRESH})
    private List<Menu>menultemList;
}