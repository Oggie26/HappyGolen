package store.makejewelry.BE.model.Auth;

import lombok.Data;

import java.util.Date;

@Data
public class RegisterResponse {
    String phone;
    String fullname;
    String email;
    String gender;
    Date birthday;

}
