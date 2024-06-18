package store.makejewelry.BE.model;

import lombok.Data;
import store.makejewelry.BE.entity.Product;

@Data
public class OrderRequest {
    String phone ;
    String email;
    long id;
    Product product;

}
