package com.cleaning.cleaning.repository;

import com.cleaning.cleaning.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPelangganId(Long pelangganId);
    List<Order> findByPekerjaId(Long pekerjaId);
    List<Order> findByStatus(String status);
}
