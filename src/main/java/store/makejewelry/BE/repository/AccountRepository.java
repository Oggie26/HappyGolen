package store.makejewelry.BE.repository;

import org.eclipse.angus.mail.imap.ACL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.Account;
import store.makejewelry.BE.entity.Category;

import java.util.ArrayList;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByPhone(String phone);
    Account findAccountById(long id);
    Account findByEmail(String email);

    List<Account> searchByPhoneLike( String phone);

    default List<Account> searchByPhoneAndId(String phone, long id) {
        List<Account> results = new ArrayList<>();

        if (phone != null && !phone.isEmpty()) {
            List<Account> accountsByPhone = searchByPhoneLike("%" + phone + "%");
            results.addAll(accountsByPhone);
        }

        if (id > 0) {
            Account account = findAccountById(id);
            if (account != null) {
                results.add(account);
            }
        }

        return results;
    }

}
