package store.makejewelry.BE.model;

import lombok.Data;
import store.makejewelry.BE.entity.Product;

@Data
public class OrderRequest {
    int quantity ;
    long materialId ;
    long categoryId ;
    long stoneId ;
    String productName ;
    Float weightStone ;
    Float size ;
    String description ;
    Float thickness ;
    String imageTemplate ;

}
