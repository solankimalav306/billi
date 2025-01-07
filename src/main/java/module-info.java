module org.example.Billi {
    requires javafx.controls;
    requires javafx.fxml;
    requires layout;
    requires kernel;
    requires io;
    requires barcodes;
    opens org.example to javafx.fxml;
    exports org.example;
}