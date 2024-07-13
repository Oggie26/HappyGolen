package store.makejewelry.BE.model;

import lombok.Data;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.entity.ProductTemplate;
import store.makejewelry.BE.entity.Stone;

@Data
public class ProductTemplateDetail {
    ProductTemplate productTemplate;
    Material material;
    Stone stone;
    Category category;
}
