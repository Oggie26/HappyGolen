package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.makejewelry.BE.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment , Long> {
}
