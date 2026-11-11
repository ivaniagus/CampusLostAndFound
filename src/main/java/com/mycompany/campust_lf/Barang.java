package com.mycompany.campust_lf;

public class Barang {
    private String idBarang;
    private String namaBarang;
    private String kategori;
    private String ciriKhusus;
    private String lokasi;
    private String waktu;
    private String status;
    
    public Barang(String idBarang, String namaBarang, String kategori, String ciriKhusus, String lokasi, String waktu, String status){
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.ciriKhusus = ciriKhusus;
        this.lokasi = lokasi;
        this.waktu = waktu;
        this.status = status;
    }
    public String getIdBarang(){
        return idBarang;
    }
    public String getNamaBarang(){
        return namaBarang;
    }
    public String getKategori(){
        return kategori;
    }
    public String getCiriKhusus(){
        return ciriKhusus;
    }
    public String getlokasi(){
        return lokasi;
    }
    public String getWaktu(){
        return waktu;
    }
    public String getStatus(){
        return status;
    }
    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public void setNamaBarang(String namaBarang) {
        if (namaBarang != null && !namaBarang.trim().isEmpty()) {
            this.namaBarang = namaBarang.trim();
        } else {
            System.out.println("Peringatan: Nama barang tidak boleh kosong!");
        }
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setCiriKhusus(String ciriKhusus) {
        this.ciriKhusus = ciriKhusus;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void tampilkanInfo() {
        System.out.println("\n--- DETAIL BARANG ---");
        System.out.println("ID         : " + idBarang);
        System.out.println("Nama       : " + namaBarang);
        System.out.println("Kategori   : " + kategori);
        System.out.println("Ciri-ciri  : " + ciriKhusus);
        System.out.println("Lokasi     : " + lokasi);
        System.out.println("Waktu      : " + waktu);
        System.out.println("Status     : " + status);
    }
}
