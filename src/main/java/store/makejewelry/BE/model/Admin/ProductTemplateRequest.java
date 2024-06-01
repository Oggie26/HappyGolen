package store.makejewelry.BE.model.Admin;

import lombok.Data;
import store.makejewelry.BE.entity.ProductTemplate;

import java.util.Date;

@Data
public class ProductTemplateRequest  {
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

}
