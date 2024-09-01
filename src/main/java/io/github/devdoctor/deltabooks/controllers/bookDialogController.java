package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.Book;
import io.github.devdoctor.deltabooks.LoadedData;
import io.github.devdoctor.deltabooks.utility.BookUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class bookDialogController implements Initializable {
    @FXML
    protected Button Bsearch;

    @FXML
    private TableView<Book> TWbooks;

    @FXML
    protected TextField TFbookName;

    @FXML
    private TableColumn<Book, String> TCbookName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collection<Book> books = new ArrayList<Book>(LoadedData.books);
        bookReviewController.recommendedBooks.forEach(books::remove);

        TCbookName.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TWbooks.setItems(FXCollections.observableArrayList(books));
    }

    @FXML
    protected void onBookTableRowClick(MouseEvent event) {
        bookReviewController.recommendedBooks.add(TWbooks.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) TWbooks.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSearchButtonClick() {
        String title = TFbookName.getText();

        if (title.isEmpty()) {
            TWbooks.setItems(FXCollections.observableArrayList(LoadedData.books));
        } else {
            TWbooks.setItems(FXCollections.observableArrayList(BookUtils.searchBookByTitle(title)));
            TWbooks.refresh();
        }
    }
}
