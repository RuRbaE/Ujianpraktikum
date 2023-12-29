package com.example.ujianpraktikum1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jdk.internal.icu.text.UnicodeSet;

public class BikeRentalApp extends Application {

    private TableView<Transaksi> tableView = new TableView<>();
    private ObservableList<Transaksi> transaksiList = FXCollections.observableArrayList();

    private final double HARGA_KAYU = 50000.0;
    private final double HARGA_LIPAT = 70000.0;
    private final double HARGA_LISTRIK = 100000.0;
    private final double HARGA_RODA_TIGA = 30000.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rental Sepeda App");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Labels
        Label usernameLabel = new Label("Username:");
        Label phoneLabel = new Label("Nomor Telepon:");
        Label emailLabel = new Label("Email:");

        // TextFields
        TextField usernameInput = new TextField();
        TextField phoneInput = new TextField();
        TextField emailInput = new TextField();

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            if (usernameInput.getText().isEmpty() || phoneInput.getText().isEmpty() || emailInput.getText().isEmpty()) {
                showAlert("Harap isi semua field dengan benar!");
            } else {
                showSewaPage(primaryStage);
            }
        });

        // Add components to the grid
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameInput, 1, 0);
        grid.add(phoneLabel, 0, 1);
        grid.add(phoneInput, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailInput, 1, 2);
        grid.add(loginButton, 1, 3);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method untuk mengecek validitas nomor telepon
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{12}"); // Hanya menerima 12 digit angka
    }

    // Method untuk mengecek validitas email
    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z]+[a-zA-Z0-9]*@gmail\\.com"); // Hanya menerima email dengan domain gmail.com
    }
    private boolean isValidUsername(String Username){
        return Username.matches("[a-z]+[A-Z]");
    }


    // Import statements...

    public class bikerentalapp extends Application {

        // Existing code...

        @Override
        public void start(Stage primaryStage) {
            // Existing code...

            // New button for "Sewa Durasi"
            Button sewaDurasiButton = new Button("Sewa Durasi");
            sewaDurasiButton.setOnAction(e -> showSewaDurasiDialog());

            // Add components to the grid
            UnicodeSet grid = null;
            grid.size();

            // Existing code...
        }

        // Existing code...

        private void showSewaDurasiDialog() {
            // Create a new dialog
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Sewa Durasi");
            dialog.setHeaderText("Masukkan durasi sewa (dalam jam):");

            // Set the button types
            ButtonType sewaButtonType = new ButtonType("Sewa", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(sewaButtonType, ButtonType.CANCEL);

            // Create and set the input field
            TextField durasiInput = new TextField();
            durasiInput.setPromptText("Durasi (jam)");
            dialog.getDialogPane().setContent(durasiInput);

            // Request focus on the input field by default
            Platform.runLater(durasiInput::requestFocus);

            // Convert the result to a string when the sewa button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == sewaButtonType) {
                    return durasiInput.getText();
                }
                return null;
            });

            // Show the dialog and handle the result
            dialog.showAndWait().ifPresent(durasi -> {
                if (isValidDurasi(durasi)) {
                    showAlert("Pesanan berhasil! Durasi sewa: " + durasi + " jam");
                } else {
                    showAlert("Harap isi durasi dengan benar!");
                }
            });
        }

        private boolean isValidDurasi(String durasi) {
            return durasi.matches("\\d+"); // Hanya menerima input berupa angka
        }

        // Existing code...
    }


    private void showSewaPage(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // ChoiceBox
        ChoiceBox<String> sepedaChoiceBox = new ChoiceBox<>();
        sepedaChoiceBox.getItems().addAll("Sepeda Kayu 50000.0", "Sepeda Lipat 70000.0", "Sepeda Listrik 100000.0");
        sepedaChoiceBox.setValue("Sepeda Kayu 30000.0");

        // Durasi Sepeda TextField
        TextField durasiInput = new TextField();
        durasiInput.setPromptText("Durasi Sewa (jam)");

        // Jumlah Sepeda TextField
        TextField jumlahSepedaInput = new TextField();
        jumlahSepedaInput.setPromptText("Jumlah Sepeda");

        // Buttons
        // Buttons
        Button sewaButton = new Button("Sewa");
        sewaButton.setOnAction(e -> {
            if (durasiInput.getText().isEmpty() || jumlahSepedaInput.getText().isEmpty()) {
                showAlert("Harap isi semua field dengan benar!");
            } else {
                tambahTransaksi(sepedaChoiceBox.getValue(), Integer.parseInt(jumlahSepedaInput.getText()), Double.parseDouble(durasiInput.getText()));
                showAlert("Pesanan berhasil!");
            }
        });

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                Transaksi selectedTransaksi = tableView.getSelectionModel().getSelectedItem();
                int index = tableView.getSelectionModel().getSelectedIndex();
                updateTransaksi(selectedTransaksi, index, sepedaChoiceBox.getValue(),
                        Integer.parseInt(jumlahSepedaInput.getText()));
            } else {
                showAlert("Pilih transaksi untuk diupdate!");
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hapusTransaksi(tableView.getSelectionModel().getSelectedItem());
            } else {
                showAlert("Pilih transaksi untuk dihapus!");
            }
        });

