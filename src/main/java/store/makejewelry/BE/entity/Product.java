package store.makejewelry.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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

    @Column
    Boolean status;

    @Column
    String description;

    @Column
    Float size;

    @Column
    int quantity;

    Boolean isDelete = false ;
}
