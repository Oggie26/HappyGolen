package store.makejewelry.BE.model.Auth;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateAccountResponse {
    String phone ;
    String fullName;
    Date birthday;
    String address;
    String gender;
    String email;
    String password;
}
