package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.Stone;

import java.util.List;

public interface StoneRepository extends JpaRepository<Stone, Long> {
    Stone findStoneById(long id);
    Stone findStoneByName (String name );

    @Query(value = "SELECT * FROM stone c WHERE c.id = :param OR c.name like concat('%',:param,'%')" , nativeQuery = true)
    List<Stone> findByIdOrNameQuery(@Param("param") String param);
}
