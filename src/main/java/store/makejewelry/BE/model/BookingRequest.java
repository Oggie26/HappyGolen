package store.makejewelry.BE.model;

import lombok.Data;

@Data
public class BookingRequest {
    int quantity ;
    float size ;
    float thickness ;
    long productTemplateId ;
}
