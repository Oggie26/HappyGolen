package store.makejewelry.BE.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import store.makejewelry.BE.enums.RoleEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(unique = true)
    String phone;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Column
    String fullName ;

    @Column(unique = true)
    String email;

    @Column
    Date birthday;

    @Column
    String gender;

    @Column(nullable = false)
    Boolean status;

    @Column
    String address;

    @Enumerated(EnumType.STRING)
    RoleEnum role;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Order> orders =  new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "account")
            @JsonIgnore
    List<ProcessOrder> processOrder;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Warranty> warranty;


}
