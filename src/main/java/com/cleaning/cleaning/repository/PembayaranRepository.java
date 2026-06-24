package com.cleaning.cleaning.repository;

import com.cleaning.cleaning.model.Pembayaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PembayaranRepository extends JpaRepository<Pembayaran, Long> {
}
