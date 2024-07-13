package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.makejewelry.BE.entity.Account;
import store.makejewelry.BE.entity.Material;
import store.makejewelry.BE.entity.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderById(long id);

    @Query(value = "SELECT * FROM orders c WHERE c.id = :param OR c.customer_name like concat('%',:param,'%')" , nativeQuery = true)
    List<Order> findByIdOrNameQuery(@Param("param") String param);

    @Query(value = "SELECT o.* FROM orders o JOIN process_order po ON o.id = po.order_Id WHERE po.account_Id = :accountId", nativeQuery = true)
    List<Order> getAllOrderByAccountId(@Param("accountId") long accountId);

    List<Order> findOrderByCustomer(Account customer);

}

