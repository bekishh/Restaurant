package peaksoft.restaurant.repository;

import peaksoft.restaurant.entity.Menu;
import peaksoft.restaurant.entity.StopList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface StopListRepository extends JpaRepository<StopList,Long> {

    boolean existsStopListByDate(LocalDate date);
    boolean existsByMenu_Id(Long id);
    boolean existsStopListByMenu(Menu menu);
    List<StopList> findByMenuId (Long menuId);
    boolean existsStopListByDateAndMenuId(LocalDate date, Long menuId);
}