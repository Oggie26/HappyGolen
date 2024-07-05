package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.makejewelry.BE.entity.Order;

import java.util.ArrayList;

public interface OrderRepository extends JpaRepository<Order, Long> {
    ArrayList<Order> findOrderByCustomerName (String name);
    ArrayList<Order> findOrderByStatus(int status);
    ArrayList<Order> findOrderById(long id);
}
