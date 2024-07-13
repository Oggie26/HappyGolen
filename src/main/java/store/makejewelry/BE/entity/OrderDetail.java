package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    String image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    Product product;

    @ManyToOne
    @JoinColumn(name = "productTemplate_id")
    @JsonIgnore
    ProductTemplate productTemplate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;



}
