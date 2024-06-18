package store.makejewelry.BE.model.Admin;
import lombok.Data;
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
    long materialId;
    long categoryId;
}
