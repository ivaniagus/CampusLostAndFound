package com.mycompany.campust_lf;

public class BarangDitemukan extends Barang {
    private String namaPenemu;
    private String opsiTitip;

    // Constructor
    public BarangDitemukan(String idBarang, String namaBarang, String kategori, String ciriKhusus, String lokasi, String waktu, String status, String namaPenemu, String opsiTitip) {
        super(idBarang, namaBarang, kategori, ciriKhusus, lokasi, waktu, status);
        this.namaPenemu = namaPenemu;
        this.opsiTitip = opsiTitip;
    }

    
    public String getNamaPenemu() { 
        return namaPenemu;
    }
    public String getOpsiTitip() { 
        return opsiTitip; 
    }
    
    public void setOpsiTitip(String opsiTitip) {
        if (opsiTitip != null) {
            String upper = opsiTitip.trim().toUpperCase();
            if (upper.equals("POS") || upper.equals("BAWA SENDIRI")) {
                this.opsiTitip = upper;
            } else {
                System.out.println("Peringatan: Opsi titip tidak valid! Gunakan POS atau BAWA SENDIRI");
                this.opsiTitip = "POS";
            }
        }
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo(); 
        System.out.println("--- INFO PENEMU ---");
        System.out.println("Nama       : " + namaPenemu);
        System.out.println("Opsi Titip : " + (opsiTitip.equals("POS") ? "Pos Penyimpanan Resmi" : "Dibawa Sendiri"));
    }
    
}
    

