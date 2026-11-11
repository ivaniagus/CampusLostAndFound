package com.mycompany.campust_lf;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    
    public void jalankan() {
        boolean jalan = true;
        while(jalan) {
            tampilkanMenu();
            int pilihan = bacaPilihan();
            
            switch (pilihan) {
                case 1:
                    laporBarangHilang();
                    break;
                case 2:
                    laporBarangDitemukan();
                    break;
                case 3:
                    System.out.println(">> \nTerima kasih telah menggunakan SmartLiving: CampusLost & Found.");
                    jalan = false;     
                    break;
                default:
                    System.out.println(">> Pilihan tidak ada, coba lagi.");
            }
            System.out.println(); 
        }
        scanner.close();
    }
    
    private void tampilkanMenu() {
        System.out.println("=== MENU LOST & FOUND ===");
        System.out.println("1. Lapor Barang Hilang");
        System.out.println("2. Lapor Barang Temuan");
        System.out.println("3. Keluar");
        System.out.print("Pilih [1-3]: ");
    }
    
    private int bacaPilihan() {
        while (true) {                    
            if (scanner.hasNextInt()) {   
                int angka = scanner.nextInt();
                scanner.nextLine();     
                return angka;             
            } else {
                System.out.print("Input harus angka! Coba lagi: ");
                scanner.next(); 
            }
        }
    }
    
    private void laporBarangHilang() {
        System.out.println("\n>> FORM LAPOR BARANG HILANG");
        System.out.print("ID Laporan      : "); String id = scanner.nextLine();
        System.out.print("Nama Barang     : "); String nama = scanner.nextLine();
        System.out.print("Kategori        : "); String kategori = scanner.nextLine();
        System.out.print("Ciri-ciri Khusus: "); String ciri = scanner.nextLine();
        System.out.print("Lokasi Hilang   : "); String lokasi = scanner.nextLine();
        System.out.print("Waktu Hilang    : "); String waktu = scanner.nextLine();
        System.out.print("Nama Pelapor    : "); String namaPelapor = scanner.nextLine();
        System.out.print("No HP           : "); String noTelepon = scanner.nextLine();
        System.out.print("Email           : "); String email = scanner.nextLine();

        
        BarangHilang barangHilang = new BarangHilang(id, nama, kategori, ciri, lokasi, waktu, "Hilang - Menunggu", namaPelapor, noTelepon, email);
        System.out.println("\n Laporan Kehilangan Berhasil Disimpan!");
        barangHilang.tampilkanInfo(); 
    }


    private void laporBarangDitemukan() {
        System.out.println("\n>> FORM LAPOR BARANG TEMUAN");
        System.out.print("ID Laporan      : "); String id = scanner.nextLine();
        System.out.print("Nama Barang     : "); String nama = scanner.nextLine();
        System.out.print("Kategori        : "); String kategori = scanner.nextLine();
        System.out.print("Ciri-ciri Khusus: "); String ciri = scanner.nextLine();
        System.out.print("Lokasi Temuan   : "); String lokasi = scanner.nextLine();
        System.out.print("Waktu Temuan    : "); String waktu = scanner.nextLine();
        System.out.print("Nama Penemu     : "); String penemu = scanner.nextLine();
        
        System.out.print("Opsi Titip (Ketik 'POS' atau 'BAWA SENDIRI'): "); 
        String opsiTitip = scanner.nextLine();

        BarangDitemukan barangDitemukan = new BarangDitemukan(
            id, nama, kategori, ciri, lokasi, waktu, "Ditemukan - Tersedia", penemu, opsiTitip
        );
        
        barangDitemukan.setOpsiTitip(opsiTitip);
        System.out.println("\nLaporan Barang Temuan Berhasil Disimpan!");
        barangDitemukan.tampilkanInfo(); 
    }
}