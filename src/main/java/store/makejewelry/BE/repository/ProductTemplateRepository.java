package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.ProductTemplate;

import java.util.ArrayList;
import java.util.List;

public interface ProductTemplateRepository extends JpaRepository<ProductTemplate,Long> {
    ProductTemplate findProductTemplateByProductName(String productName);
    ProductTemplate findProductTemplateById(long productID);
    List<ProductTemplate> findProductTemplateByProductNameLike(String productName);

    default List<ProductTemplate> searchByProductNameAndProductId(String productName, long productId) {
        List<ProductTemplate> results = new ArrayList<>();

        if (productName != null && !productName.isEmpty()) {
            List<ProductTemplate> productsByName = findProductTemplateByProductNameLike("%" + productName + "%");
            results.addAll(productsByName);
        }

        if (productId > 0) {
            ProductTemplate productById = findProductTemplateById(productId);
            if (productById != null) {
                results.add(productById);
            }
        }

        return results;
    }

    @Query(value = "SELECT p.*, c.name , m.name, s.name FROM productTemplate p " +
            "JOIN order_detail od ON od.productTemplate_id = p.id " +
            "JOIN orders o ON o.id = od.order_id " +
            "JOIN category c ON c.id = p.category_id " +
            "JOIN material m ON m.id = p.material_id " +
            "JOIN stone s ON s.id = p.stone_id " +
            "WHERE o.id = :orderId", nativeQuery = true)
    ProductTemplate findProductTemplateByOrderId(@Param("orderId") long orderId);
}
