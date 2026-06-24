package com.cleaning.cleaning.service;

import com.cleaning.cleaning.model.Layanan;
import com.cleaning.cleaning.repository.LayananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LayananService {

    @Autowired
    private LayananRepository layananRepository;

    public List<Layanan> getSemuaLayanan() {
        return layananRepository.findAll();
    }

    public Layanan tambahLayanan(Layanan layanan) {
        // Auto-generate idLayanan kalau belum diisi
        if (layanan.getIdLayanan() == null || layanan.getIdLayanan().isBlank()) {
            long count = layananRepository.count() + 1;
            layanan.setIdLayanan(String.format("L%03d", count));
        }
        return layananRepository.save(layanan);
    }

    public void hapusLayanan(Long id) {
        layananRepository.deleteById(id);
    }
}