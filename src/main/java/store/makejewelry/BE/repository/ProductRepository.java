package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.makejewelry.BE.entity.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByProductName(String productName);
    Product findProductById(long id);
//    List<Product> findProductByIdOrProductName(long id, String productName);
}
