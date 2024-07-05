package store.makejewelry.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    int quantity ;

    @Column
    Float price ;

    @Column
    String productName ;

    @Column
    String productTemplateName ;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "productTemplate_id")
    ProductTemplate productTemplate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;



}
