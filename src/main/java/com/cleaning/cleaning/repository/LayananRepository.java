package com.cleaning.cleaning.repository;

import com.cleaning.cleaning.model.Layanan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayananRepository extends JpaRepository<Layanan, Long> {
}
