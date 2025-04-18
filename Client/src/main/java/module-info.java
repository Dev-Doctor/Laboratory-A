module io.github.devdoctor.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    requires java.logging;
    requires jbcrypt;
    requires java.desktop;
    requires jdk.httpserver;
    requires jdk.jfr;
    requires io.github.devdoctor.shared;

    exports io.github.devdoctor.BookRecommender;
    exports io.github.devdoctor.BookRecommender.controllers;
    exports io.github.devdoctor.BookRecommender.events;
    exports io.github.devdoctor.BookRecommender.utility;


    opens io.github.devdoctor.BookRecommender to javafx.fxml, com.google.gson;
    opens io.github.devdoctor.BookRecommender.controllers to com.google.gson, javafx.fxml;
    opens io.github.devdoctor.BookRecommender.utility to com.google.gson, javafx.fxml;
    opens io.github.devdoctor.BookRecommender.events to com.google.gson, javafx.fxml;
}