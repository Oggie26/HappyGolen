package store.makejewelry.BE.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import store.makejewelry.BE.enums.OrderStatus;

@Data
public class OrderRequestDTO {

    long idOrder;

    long idAccountAssigne;
    float price;
    String message;


    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    String imageDesigner;


}
