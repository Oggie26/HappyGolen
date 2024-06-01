package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.makejewelry.BE.entity.Account;

import java.util.ArrayList;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByPhone(String phone);
    Account findAccountById(long id);
    Account findByEmail(String email);
}
