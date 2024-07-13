package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import store.makejewelry.BE.enums.OrderStatus;
import store.makejewelry.BE.enums.RoleEnum;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Getter
@Setter
public class ProcessOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    OrderStatus status;

    LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;
}
