package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    Boolean status;

    @Column
    Float price ;

    @Column(unique = true)
    String name ;

    @Column
    Float weight ;

    @OneToMany(mappedBy = "material")
    @JsonIgnore
    List<Product> product;

    @OneToMany(mappedBy = "material")
    @JsonIgnore
    List<ProductTemplate> productTemplate;

}
