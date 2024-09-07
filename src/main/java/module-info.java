module io.github.devdoctor.bookrecommender {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    requires java.logging;
    requires jbcrypt;
    requires java.desktop;

    opens io.github.devdoctor.bookrecommender to javafx.fxml, com.google.gson;
    exports io.github.devdoctor.bookrecommender;
    exports io.github.devdoctor.bookrecommender.controllers;
    opens io.github.devdoctor.bookrecommender.controllers to com.google.gson, javafx.fxml;
    exports io.github.devdoctor.bookrecommender.utility;
    opens io.github.devdoctor.bookrecommender.utility to com.google.gson, javafx.fxml;
    exports io.github.devdoctor.bookrecommender.events;
    opens io.github.devdoctor.bookrecommender.events to com.google.gson, javafx.fxml;
}