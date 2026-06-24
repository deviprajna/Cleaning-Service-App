package com.cleaning.cleaning.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PELANGGAN")
@Data
@EqualsAndHashCode(callSuper = true)
public class Pelanggan extends User {

    private String metodePembayaran;
    private String alamatTersimpan;
    private double saldoWallet;

    @OneToMany(mappedBy = "pelanggan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> riwayatPesanan = new ArrayList<>();
}
