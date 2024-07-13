package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Stone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    Float price ;

    @Column(unique = true)
    String name ;

    @Column
    Boolean status ;

    @OneToMany(mappedBy = "stone")
    @JsonIgnore
    List<ProductTemplate> productTemplate;

    @OneToMany(mappedBy = "stone")
    @JsonIgnore
    List<Product> product ;

}
