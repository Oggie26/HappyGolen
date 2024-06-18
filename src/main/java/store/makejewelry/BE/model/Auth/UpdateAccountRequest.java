package store.makejewelry.BE.model.Auth;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateAccountResponse {
    String fullname;
    String email;
    String gender;
    Date birthday;
    String password;
    String address;

}
