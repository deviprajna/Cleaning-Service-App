package com.cleaning.cleaning.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ORDERS")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pelanggan_id")
    @JsonIgnoreProperties("riwayatPesanan")
    private Pelanggan pelanggan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pekerja_id")
    private Pekerja pekerja;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "layanan_id")
    private Layanan layanan;

    private String alamatTujuan;
    private String status = "pending";
    private int nilaiRating;
    private String komentar;

    public void updateStatus(String status) {
        this.status = status;
    }

    public void berikanRating(int nilai, String komentar) {
        if (nilai >= 1 && nilai <= 5) {
            this.nilaiRating = nilai;
            this.komentar = komentar;
        }
    }
}
