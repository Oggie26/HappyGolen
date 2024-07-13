package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column()
    String productName ;

    @Column
    String image ;

    @Column
    Float price;

    @Column
    Float weight;

    @Column
    Float thickness ;
    @Column
    Date date ;

    @Column(nullable = false)
    Boolean status;

    @Column
    String description;

    @Column
    Float size;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetail;

    @ManyToOne
    @JoinColumn(name = "material_id")
    @JsonIgnore
    Material material;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    Category category;

    @ManyToOne
    @JoinColumn(name = "stone_id")
    @JsonIgnore
    Stone stone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warranty_id", unique = true)
    @JsonIgnore
    Warranty warranty;
}
