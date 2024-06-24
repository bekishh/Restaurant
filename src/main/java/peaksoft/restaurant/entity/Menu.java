package peaksoft.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@Table(name = "menus")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu  {
    @Id
    @GeneratedValue(strategy = SEQUENCE,generator = "menu_gen")
    @SequenceGenerator(name = "menu_gen",sequenceName = "menu_seq",allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private boolean isVegetarian;
    @ManyToOne(cascade = {REFRESH,MERGE,DETACH,PERSIST})
    private Restaurant restaurant;
    @ManyToOne(cascade = {ALL})
    private Subcategory subcategory;
    @OneToOne (mappedBy = "menu",cascade = {ALL})
    private StopList stopList;
    @ManyToMany(mappedBy = "menuList", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, REMOVE})
    private List<Cheque> chequeList;
}