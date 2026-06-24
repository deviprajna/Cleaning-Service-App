package com.cleaning.cleaning.config;

import com.cleaning.cleaning.model.*;
import com.cleaning.cleaning.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private LayananRepository layananRepository;
    @Autowired private PelangganRepository pelangganRepository;
    @Autowired private PekerjaRepository pekerjaRepository;
    @Autowired private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {

        if (layananRepository.count() == 0) {
            Layanan l1 = new Layanan(); l1.setIdLayanan("L001"); l1.setNamaLayanan("Cleaning Rumah"); l1.setHargaDasar(150000); l1.setDeskripsi("Pembersihan standar seluruh ruangan rumah");
            Layanan l2 = new Layanan(); l2.setIdLayanan("L002"); l2.setNamaLayanan("Deep Cleaning"); l2.setHargaDasar(350000); l2.setDeskripsi("Pembersihan mendalam termasuk sofa, karpet, dan dapur");
            Layanan l3 = new Layanan(); l3.setIdLayanan("L003"); l3.setNamaLayanan("Cleaning Kantor"); l3.setHargaDasar(250000); l3.setDeskripsi("Pembersihan ruang kerja dan area kantor");
            Layanan l4 = new Layanan(); l4.setIdLayanan("L004"); l4.setNamaLayanan("Cleaning Kaca"); l4.setHargaDasar(120000); l4.setDeskripsi("Pembersihan kaca jendela dalam dan luar");
            layananRepository.saveAll(Arrays.asList(l1, l2, l3, l4));
        }

        if (pekerjaRepository.count() == 0) {
            // Rating TIDAK di-hardcode — akan dihitung dari order nyata di bawah
            Pekerja p1 = new Pekerja(); p1.setIdUser("WKR-001"); p1.setNama("Budi Santoso"); p1.setEmail("budi@cleanup.id"); p1.setPassword("budi123"); p1.setRole("PEKERJA"); p1.setNoHP("081234567890"); p1.setRating(0); p1.setStatus("available"); p1.setListKeahlian(Arrays.asList("Cleaning Rumah", "Deep Cleaning"));
            Pekerja p2 = new Pekerja(); p2.setIdUser("WKR-002"); p2.setNama("Sari Dewi"); p2.setEmail("sari@cleanup.id"); p2.setPassword("sari123"); p2.setRole("PEKERJA"); p2.setNoHP("082345678901"); p2.setRating(0); p2.setStatus("available"); p2.setListKeahlian(Arrays.asList("Cleaning Kantor", "Cleaning Kaca"));
            Pekerja p3 = new Pekerja(); p3.setIdUser("WKR-003"); p3.setNama("Agus Pratama"); p3.setEmail("agus@cleanup.id"); p3.setPassword("agus123"); p3.setRole("PEKERJA"); p3.setNoHP("083456789012"); p3.setRating(0); p3.setStatus("available"); p3.setListKeahlian(Arrays.asList("Deep Cleaning", "Cleaning Kaca"));
            pekerjaRepository.saveAll(Arrays.asList(p1, p2, p3));
        }

        if (pelangganRepository.count() == 0) {
            Pelanggan c1 = new Pelanggan(); c1.setIdUser("PLG-001"); c1.setNama("Devi Prajnaparamita"); c1.setEmail("devi@email.com"); c1.setPassword("devi123"); c1.setRole("PELANGGAN"); c1.setNoHP("089876543210"); c1.setMetodePembayaran("e-wallet"); c1.setAlamatTersimpan("Jl. Pamulang No. 12"); c1.setSaldoWallet(500000);
            pelangganRepository.save(c1);
        }

        // Seed order + rating agar rating pekerja konsisten dan bisa diverifikasi
        if (orderRepository.count() == 0) {
            Pelanggan devi = pelangganRepository.findAll().get(0);
            List<Pekerja> pekerjaList = pekerjaRepository.findAll();
            List<Layanan> layananList = layananRepository.findAll();

            Pekerja budi = pekerjaList.stream().filter(p -> p.getEmail().equals("budi@cleanup.id")).findFirst().orElse(null);
            Pekerja sari = pekerjaList.stream().filter(p -> p.getEmail().equals("sari@cleanup.id")).findFirst().orElse(null);
            Pekerja agus = pekerjaList.stream().filter(p -> p.getEmail().equals("agus@cleanup.id")).findFirst().orElse(null);

            Layanan cleaning = layananList.get(0);
            Layanan deep = layananList.get(1);
            Layanan kantor = layananList.get(2);

            // Seed 3 order selesai untuk Budi → rating 5, 5, 4 → rata-rata 4.67
            Order o1 = buatOrderSelesai("ORD-001", devi, budi, cleaning, "Jl. Pamulang No. 12", 5, "Bersih banget, ramah!");
            Order o2 = buatOrderSelesai("ORD-002", devi, budi, deep, "Jl. Pamulang No. 12", 5, "Sangat profesional");
            Order o3 = buatOrderSelesai("ORD-003", devi, budi, cleaning, "Jl. Melati No. 5", 4, "Bagus, tepat waktu");
            orderRepository.saveAll(Arrays.asList(o1, o2, o3));

            // Hitung dan simpan rating Budi
            double ratingBudi = (5 + 5 + 4) / 3.0;
            budi.setRating(ratingBudi);
            pekerjaRepository.save(budi);

            // Seed 2 order selesai untuk Sari → rating 5, 4 → rata-rata 4.5
            Order o4 = buatOrderSelesai("ORD-004", devi, sari, kantor, "Jl. Sudirman No. 88", 5, "Kantor jadi sangat rapi");
            Order o5 = buatOrderSelesai("ORD-005", devi, sari, kantor, "Jl. Sudirman No. 88", 4, "Cukup memuaskan");
            orderRepository.saveAll(Arrays.asList(o4, o5));

            double ratingSari = (5 + 4) / 2.0;
            sari.setRating(ratingSari);
            pekerjaRepository.save(sari);

            // Seed 1 order selesai untuk Agus → rating 4 → rata-rata 4.0
            Order o6 = buatOrderSelesai("ORD-006", devi, agus, deep, "Jl. Anggrek No. 3", 4, "Pekerjaan lumayan bagus");
            orderRepository.save(o6);

            agus.setRating(4.0);
            pekerjaRepository.save(agus);
        }

        System.out.println("Data awal berhasil dimuat!");
    }

    private Order buatOrderSelesai(String idOrder, Pelanggan pelanggan, Pekerja pekerja,
                                    Layanan layanan, String alamat, int rating, String komentar) {
        Order o = new Order();
        o.setIdOrder(idOrder);
        o.setPelanggan(pelanggan);
        o.setPekerja(pekerja);
        o.setLayanan(layanan);
        o.setAlamatTujuan(alamat);
        o.setStatus("selesai");
        o.setNilaiRating(rating);
        o.setKomentar(komentar);
        return o;
    }
}
