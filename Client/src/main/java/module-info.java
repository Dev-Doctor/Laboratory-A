module io.github.devdoctor.BookRecommender {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    requires java.logging;
    requires jbcrypt;
    requires java.desktop;

    opens io.github.devdoctor.BookRecommender to javafx.fxml, com.google.gson;
    exports io.github.devdoctor.BookRecommender;
    exports io.github.devdoctor.BookRecommender.controllers;
    opens io.github.devdoctor.BookRecommender.controllers to com.google.gson, javafx.fxml;
    exports io.github.devdoctor.BookRecommender.utility;
    opens io.github.devdoctor.BookRecommender.utility to com.google.gson, javafx.fxml;
    exports io.github.devdoctor.BookRecommender.events;
    opens io.github.devdoctor.BookRecommender.events to com.google.gson, javafx.fxml;
}