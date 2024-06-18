package store.makejewelry.BE.model.Auth;

import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {
    String phone;
    String password;
    String fullname;
    String email;
    String gender;
    Date birthday;
    String address;
}
