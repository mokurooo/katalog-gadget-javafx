package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import data.GadgetRepository;
import model.Gadget;

public class KatalogController {

    @FXML private TextField namaField;
    @FXML private TextField hargaField;
    @FXML private ComboBox<String> fotoBox;
    @FXML private Label pesanLabel;
    @FXML private Label jumlahLabel;
    @FXML private TilePane katalogPane;

    private final GadgetRepository repository = new GadgetRepository();

    @FXML
    private void initialize() {
        fotoBox.getItems().add("mouse.png");
        fotoBox.getItems().add("keyboard.png");
        fotoBox.getItems().add("headset.png");
        fotoBox.setValue("mouse.png");

        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal membaca database. Lihat Output.");
            e.printStackTrace();
        }
    }

    private void showData() throws SQLException {
        ArrayList<Gadget> daftar = repository.getAll();
        katalogPane.getChildren().clear();

        for (Gadget gadget : daftar) {
            VBox kartu = createGadgetCard(gadget);
            katalogPane.getChildren().add(kartu);
        }
        jumlahLabel.setText("Jumlah gadget: " + daftar.size());
    }

    private VBox createGadgetCard(Gadget gadget) {
        ImageView fotoView = new ImageView();
        fotoView.setFitWidth(170);
        fotoView.setFitHeight(110);
        fotoView.setPreserveRatio(true);

        StackPane bingkai = new StackPane();
        bingkai.setPrefSize(180, 120);
        bingkai.setMinSize(180, 120);
        bingkai.setMaxSize(180, 120);

        // Folder resource bernama "gambar" (sesuai struktur modul)
        URL lokasi = getClass().getResource("/gambar/" + gadget.getFoto());
        if (lokasi != null) {
            Image gambar = new Image(lokasi.toExternalForm(),
                    170, 110, true, true);
            if (!gambar.isError()) {
                fotoView.setImage(gambar);
                bingkai.getChildren().add(fotoView);
            } else {
                bingkai.getChildren().add(new Label("Gambar tidak terbaca"));
            }
        } else {
            bingkai.getChildren().add(new Label("Gambar tidak tersedia"));
        }

        Label namaLabel = new Label(gadget.getNama());
        namaLabel.setWrapText(true);
        namaLabel.getStyleClass().add("nama-gadget");

        Label hargaLabel = new Label("Rp" + gadget.getHarga());
        Label idLabel = new Label("ID: " + gadget.getId());
        idLabel.getStyleClass().add("muted");

        VBox kartu = new VBox();
        kartu.setSpacing(8);
        kartu.setMinWidth(220);
        kartu.setPrefWidth(220);
        kartu.setMaxWidth(220);
        kartu.getStyleClass().add("kartu-gadget");
        kartu.getChildren().add(bingkai);
        kartu.getChildren().add(namaLabel);
        kartu.getChildren().add(hargaLabel);
        kartu.getChildren().add(idLabel);
        return kartu;
    }

    @FXML
    private void simpan() {
        Gadget gadget;
        try {
            String nama = namaField.getText();
            long harga = Long.parseLong(hargaField.getText().trim());
            String foto = fotoBox.getValue();
            gadget = new Gadget(nama, harga, foto);
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus angka bulat, contoh 150000.");
            return;
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }

        try {
            repository.add(gadget);
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menyimpan. Lihat Output.");
            e.printStackTrace();
            return;
        }

        namaField.clear();
        hargaField.clear();

        try {
            showData();
            pesanLabel.setText("Gadget berhasil disimpan.");
        } catch (SQLException e) {
            pesanLabel.setText("Data tersimpan, tetapi katalog gagal dimuat ulang.");
            e.printStackTrace();
        }
    }
}
