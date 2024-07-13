package store.makejewelry.BE.model.Email.Admin;
import lombok.Data;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.entity.Stone;

import java.util.Date;

@Data
public class ProductRequest {
    String productName;
    Float price;
    Date date;
    String image;
    Float size ;
    Float weight;
    String content;
    int quantity ;
    Boolean status;
    long id;
    Float thickness ;
    Material material;
    Category category;
    Stone stone;
}
