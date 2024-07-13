package store.makejewelry.BE.model.Email.Admin;

import lombok.Data;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.entity.Order;
import store.makejewelry.BE.entity.Stone;

import java.util.Date;

@Data
public class ProductResponse {
    int quantity;
    Float size;
    Boolean status;
    String image;
    Date date;
    Float price ;
    String productName;
    Float weight;
    String content;
    long id;
    Material material;
    Category category;
    Stone stone;
    Order order;
}
