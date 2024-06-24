package peaksoft.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = SEQUENCE,generator = "category_gen")
    @SequenceGenerator(name = "category_gen",sequenceName = "category_seq",allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category",cascade = {ALL})
    private List<Subcategory> subcategory;
}