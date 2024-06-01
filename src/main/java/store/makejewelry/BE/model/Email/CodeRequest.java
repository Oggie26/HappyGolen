package store.makejewelry.BE.model.Email;

import lombok.Data;

@Data
public class CodeRequest  {
    String phone;
    String email;
    String newpassword;
    String checkPassword;
}
