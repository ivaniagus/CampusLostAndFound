package com.mycompany.campust_lf;

import java.util.ArrayList;
import java.util.List;    
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;        
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class App extends Application {

    // Polymorphic untuk menyimpan semua laporan (BarangHilang & BarangDitemukan)
    private final ArrayList<Barang> daftarLaporan = new ArrayList<>();

    // Komponen Input Barang Hilang
    private TextField txtIdHilang, txtNamaHilang, txtCiriHilang;
    private TextField txtLokasiHilang, txtWaktuHilang, txtPelapor, txtNoTelp, txtEmail;
    private ComboBox<String> cmbKategoriHilang;
    private TextField txtKategoriCustomHilang;
    private Button btnSimpanHilang, btnResetHilang;

    // Komponen Input Barang Temuan
    private TextField txtIdTemuan, txtNamaTemuan, txtCiriTemuan;
    private TextField txtLokasiTemuan, txtWaktuTemuan, txtPenemu;
    private ComboBox<String> cmbKategoriTemuan;
    private TextField txtKategoriCustomTemuan;
    private ComboBox<String> cmbOpsiTitip;
    private Button btnSimpanTemuan, btnResetTemuan;

    // Area Output Terminal
    private TextArea areaOutput;
    
    private static final List<String> KATEGORI_OPSI = List.of(
            "Dokumen", "Elektronik", "Aksesoris", "Pakaian",
            "Perlengkapan", "Kunci", "Lainnya..."
    );

    @Override
    public void start(Stage stage) {
        // 1. Header Aplikasi
        Label lblJudul = new Label("SmartLiving: CampusLost & Found");
        lblJudul.setFont(Font.font("System", FontWeight.BOLD, 20));

        Label lblSubjudul = new Label("Sistem Pelaporan Barang Hilang & Temuan Kampus");
        lblSubjudul.setStyle("-fx-text-fill: #555555;");

        VBox headerBox = new VBox(4, lblJudul, lblSubjudul);
        headerBox.setAlignment(Pos.CENTER);

        // 2. TabPane untuk 2 Jenis Laporan
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabHilang = new Tab("Lapor Barang Hilang", buatFormBarangHilang());
        Tab tabTemuan = new Tab("Lapor Barang Temuan", buatFormBarangTemuan());
        tabPane.getTabs().addAll(tabHilang, tabTemuan);

        // 3. Area Output di Bagian Bawah
        Label lblOutput = new Label("Detail Laporan Tersimpan:");
        lblOutput.setFont(Font.font("System", FontWeight.BOLD, 12));

        areaOutput = new TextArea();
        areaOutput.setEditable(false);
        areaOutput.setPrefRowCount(8);
        areaOutput.setPromptText("Detail barang yang dilaporkan akan muncul di sini...");

        // 4. Pasang Event Handling & Validasi
        setupEventHandlers();

        // 5. Root Layout (VBox)
        VBox root = new VBox(12, headerBox, tabPane, lblOutput, areaOutput);
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: #F8F9FA;");

        Scene scene = new Scene(root, 620, 680);
        stage.setTitle("SmartLiving - CampusLost & Found");
        stage.setScene(scene);
        stage.show();
    }

    private void setupEventHandlers() {
        // Event tombol Simpan Barang Hilang (dengan Validasi & Try-Catch)
        btnSimpanHilang.setOnAction(event -> {
            String id = txtIdHilang.getText().trim();
            String nama = txtNamaHilang.getText().trim();
            String kategori = cmbKategoriHilang.getValue();
            String ciri = txtCiriHilang.getText().trim();
            String lokasi = txtLokasiHilang.getText().trim();
            String waktu = txtWaktuHilang.getText().trim();
            String pelapor = txtPelapor.getText().trim();
            String noTelp = txtNoTelp.getText().trim();
            String email = txtEmail.getText().trim();

            // Validasi 1: Cek kolom kosong
            String kategoriHilang = getKategoriValue(cmbKategoriHilang, txtKategoriCustomHilang);
            if (id.isEmpty() || nama.isEmpty() || kategoriHilang.isEmpty() || ciri.isEmpty()
                    || lokasi.isEmpty() || waktu.isEmpty() || pelapor.isEmpty()
                    || noTelp.isEmpty() || email.isEmpty()) {
                tampilkanAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap",
                        "Semua kolom pada form HARUS DIISI!");
                return;
            }

            // Validasi 2: Cek duplikasi ID Laporan
            if (isIdSudahAda(id)) {
                tampilkanAlert(Alert.AlertType.WARNING, "ID Duplikat",
                        "ID Laporan '" + id + "' sudah terdaftar. Gunakan ID lain!");
                return;
            }

            // Validasi 3: Try-Catch format angka untuk No. Telepon
            try {
                Long.parseLong(noTelp);
                if (noTelp.startsWith("-") || noTelp.length() < 11) {
                    tampilkanAlert(Alert.AlertType.ERROR, "Format Nomor Tidak Valid",
                            "Nomor telepon harus berupa angka positif minimal 11 digit!");
                    return;
                }
            } catch (NumberFormatException e) {
                tampilkanAlert(Alert.AlertType.ERROR, "Kesalahan Format Angka",
                        "Kolom No. Telepon hanya boleh berisi angka (contoh: 081234567890)!");
                txtNoTelp.requestFocus();
                return;
            }

            // Validasi 4: Cek format email sederhana
            if (!email.contains("@") || !email.contains(".")) {
                tampilkanAlert(Alert.AlertType.WARNING, "Format Email Tidak Valid",
                        "Masukkan alamat email yang valid (contoh: nama@students.unila.ac.id)!");
                txtEmail.requestFocus();
                return;
            }

            // Jika semua validasi lolos, buat objek BarangHilang
                BarangHilang barangHilang = new BarangHilang(
                id, nama, kategoriHilang, ciri, lokasi, waktu,  
                "Hilang - Menunggu", pelapor, noTelp, email
            );


            daftarLaporan.add(barangHilang);
            barangHilang.tampilkanInfo();
            tampilkanSemuaLaporan();
            resetFormHilang();

            tampilkanAlert(Alert.AlertType.INFORMATION, "Berhasil Disimpan",
                    "Laporan barang hilang '" + nama + "' (ID: " + id + ") berhasil disimpan!");
        });

        // tombol Reset Barang Hilang
        btnResetHilang.setOnAction(event -> resetFormHilang());

        // tombol Simpan Barang Temuan (dengan Validasi)
        btnSimpanTemuan.setOnAction(event -> {
            String id = txtIdTemuan.getText().trim();
            String nama = txtNamaTemuan.getText().trim();
            String kategori = cmbKategoriTemuan.getValue();
            String ciri = txtCiriTemuan.getText().trim();
            String lokasi = txtLokasiTemuan.getText().trim();
            String waktu = txtWaktuTemuan.getText().trim();
            String penemu = txtPenemu.getText().trim();
            String opsiTitip = cmbOpsiTitip.getValue();

            // Validasi 1: Cek kolom kosong
            String kategoriTemuan = getKategoriValue(cmbKategoriTemuan, txtKategoriCustomTemuan);
            if (id.isEmpty() || nama.isEmpty() || kategoriTemuan.isEmpty() || ciri.isEmpty()
                    || lokasi.isEmpty() || waktu.isEmpty() || penemu.isEmpty() || opsiTitip == null) {
                tampilkanAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap",
                        "Semua kolom pada form HARUS DIISI!");
                return;
            }

            // Validasi 2: Cek duplikasi ID Laporan
            if (isIdSudahAda(id)) {
                tampilkanAlert(Alert.AlertType.WARNING, "ID Duplikat",
                        "ID Laporan '" + id + "' sudah terdaftar. Gunakan ID lain!");
                return;
            }

            // Jika validasi lolos, buat objek BarangDitemukan
                BarangDitemukan barangDitemukan = new BarangDitemukan(
                id, nama, kategoriTemuan, ciri, lokasi, waktu,   
                "Ditemukan - Tersedia", penemu, opsiTitip
            );

            barangDitemukan.setOpsiTitip(opsiTitip);

            daftarLaporan.add(barangDitemukan);
            barangDitemukan.tampilkanInfo();
            tampilkanSemuaLaporan();
            resetFormTemuan();

            tampilkanAlert(Alert.AlertType.INFORMATION, "Berhasil Disimpan",
                    "Laporan barang temuan '" + nama + "' (ID: " + id + ") berhasil disimpan!");
        });

        // tombol Reset Barang Temuan
        btnResetTemuan.setOnAction(event -> resetFormTemuan());
    }

    // Method untuk menampilkan pop-up Alert
    private void tampilkanAlert(Alert.AlertType tipe, String judul, String pesan) {
        Alert alert = new Alert(tipe);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    // Method bantu untuk memeriksa apakah ID sudah digunakan
    private boolean isIdSudahAda(String id) {
        for (Barang b : daftarLaporan) {
            if (b.getIdBarang().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private void tampilkanSemuaLaporan() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Laporan Masuk: ").append(daftarLaporan.size()).append("\n\n");
        for (Barang b : daftarLaporan) {
            sb.append(b.getDetailInfo()).append("\n");
        }
        areaOutput.setText(sb.toString());
    }

    private void resetFormHilang() {
        txtIdHilang.clear();
        txtNamaHilang.clear();
        resetKategori(cmbKategoriHilang, txtKategoriCustomHilang);
        txtCiriHilang.clear();
        txtLokasiHilang.clear();
        txtWaktuHilang.clear();
        txtPelapor.clear();
        txtNoTelp.clear();
        txtEmail.clear();
    }

    private void resetFormTemuan() {
        txtIdTemuan.clear();
        txtNamaTemuan.clear();
        resetKategori(cmbKategoriTemuan, txtKategoriCustomTemuan);
        txtCiriTemuan.clear();
        txtLokasiTemuan.clear();
        txtWaktuTemuan.clear();
        txtPenemu.clear();
        cmbOpsiTitip.setValue("POS");
    }

    private VBox buatFormBarangHilang() {
        GridPane grid = buatGridDasar();

        txtIdHilang = new TextField();
        txtIdHilang.setPromptText("Contoh: HL-001");
        txtNamaHilang = new TextField();
        txtNamaHilang.setPromptText("Contoh: KTM / Dompet / Tumbler");
        cmbKategoriHilang = new ComboBox<>();
        txtKategoriCustomHilang = new TextField();
        VBox kategoriHilangBox = buatKategoriComboBox(cmbKategoriHilang, txtKategoriCustomHilang);
        tambahBarisGrid(grid, 2, "Kategori:", kategoriHilangBox);
        txtCiriHilang = new TextField();
        txtCiriHilang.setPromptText("Contoh: Warna hitam, stiker Unila");
        txtLokasiHilang = new TextField();
        txtLokasiHilang.setPromptText("Contoh: Gedung Ilmu Komputer Lt. 2");
        txtWaktuHilang = new TextField();
        txtWaktuHilang.setPromptText("Contoh: 18 Nov 2026, 10:00 WIB");
        txtPelapor = new TextField();
        txtPelapor.setPromptText("Nama lengkap pelapor");
        txtNoTelp = new TextField();
        txtNoTelp.setPromptText("Contoh: 081234567890");
        txtEmail = new TextField();
        txtEmail.setPromptText("Contoh: mahasiswa@students.unila.ac.id");

        tambahBarisGrid(grid, 0, "ID Laporan:", txtIdHilang);
        tambahBarisGrid(grid, 1, "Nama Barang:", txtNamaHilang);
        tambahBarisGrid(grid, 2, "Kategori:", cmbKategoriHilang);
        tambahBarisGrid(grid, 3, "Ciri-ciri Khusus:", txtCiriHilang);
        tambahBarisGrid(grid, 4, "Lokasi Hilang:", txtLokasiHilang);
        tambahBarisGrid(grid, 5, "Waktu Hilang:", txtWaktuHilang);
        tambahBarisGrid(grid, 6, "Nama Pelapor:", txtPelapor);
        tambahBarisGrid(grid, 7, "No. Telepon:", txtNoTelp);
        tambahBarisGrid(grid, 8, "Email:", txtEmail);

        btnSimpanHilang = new Button("Simpan Laporan Hilang");
        btnSimpanHilang.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-weight: bold;");
        btnResetHilang = new Button("Reset Form");

        HBox buttonBox = new HBox(10, btnSimpanHilang, btnResetHilang);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox container = new VBox(12, grid, buttonBox);
        container.setPadding(new Insets(14));
        return container;
    }

    private VBox buatFormBarangTemuan() {
        GridPane grid = buatGridDasar();

        txtIdTemuan = new TextField();
        txtIdTemuan.setPromptText("Contoh: TM-001");
        txtNamaTemuan = new TextField();
        txtNamaTemuan.setPromptText("Contoh: Kunci Motor / Flashdisk");
        cmbKategoriTemuan = new ComboBox<>();
        txtKategoriCustomTemuan = new TextField();
        VBox kategoriTemuanBox = buatKategoriComboBox(cmbKategoriTemuan, txtKategoriCustomTemuan);
        tambahBarisGrid(grid, 2, "Kategori:", kategoriTemuanBox);
        txtCiriTemuan = new TextField();
        txtCiriTemuan.setPromptText("Contoh: Gantungan kunci biru");
        txtLokasiTemuan = new TextField();
        txtLokasiTemuan.setPromptText("Contoh: Kantin FMIPA");
        txtWaktuTemuan = new TextField();
        txtWaktuTemuan.setPromptText("Contoh: 18 Nov 2026, 13:30 WIB");
        txtPenemu = new TextField();
        txtPenemu.setPromptText("Nama lengkap penemu");

        cmbOpsiTitip = new ComboBox<>();
        cmbOpsiTitip.getItems().addAll("POS", "BAWA SENDIRI");
        cmbOpsiTitip.setValue("POS");
        cmbOpsiTitip.setMaxWidth(Double.MAX_VALUE);

        tambahBarisGrid(grid, 0, "ID Laporan:", txtIdTemuan);
        tambahBarisGrid(grid, 1, "Nama Barang:", txtNamaTemuan);
        tambahBarisGrid(grid, 2, "Kategori:", cmbKategoriTemuan);
        tambahBarisGrid(grid, 3, "Ciri-ciri Khusus:", txtCiriTemuan);
        tambahBarisGrid(grid, 4, "Lokasi Temuan:", txtLokasiTemuan);
        tambahBarisGrid(grid, 5, "Waktu Temuan:", txtWaktuTemuan);
        tambahBarisGrid(grid, 6, "Nama Penemu:", txtPenemu);
        tambahBarisGrid(grid, 7, "Opsi Titip:", cmbOpsiTitip);

        btnSimpanTemuan = new Button("Simpan Laporan Temuan");
        btnSimpanTemuan.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-font-weight: bold;");
        btnResetTemuan = new Button("Reset Form");

        HBox buttonBox = new HBox(10, btnSimpanTemuan, btnResetTemuan);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox container = new VBox(12, grid, buttonBox);
        container.setPadding(new Insets(14));
        return container;
    }

    private GridPane buatGridDasar() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);

        ColumnConstraints colLabel = new ColumnConstraints();
        colLabel.setMinWidth(130);
        ColumnConstraints colInput = new ColumnConstraints();
        colInput.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(colLabel, colInput);
        return grid;
    }

    private void tambahBarisGrid(GridPane grid, int baris, String teksLabel,Node inputNode) {
        Label label = new Label(teksLabel);
        grid.add(label, 0, baris);
        grid.add(inputNode, 1, baris);
    }
    
    private VBox buatKategoriComboBox(ComboBox<String> cmbKategori, TextField txtCustom) {
    cmbKategori.getItems().addAll(KATEGORI_OPSI);
    cmbKategori.setValue(KATEGORI_OPSI.get(0));
    cmbKategori.setMaxWidth(Double.MAX_VALUE);

    txtCustom.setPromptText("Tulis kategori sendiri...");
    txtCustom.setManaged(false);
    txtCustom.setVisible(false);
    txtCustom.setMaxWidth(Double.MAX_VALUE);

    cmbKategori.valueProperty().addListener((obs, oldVal, newVal) -> {
        boolean isLainnya = "Lainnya...".equals(newVal);
        txtCustom.setManaged(isLainnya);
        txtCustom.setVisible(isLainnya);
        if (!isLainnya) txtCustom.clear();
    });

    return new VBox(4, cmbKategori, txtCustom);
}
    
    private void resetKategori(ComboBox<String> cmbKategori, TextField txtCustom) {
        cmbKategori.setValue(KATEGORI_OPSI.get(0));
        txtCustom.clear();
        txtCustom.setManaged(false);
        txtCustom.setVisible(false);
    }
    
    private String getKategoriValue(ComboBox<String> cmbKategori, TextField txtCustom) {
        String selected = cmbKategori.getValue();
        if ("Lainnya...".equals(selected)) {
            return txtCustom.getText().trim().isEmpty() ? "Lainnya" : txtCustom.getText().trim();
        }
        return selected != null ? selected : "";
    }
    

    public static void main(String[] args) {
        launch();
    }
}