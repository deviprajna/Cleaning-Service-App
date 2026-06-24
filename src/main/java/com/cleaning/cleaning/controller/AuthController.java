package com.cleaning.cleaning.controller;

import com.cleaning.cleaning.model.Pelanggan;
import com.cleaning.cleaning.model.Pekerja;
import com.cleaning.cleaning.model.User;
import com.cleaning.cleaning.repository.PelangganRepository;
import com.cleaning.cleaning.repository.PekerjaRepository;
import com.cleaning.cleaning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private PelangganRepository pelangganRepository;
    @Autowired private PekerjaRepository pekerjaRepository;

    // ========== LOGIN ==========
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email dan password wajib diisi"));
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Email tidak ditemukan"));
        }

        User user = userOpt.get();
        if (!password.equals(user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Password salah"));
        }

        Map<String, Object> response = buildUserResponse(user);
        return ResponseEntity.ok(response);
    }

    // ========== REGISTER ==========
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> req) {
        String nama = (String) req.get("nama");
        String email = (String) req.get("email");
        String password = (String) req.get("password");
        String noHP = (String) req.get("noHP");
        String role = (String) req.get("role"); // "PELANGGAN" atau "PEKERJA"

        if (nama == null || email == null || password == null || role == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Semua field wajib diisi"));
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email sudah terdaftar"));
        }

        User savedUser;

        if ("PELANGGAN".equalsIgnoreCase(role)) {
            long count = pelangganRepository.count();
            Pelanggan p = new Pelanggan();
            p.setIdUser(String.format("PLG-%03d", count + 1));
            p.setNama(nama);
            p.setEmail(email);
            p.setPassword(password);
            p.setNoHP(noHP != null ? noHP : "");
            p.setRole("PELANGGAN");
            p.setMetodePembayaran("e-wallet");
            p.setAlamatTersimpan("");
            p.setSaldoWallet(0);
            savedUser = pelangganRepository.save(p);

        } else if ("PEKERJA".equalsIgnoreCase(role)) {
            long count = pekerjaRepository.count();
            Pekerja p = new Pekerja();
            p.setIdUser(String.format("WKR-%03d", count + 1));
            p.setNama(nama);
            p.setEmail(email);
            p.setPassword(password);
            p.setNoHP(noHP != null ? noHP : "");
            p.setRole("PEKERJA");
            p.setRating(0.0);
            p.setStatus("available");
            savedUser = pekerjaRepository.save(p);

        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Role tidak valid. Gunakan PELANGGAN atau PEKERJA"));
        }

        Map<String, Object> response = buildUserResponse(savedUser);
        return ResponseEntity.ok(response);
    }

    // ========== HELPER ==========
    private Map<String, Object> buildUserResponse(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("idUser", user.getIdUser());
        map.put("nama", user.getNama());
        map.put("email", user.getEmail());
        map.put("noHP", user.getNoHP());
        map.put("role", user.getRole());

        if (user instanceof Pelanggan p) {
            map.put("saldoWallet", p.getSaldoWallet());
            map.put("alamatTersimpan", p.getAlamatTersimpan());
            map.put("metodePembayaran", p.getMetodePembayaran());
        } else if (user instanceof Pekerja p) {
            map.put("rating", p.getRating());
            map.put("status", p.getStatus());
            map.put("listKeahlian", p.getListKeahlian());
        }

        return map;
    }
}
