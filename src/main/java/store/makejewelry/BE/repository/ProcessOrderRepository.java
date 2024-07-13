package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.makejewelry.BE.entity.ProcessOrder;

public interface ProcessOrderRepository extends JpaRepository<ProcessOrder , Long> {
}
