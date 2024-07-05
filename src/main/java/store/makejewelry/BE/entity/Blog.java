package store.makejewelry.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    String blogName;

    @Column
    String link;

    @Column(nullable = false)
    Boolean status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;
}
