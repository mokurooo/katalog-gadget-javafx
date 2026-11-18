package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import model.Gadget;

public class GadgetRepository {

    public void add(Gadget gadget) throws SQLException {
        String sql = "INSERT INTO gadget (nama, harga, foto) VALUES (?, ?, ?)";

        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            perintah.setString(1, gadget.getNama());
            perintah.setLong(2, gadget.getHarga());
            perintah.setString(3, gadget.getFoto());
            perintah.executeUpdate();
        }
    }

    public ArrayList<Gadget> getAll() throws SQLException {
        ArrayList<Gadget> daftar = new ArrayList<Gadget>();
        String sql = "SELECT id, nama, harga, foto FROM gadget ORDER BY id";

        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql);
             ResultSet hasil = perintah.executeQuery()) {

            while (hasil.next()) {
                long id = hasil.getLong("id");
                String nama = hasil.getString("nama");
                long harga = hasil.getLong("harga");
                String foto = hasil.getString("foto");

                Gadget gadget = new Gadget(id, nama, harga, foto);
                daftar.add(gadget);
            }
        }
        return daftar;
    }
}
