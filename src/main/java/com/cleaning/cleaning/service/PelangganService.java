package com.cleaning.cleaning.service;

import com.cleaning.cleaning.model.Pelanggan;
import com.cleaning.cleaning.repository.PelangganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PelangganService {

    @Autowired
    private PelangganRepository pelangganRepository;

    public List<Pelanggan> getSemuaPelanggan() {
        return pelangganRepository.findAll();
    }

    public Pelanggan daftarPelanggan(Pelanggan pelanggan) {
        long count = pelangganRepository.count();
        String idBaru = String.format("PLG-%03d", count + 1);
        pelanggan.setIdUser(idBaru);
        return pelangganRepository.save(pelanggan);
    }

    public Pelanggan getPelangganById(Long id) {
        return pelangganRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));
    }

    public void hapusPelanggan(Long id) {
        pelangganRepository.deleteById(id);
    }
}
