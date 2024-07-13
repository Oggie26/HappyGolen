package store.makejewelry.BE.model;

import lombok.Data;

@Data
public class BlogRequest {
    long id;
    String blogName ;
    String image ;
    String content ;
    Boolean status ;
    String description ;
}
