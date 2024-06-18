package store.makejewelry.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import store.makejewelry.BE.enums.OrderStatus;

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
    Date orderDate ;

    @Column
    Date handDate ;

    @Column
    String productName;

    @Enumerated(EnumType.STRING)
    OrderStatus status ;

    @Column
    String staffName ;

    @Column
    String customerName ;

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetail;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "order")
    private List<ProcessOrder> processOrders;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    Payment payment;


}

