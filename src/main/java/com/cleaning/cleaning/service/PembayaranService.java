package com.cleaning.cleaning.service;

import com.cleaning.cleaning.model.*;
import com.cleaning.cleaning.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PembayaranService {

    @Autowired
    private PembayaranRepository pembayaranRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Pembayaran prosesPembayaran(Long orderId, String metode) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));

        Pembayaran pembayaran = new Pembayaran();
        pembayaran.setOrder(order);
        pembayaran.setMetode(metode);
        pembayaran.setTotalBayar(order.getLayanan() != null ? order.getLayanan().getHargaDasar() : 0);
        
        long count = pembayaranRepository.count(); // tambah ini
        pembayaran.prosesPembayaran(count);

        orderRepository.save(order);
        return pembayaranRepository.save(pembayaran);
    }

    public List<Pembayaran> getSemuaPembayaran() {
        return pembayaranRepository.findAll();
    }
}
