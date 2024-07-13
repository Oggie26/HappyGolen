package store.makejewelry.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.makejewelry.BE.entity.Warranty;

public interface WarrantyRepository extends JpaRepository<Warranty , Long> {
}
