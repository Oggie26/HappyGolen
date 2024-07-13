package store.makejewelry.BE.repository;

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

    @Query(value = "SELECT * FROM account c WHERE c.id = :param OR c.phone like concat('%',:param,'%')" , nativeQuery = true)
    List<Account> findByIdOrNameQuery(@Param("param") String param);

    @Query(value = "SELECT * FROM account c WHERE c.id = :param OR c.phone like concat('%',:param,'%') "  , nativeQuery = true)
    List<Account> findByStaff(@Param("param") String param);

}
