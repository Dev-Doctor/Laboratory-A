package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.Book;
import io.github.devdoctor.deltabooks.Config;
import io.github.devdoctor.deltabooks.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class masterController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Config config = FileUtils.loadConfig();
    }

    @FXML
    protected void onHelloButtonClick() {
        // System.getProperty("user.dir") + "\\book-catalog.json"
        Config config = FileUtils.loadConfig();
        try {
            Collection<Book> books = FileUtils.loadBooks(config.getBooks_dataset_location());
            for(Book book : books) {
                System.out.println(book.getCategories());
            }
        } catch (Exception ignored){};
    }
}