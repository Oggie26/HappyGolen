package store.makejewelry.BE.model;

import lombok.Data;
import store.makejewelry.BE.entity.Category;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.entity.Stone;

import java.util.Date;

@Data
public class DetailProduct {
    long id;
    String productName ;
    String image ;
    Float price ;
    Float weight ;
    Float thickness ;
    String description;
    Float size ;
    Material material;
    Category category;
    Stone stone;
}
