package com.cleaning.cleaning.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LAYANAN")
@Data
public class Layanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idLayanan;
    private String namaLayanan;
    private double hargaDasar;
    private String deskripsi;
}
