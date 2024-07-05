package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
