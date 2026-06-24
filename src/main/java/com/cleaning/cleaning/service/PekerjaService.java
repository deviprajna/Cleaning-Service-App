package com.cleaning.cleaning.service;

import com.cleaning.cleaning.model.Pekerja;
import com.cleaning.cleaning.repository.PekerjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PekerjaService {

    @Autowired
    private PekerjaRepository pekerjaRepository;

    public List<Pekerja> getSemuaPekerja() {
        return pekerjaRepository.findAll();
    }

    public Pekerja daftarPekerja(Pekerja pekerja) {
        long count = pekerjaRepository.count();
        String idBaru = String.format("WKR-%03d", count + 1); // WKR-001, WKR-002, dst
        pekerja.setIdUser(idBaru);
        pekerja.setStatus("available");
        return pekerjaRepository.save(pekerja);
    }

    public List<Pekerja> getPekerjaAvailable() {
        return pekerjaRepository.findByStatus("available");
    }

    public Pekerja getPekerjaById(Long id) {
        return pekerjaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pekerja tidak ditemukan"));
    }

    public void hapusPekerja(Long id) {
        pekerjaRepository.deleteById(id);
    }
}
