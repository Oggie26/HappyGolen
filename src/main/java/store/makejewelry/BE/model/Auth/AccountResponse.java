package store.makejewelry.BE.model.Auth;

import lombok.Data;
import store.makejewelry.BE.entity.Account;


@Data
public class AccountResponse extends Account {
    String token;

}
