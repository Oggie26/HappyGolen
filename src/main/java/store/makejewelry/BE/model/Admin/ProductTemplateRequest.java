package store.makejewelry.BE.model.Admin;

import lombok.Data;


@Data
public class ProductTemplateRequest  {
    String productName;
    Float price;
    String date;
    String image;
    Float size ;
    Float weight;
    String content;
    int quantity ;
    Boolean status;
    long id;
    long materialId ;
    long categoryId ;

}
