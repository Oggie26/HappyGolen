package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.makejewelry.BE.entity.ProductTemplate;

public interface ProductTemplateRepository extends JpaRepository<ProductTemplate,Long> {
    ProductTemplate findProductTemplateByProductName(String productName);
    ProductTemplate findProductTemplateById(long productID);
    ProductTemplate findProductTemplateByIdAndIsDeleteFalse(long id);

}
