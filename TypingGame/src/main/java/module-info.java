module com.example.typinggame {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.typinggame to javafx.fxml;
    exports com.example.typinggame;
}