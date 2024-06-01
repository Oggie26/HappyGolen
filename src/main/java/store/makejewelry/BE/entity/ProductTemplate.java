package store.makejewelry.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Setter
@Getter
@Entity
public class ProductTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
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
    int quantity;

    @Column
    Float size;

    @Column
    Float weight;

    @Column
    String description;

    boolean isDelete = false;
}
