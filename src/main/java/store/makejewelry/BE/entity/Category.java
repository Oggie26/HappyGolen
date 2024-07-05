package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @Column(unique = true)
    String name;

    @Column
    Boolean status;

    @OneToMany(mappedBy = "category")
            @JsonIgnore
    List<Product> product;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    List<ProductTemplate> productTemplate ;
}
