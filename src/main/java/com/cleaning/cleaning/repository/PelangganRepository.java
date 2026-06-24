package com.cleaning.cleaning.repository;

import com.cleaning.cleaning.model.Pelanggan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {
    java.util.Optional<Pelanggan> findByEmail(String email);
}
