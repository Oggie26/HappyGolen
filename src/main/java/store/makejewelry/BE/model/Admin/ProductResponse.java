package store.makejewelry.BE.model.Admin;

import lombok.Data;

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
    long materialID ;
    long categoryId ;
}
