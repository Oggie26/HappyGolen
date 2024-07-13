package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.entity.Material;

import java.util.ArrayList;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material ,Long> {
    Material findMaterialById(long id);
    Material findMaterialByName(String name);

    @Query(value = "SELECT * FROM material c WHERE c.id = :param OR c.name like concat('%',:param,'%')" , nativeQuery = true)
    List<Material> findByIdOrNameQuery(@Param("param") String param);
}
