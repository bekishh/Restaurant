package peaksoft.restaurant.repository;

import peaksoft.restaurant.entity.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque,Long> {

    @Query("select sum (m.price)from User u join u.chequeList c join c.menuList m where u.id=:userId ")
    BigDecimal getTopByCreatedAt( Long userId);

    @Query("select avg(c.priceAvg) from Cheque c join User u on c.user.id = u.id" +
            " join Restaurant r on u.restaurant.id = r.id where r.id = :restaurantId")
    BigDecimal avg( Long restaurantId);

    Optional<Cheque>deleteChequeById(Long chequeId);
}