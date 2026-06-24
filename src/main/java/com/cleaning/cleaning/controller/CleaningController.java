package com.cleaning.cleaning.controller;

import com.cleaning.cleaning.model.*;
import com.cleaning.cleaning.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CleaningController {

    @Autowired private LayananService layananService;
    @Autowired private PelangganService pelangganService;
    @Autowired private PekerjaService pekerjaService;
    @Autowired private OrderService orderService;
    @Autowired private PembayaranService pembayaranService;

    // ========== LAYANAN ==========
    @GetMapping("/layanan")
    public List<Layanan> getAllLayanan() { return layananService.getSemuaLayanan(); }

    @PostMapping("/layanan")
    public Layanan tambahLayanan(@RequestBody Layanan layanan) { return layananService.tambahLayanan(layanan); }

    @DeleteMapping("/layanan/{id}")
    public String hapusLayanan(@PathVariable Long id) {
        layananService.hapusLayanan(id);
        return "Layanan berhasil dihapus";
    }

    // ========== PELANGGAN ==========
    @GetMapping("/pelanggan")
    public List<Pelanggan> getAllPelanggan() { return pelangganService.getSemuaPelanggan(); }

    @PostMapping("/pelanggan")
    public Pelanggan daftarPelanggan(@RequestBody Pelanggan pelanggan) { return pelangganService.daftarPelanggan(pelanggan); }

    @DeleteMapping("/pelanggan/{id}")
    public String hapusPelanggan(@PathVariable Long id) {
        pelangganService.hapusPelanggan(id);
        return "Pelanggan berhasil dihapus";
    }

    // ========== PEKERJA ==========
    @GetMapping("/pekerja")
    public List<Pekerja> getAllPekerja() { return pekerjaService.getSemuaPekerja(); }

    @PostMapping("/pekerja")
    public Pekerja daftarPekerja(@RequestBody Pekerja pekerja) { return pekerjaService.daftarPekerja(pekerja); }

    @DeleteMapping("/pekerja/{id}")
    public String hapusPekerja(@PathVariable Long id) {
        pekerjaService.hapusPekerja(id);
        return "Pekerja berhasil dihapus";
    }

    // ========== ORDER ==========
    @GetMapping("/order")
    public List<Order> getAllOrder() { return orderService.getSemuaOrder(); }

    @PostMapping("/order")
    public Order buatOrder(@RequestBody Map<String, Object> req) {
        Long pelangganId = Long.valueOf(req.get("pelangganId").toString());
        Long layananId = Long.valueOf(req.get("layananId").toString());
        String alamat = req.get("alamatTujuan").toString();
        Long pekerjaId = req.containsKey("pekerjaId") && req.get("pekerjaId") != null
                ? Long.valueOf(req.get("pekerjaId").toString()) : null;
        return orderService.buatOrder(pelangganId, layananId, alamat, pekerjaId);
    }

    @PutMapping("/order/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestBody Map<String, String> req) {
        return orderService.updateStatusOrder(id, req.get("status"));
    }

    @PutMapping("/order/{id}/rating")
    public Order berikanRating(@PathVariable Long id, @RequestBody Map<String, Object> req) {
        int nilai = Integer.parseInt(req.get("nilai").toString());
        String komentar = req.get("komentar").toString();
        return orderService.berikanRating(id, nilai, komentar);
    }

    @DeleteMapping("/order/{id}")
    public String batalkanOrder(@PathVariable Long id) {
        orderService.batalkanOrder(id);
        return "Order dibatalkan";
    }

    // ========== PEMBAYARAN ==========
    @GetMapping("/pembayaran")
    public List<Pembayaran> getAllPembayaran() { return pembayaranService.getSemuaPembayaran(); }

    @PostMapping("/pembayaran")
    public Pembayaran prosesPembayaran(@RequestBody Map<String, Object> req) {
        Long orderId = Long.valueOf(req.get("orderId").toString());
        String metode = req.get("metode").toString();
        return pembayaranService.prosesPembayaran(orderId, metode);
    }
}
