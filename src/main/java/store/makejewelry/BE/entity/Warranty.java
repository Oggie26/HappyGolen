package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class Warranty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    LocalDateTime startDate;

    @Column
    LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

    @OneToOne(mappedBy = "warranty", cascade = CascadeType.ALL)
    @JsonIgnore
    Product product;

    @ManyToOne
    @JoinColumn(name = "productTemplate_id")
    @JsonIgnore
    ProductTemplate productTemplate;


}