// Add components to the grid
        grid.add(sepedaChoiceBox, 0, 0);
        grid.add(durasiInput, 1, 0);
        grid.add(jumlahSepedaInput, 2, 0);
        grid.add(sewaButton, 0, 1);
        grid.add(updateButton, 1, 1);
        grid.add(deleteButton, 2, 1);
        // TableView
        TableColumn<Transaksi, String> sepedaCol = new TableColumn<>("Sepeda");
        sepedaCol.setCellValueFactory(new PropertyValueFactory<>("sepeda"));

        TableColumn<Transaksi, Integer> jumlahCol = new TableColumn<>("Jumlah");
        jumlahCol.setCellValueFactory(new PropertyValueFactory<>("jumlah"));

        TableColumn<Transaksi, Double> totalCol = new TableColumn<>("Total Harga");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<Transaksi, Double> durasiCol = new TableColumn<>("Durasi Sewa");
        durasiCol.setCellValueFactory(new PropertyValueFactory<>("durasi")); // Use "durasi" instead of "lama"


        tableView.setItems(transaksiList);
        tableView.getColumns().addAll(sepedaCol, jumlahCol, totalCol, durasiCol);
        // Add components to the grid
        grid.add(tableView, 0, 2, 3, 1);

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
    }

    private void hapusTransaksi(Transaksi transaksi) {
        transaksiList.remove(transaksi);
        tableView.refresh();
    }


    private void updateTransaksi(Transaksi selectedTransaksi, int index, String sepeda, int jumlah) {
        double harga = 0;
        switch (sepeda) {
            case "Sepeda Kayu":
                harga = HARGA_KAYU;
                break;
            case "Sepeda Lipat":
                harga = HARGA_LIPAT;
                break;
            case "Sepeda Listrik":
                harga = HARGA_LISTRIK;
                break;
            case "Sepeda Roda Tiga": // Tambahkan case untuk sepeda roda tiga
                harga = HARGA_RODA_TIGA;
                break;
        }

        double total = harga * jumlah;
        selectedTransaksi.setSepeda(sepeda);
        selectedTransaksi.setJumlah(jumlah);
        selectedTransaksi.setTotal(total);
        transaksiList.set(index, selectedTransaksi);
        tableView.refresh();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void tambahTransaksi(String sepeda, int jumlah, double durasi) {
        double harga = 0;
        switch (sepeda) {
            case "Sepeda Kayu 50000.0":
                harga = HARGA_KAYU;
                break;
            case "Sepeda Lipat 70000.0":
                harga = HARGA_LIPAT;
                break;
            case "Sepeda Listrik 100000.0":
                harga = HARGA_LISTRIK;
                break;
            case "Sepeda Roda Tiga 30000.0": // Tambahkan case untuk sepeda roda tiga
                harga = HARGA_RODA_TIGA;
                break;
        }
        double total = harga * jumlah * durasi;  // Multiply by duration
        Transaksi transaksi = new Transaksi(sepeda, jumlah, total, durasi);
        transaksiList.add(transaksi);
        tableView.refresh();
    }

    // ... (existing code)

    public static class Transaksi {
        private String sepeda;
        private int jumlah;
        private double total;
        private double durasi;  // Rename to lama or duration

        public Transaksi(String sepeda, int jumlah, double total, double durasi) {
            this.sepeda = sepeda;
            this.jumlah = jumlah;
            this.total = total;
            this.durasi = durasi;
        }


        public String getSepeda() {
            return sepeda;
        }

        public void setSepeda(String sepeda) {
            this.sepeda = sepeda;
        }

        public int getJumlah() {
            return jumlah;
        }

        public void setJumlah(int jumlah) {
            this.jumlah = jumlah;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }



        // Existing code...

        public double getDurasi() {
            return durasi;
        }

        public void setDurasi(double durasi) {
            this.durasi = durasi;
        }

    }
}