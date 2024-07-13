package store.makejewelry.BE.model.Email.Admin;

import lombok.Data;
import store.makejewelry.BE.enums.RoleEnum;

import java.util.Date;
@Data
public class AddAccountByAdminRequest {
    String phone;
    String password;
    String fullname;
    String email;
    String gender;
    Date birthday;
    String address;
    RoleEnum roleEnum;
}
