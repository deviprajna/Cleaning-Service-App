package com.cleaning.cleaning.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PEMBAYARAN")
@Data
public class Pembayaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idPembayaran;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    private String metode;
    private double totalBayar;
    private String statusPembayaran = "pending";

    public boolean prosesPembayaran(long count) {
        this.idPembayaran = String.format("PAY-%03d", count + 1);
        this.statusPembayaran = "berhasil";
        if (this.order != null) {
            this.order.updateStatus("selesai");
        }
        return true;
    }
}
