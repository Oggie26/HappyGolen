package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
@Setter
@Getter
@Entity
public class ProductTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    String productName;

    @Column
    Float price ;

    @Column
    Date date;

    @Column
    String image;

    @Column(nullable = false)
    Boolean status;

    @Column
    Float size;

    @Column
    Float weight;

    @Column
    String description;

    @Column
    Float thickness ;

    @OneToMany(mappedBy = "productTemplate")
    List<OrderDetail> orderDetail;

    @ManyToOne
    @JoinColumn(name = "material_id")
    Material material;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "stone_id")
    Stone stone;

    @OneToMany(mappedBy = "productTemplate")
    @JsonIgnore
    List<Warranty> warranty;
}
