package store.makejewelry.BE.model.Email.Admin;

import lombok.Data;

@Data
public class MaterialRequest {

    long id ;
    Boolean status ;
    String name ;
    Float weight;
    Float price ;
}
