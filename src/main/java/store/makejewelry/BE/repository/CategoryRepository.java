package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.makejewelry.BE.entity.Category;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {
    Category findCategoryById(long id);
    Category findCategoryByName(String name);
    @Query(value = "SELECT * FROM category c WHERE c.id = :param OR c.name like concat('%',:param,'%')" , nativeQuery = true)
    List<Category> findByIdOrNameQuery(@Param("param") String param);

}
