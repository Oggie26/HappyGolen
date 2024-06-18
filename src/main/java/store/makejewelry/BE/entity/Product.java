package store.makejewelry.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id ;

    @Column(unique = true)
    String productName ;

    @Column
    String image ;

    @Column
    Float price;

    @Column
    Float weight;

    @Column
    Date date ;

    @Column(nullable = false)
    Boolean status;

    @Column
    String description;

    @Column
    Float size;

    @Column
    int quantity;

    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetail;

    @ManyToOne
    @JoinColumn(name = "material_id")
    Material material;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
