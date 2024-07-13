package store.makejewelry.BE.model.Email.Admin;

import lombok.Data;

import java.util.Date;


@Data
public class ProductTemplateRequest  {
    long materialId ;
    long categoryId ;
    long stoneId ;
    String productName ;
    Float weightStone ;
    Float size ;
    Float thickness ;
    Date date;
    String image ;
    Boolean status ;
    String content;
    long id;
}
