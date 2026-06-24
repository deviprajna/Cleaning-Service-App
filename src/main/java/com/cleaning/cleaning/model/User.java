package com.cleaning.cleaning.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idUser;
    private String nama;
    private String email;
    private String noHP;
    private String password;
    private String role; // "PELANGGAN" atau "PEKERJA"

    public boolean login(String email, String password) {
        return this.email != null && this.email.equals(email)
                && this.password != null && this.password.equals(password);
    }

    public void updateProfil(String nama, String email, String noHP) {
        this.nama = nama;
        this.email = email;
        this.noHP = noHP;
    }
}
