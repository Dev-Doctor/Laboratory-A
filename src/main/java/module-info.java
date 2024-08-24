module io.github.devdoctor.deltabooks {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.logging;
    requires jbcrypt;

    opens io.github.devdoctor.deltabooks to javafx.fxml, com.google.gson;
    exports io.github.devdoctor.deltabooks;
    exports io.github.devdoctor.deltabooks.controllers;
    opens io.github.devdoctor.deltabooks.controllers to com.google.gson, javafx.fxml;
}