module com.example.ujianpraktikum1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ujianpraktikum1 to javafx.fxml;
    exports com.example.ujianpraktikum1;
}