package store.makejewelry.BE.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RechargeRequestDTO {
   private String amount;
   long orderId;
}
