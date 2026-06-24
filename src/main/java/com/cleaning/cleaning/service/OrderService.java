package com.cleaning.cleaning.service;

import com.cleaning.cleaning.model.*;
import com.cleaning.cleaning.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PelangganRepository pelangganRepository;

    @Autowired
    private PekerjaRepository pekerjaRepository;

    @Autowired
    private LayananRepository layananRepository;

    public List<Order> getSemuaOrder() {
        return orderRepository.findAll();
    }

    public Order buatOrder(Long pelangganId, Long layananId, String alamatTujuan, Long pekerjaId) {
        Pelanggan pelanggan = pelangganRepository.findById(pelangganId)
                .orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));
        Layanan layanan = layananRepository.findById(layananId)
                .orElseThrow(() -> new RuntimeException("Layanan tidak ditemukan"));

        Order order = new Order();
        long count = orderRepository.count();
        order.setIdOrder(String.format("ORD-%03d", count + 1));
        order.setPelanggan(pelanggan);
        order.setLayanan(layanan);
        order.setAlamatTujuan(alamatTujuan);
        order.setStatus("pending");

        Pekerja pekerja = null;
        if (pekerjaId != null) {
            pekerja = pekerjaRepository.findById(pekerjaId).orElse(null);
        }
        if (pekerja == null) {
            // Fallback: ambil pekerja available dengan rating tertinggi
            List<Pekerja> available = pekerjaRepository.findByStatus("available");
            pekerja = available.stream()
                    .max(java.util.Comparator.comparingDouble(Pekerja::getRating))
                    .orElse(null);
        }
        if (pekerja != null) {
            order.setPekerja(pekerja);
            pekerja.setStatus("busy");
            pekerjaRepository.save(pekerja);
            order.setStatus("diproses");
        }

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));
    }

    public Order updateStatusOrder(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        if ("selesai".equals(status) && order.getPekerja() != null) {
            Pekerja pekerja = order.getPekerja();
            pekerja.setStatus("available");
            pekerjaRepository.save(pekerja);
        }
        return orderRepository.save(order);
    }

    public Order berikanRating(Long id, int nilai, String komentar) {
        Order order = getOrderById(id);
        order.berikanRating(nilai, komentar);
        if (order.getPekerja() != null) {
            Pekerja pekerja = order.getPekerja();
            List<Order> orderPekerja = orderRepository.findByPekerjaId(pekerja.getId());
            double rataRating = orderPekerja.stream()
                    .filter(o -> o.getNilaiRating() > 0)
                    .mapToInt(Order::getNilaiRating)
                    .average()
                    .orElse(nilai);
            pekerja.setRating(rataRating);
            pekerjaRepository.save(pekerja);
        }
        return orderRepository.save(order);
    }

    public void batalkanOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus("dibatalkan");
        if (order.getPekerja() != null) {
            Pekerja pekerja = order.getPekerja();
            pekerja.setStatus("available");
            pekerjaRepository.save(pekerja);
        }
        orderRepository.save(order);
    }
}
