package store.makejewelry.BE.model.Auth;

import lombok.Data;

@Data
public class LoginResponse {
    String phone;
    String token;
}
