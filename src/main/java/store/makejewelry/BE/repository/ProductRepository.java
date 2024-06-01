package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.makejewelry.BE.entity.Product;
import store.makejewelry.BE.entity.ProductTemplate;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByProductName(String productName);
    Product findProductById(long productID);
    Product findProductByIdAndIsDeleteFalse(long id);
}
