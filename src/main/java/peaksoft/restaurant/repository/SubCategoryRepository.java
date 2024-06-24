package peaksoft.restaurant.repository;

import peaksoft.restaurant.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<Subcategory,Long> {
}