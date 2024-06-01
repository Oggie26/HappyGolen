package store.makejewelry.BE.model.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    String phone;
    String password;
}
