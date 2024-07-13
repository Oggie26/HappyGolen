package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(long id);

    @Query(value = "SELECT p.*, c.name , m.name, s.name FROM product p " +
            "JOIN order_detail od ON od.product_id = p.id " +
            "JOIN orders o ON o.id = od.order_id " +
            "JOIN category c ON c.id = p.category_id " +
            "JOIN material m ON m.id = p.material_id " +
            "JOIN stone s ON s.id = p.stone_id " +
            "WHERE o.id = :orderId", nativeQuery = true)
    Product findProductByOrderId(@Param("orderId") long orderId);
}
