package store.makejewelry.BE.model;

import lombok.Data;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.entity.Stone;
@Data
public class OrderRespone {
    int quantity ;
    Material material;
    Stone stone;
    Category category;
    String productName ;
    String image;
    String description ;
    String customerName ;
    Float total;
    long id;
    String phone ;
    String email ;
    String gender;
    Float size ;
    Float thickness ;
    Float weight ;



}

