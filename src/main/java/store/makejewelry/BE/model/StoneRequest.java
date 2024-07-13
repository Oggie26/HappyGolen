package store.makejewelry.BE.model;

import lombok.Data;

@Data
public class StoneRequest {
    long id ;
    String name ;
    Boolean status;
    Float price ;
}
