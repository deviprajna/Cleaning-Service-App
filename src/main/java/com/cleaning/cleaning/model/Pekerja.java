package com.cleaning.cleaning.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PEKERJA")
@Data
@EqualsAndHashCode(callSuper = true)
public class Pekerja extends User {

    private double rating;
    private String status = "available";

    @ElementCollection
    @CollectionTable(name = "PEKERJA_KEAHLIAN", joinColumns = @JoinColumn(name = "pekerja_id"))
    @Column(name = "keahlian")
    private List<String> listKeahlian = new ArrayList<>();

    public void terimaPesanan(String idOrder) {
        this.status = "busy";
    }

    public void selesaikanPesanan(String idOrder) {
        this.status = "available";
    }
}
