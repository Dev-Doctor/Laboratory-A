module io.github.devdoctor.deltabooks {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.logging;
    requires jbcrypt;
    requires java.desktop;

    opens io.github.devdoctor.deltabooks to javafx.fxml, com.google.gson;
    exports io.github.devdoctor.deltabooks;
    exports io.github.devdoctor.deltabooks.controllers;
    opens io.github.devdoctor.deltabooks.controllers to com.google.gson, javafx.fxml;
    exports io.github.devdoctor.deltabooks.utility;
    opens io.github.devdoctor.deltabooks.utility to com.google.gson, javafx.fxml;
    exports io.github.devdoctor.deltabooks.events;
    opens io.github.devdoctor.deltabooks.events to com.google.gson, javafx.fxml;
}