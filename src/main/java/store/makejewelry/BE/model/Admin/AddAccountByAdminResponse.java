package store.makejewelry.BE.model.Admin;

import lombok.Data;
import store.makejewelry.BE.enums.RoleEnum;

import java.util.Date;
@Data
public class AddAccountByAdminResponse {
    String phone;
    String fullName;
    String email;
    String gender;
    Date birthday;
    String address;
    RoleEnum roleEnum;
    Boolean status ;

}
