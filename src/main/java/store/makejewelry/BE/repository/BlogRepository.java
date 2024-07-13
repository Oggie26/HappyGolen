package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.Blog;
import store.makejewelry.BE.entity.Category;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findBlogById(long id);

    @Query(value = "SELECT * FROM blog c WHERE c.id = :param OR c.name like concat('%',:param,'%')" , nativeQuery = true)
    List<Blog> findByIdOrNameQuery(@Param("param") String param);
}
