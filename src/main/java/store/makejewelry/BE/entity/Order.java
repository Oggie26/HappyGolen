package store.makejewelry.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import store.makejewelry.BE.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id ;

    @Column
    Float total ;

    @Column
    LocalDateTime orderDate ;

    @Column
    LocalDateTime handDate;

    @Column
    String productName;

    @Column
    String image;

    @Column
    String imageTemplate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Account customer;

    String message;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetail = new ArrayList<>();

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<ProcessOrder> processOrders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "payment_id")
    Payment payment;


}

