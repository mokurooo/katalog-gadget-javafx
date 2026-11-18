



# Katalog Gadget

Aplikasi desktop katalog gadget berbasis **JavaFX** dan **SQLite**. Data diisi lewat form, disimpan permanen ke database, lalu ditampilkan sebagai kartu katalog.



## Fitur

- Form input nama, harga, dan foto gadget
- Validasi input (nama wajib diisi, harga harus angka bulat lebih dari nol)
- Penyimpanan permanen ke SQLite
- Katalog berbentuk kartu (foto, nama, harga, ID)
- Data tetap ada setelah aplikasi ditutup dan dibuka kembali

## Teknologi

- Java 21
- JavaFX 21.0.5
- SQLite JDBC 3.49.1.0
- Maven

## Cara Menjalankan

```bash
git clone https://github.com/USERNAME/katalog-gadget-javafx.git
cd katalog-gadget-javafx
mvn clean javafx:run
```

Database `data/katalog_gadget.db` dibuat otomatis saat pertama dijalankan. Jalankan aplikasi dari folder root proyek.

## Cara Menggunakan

1. Isi nama gadget, misalnya `Mouse Wireless`.
2. Isi harga dengan angka bulat tanpa titik, misalnya `150000`.
3. Pilih foto (`mouse.png`, `keyboard.png`, atau `headset.png`).
4. Klik **Simpan**. Kartu baru langsung tampil di katalog.

## Struktur Proyek

```
katalog-gadget-javafx/
├── pom.xml
├── data/                          # katalog_gadget.db dibuat saat dijalankan
└── src/main/
    ├── java/
    │   ├── main/MainApp.java
    │   ├── model/Gadget.java
    │   ├── db/Database.java
    │   ├── data/GadgetRepository.java
    │   └── controller/KatalogController.java
    └── resources/
        ├── view/katalog.fxml
        ├── view/styles/app.css
        └── gambar/
```

| Class | Fungsi |
|-------|--------|
| `MainApp` | Pintu masuk aplikasi, menyiapkan database lalu memuat tampilan |
| `Gadget` | Objek data beserta validasi |
| `Database` | Koneksi dan pembuatan tabel |
| `GadgetRepository` | Perintah simpan (`INSERT`) dan baca (`SELECT`) |
| `KatalogController` | Menghubungkan form, database, dan katalog |

## Struktur Tabel

| Kolom | Tipe | Keterangan |
|-------|------|------------|
| `id` | INTEGER PRIMARY KEY | Dibuat otomatis oleh SQLite |
| `nama` | TEXT NOT NULL | Nama gadget |
| `harga` | INTEGER NOT NULL | Harga rupiah bulat, lebih dari nol |
| `foto` | TEXT NOT NULL | Nama file gambar, misalnya `mouse.png` |

## Catatan Database

- Backup: salin file `data/katalog_gadget.db`.
- Reset data: hapus file tersebut, lalu jalankan aplikasi lagi.

## contoh gambar

<img width="749" height="586" alt="image" src="https://github.com/user-attachments/assets/565fceb0-b385-4d1a-9c13-f6d024a67e46" />
