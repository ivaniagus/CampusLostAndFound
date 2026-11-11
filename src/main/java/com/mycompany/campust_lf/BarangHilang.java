package com.mycompany.campust_lf;

public class BarangHilang extends Barang {
    private String namaPelapor;
    private String noTelepon;
    private String email;

    
    public BarangHilang(String idBarang, String namaBarang, String kategori, String ciriKhusus, String lokasi, String waktu, String status, 
                        String namaPelapor, String noTelepon, String email) {
        super(idBarang, namaBarang, kategori, ciriKhusus, lokasi, waktu, status);
        this.namaPelapor = namaPelapor;
        this.noTelepon = noTelepon;
        this.email = email;
    }

    public String getNamaPelapor() { 
        return namaPelapor; 
    }
    public String getNoTelepon() { 
        return noTelepon;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("Pelapor    : " + namaPelapor);
        System.out.println("Kontak     : " + noTelepon);
        System.out.println("Email      : " + email);
        System.out.println("-------------------------");
    }
}

