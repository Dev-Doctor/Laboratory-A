/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.controllers;

import io.github.devdoctor.bookrecommender.Book;
import io.github.devdoctor.bookrecommender.LoadedData;
import io.github.devdoctor.bookrecommender.utility.BookUtils;
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

/**
 * The BookDialog window controller.
 *
 * @author DevDoctor
 */
public class bookDialogController implements Initializable {
    @FXML
    protected Button Bsearch;

    @FXML
    protected TableView<Book> TWbooks;

    @FXML
    protected TextField TFbookName;

    @FXML
    protected TableColumn<Book, String> TCbookName;

    /**
     * It is called when the window is created.
     * Remove from the table data the list of books that were already
     * recommended. And adds the new data to the table.
     *
     * @param url
     * @param resourceBundle
     * @see bookReviewController#recommendedBooks
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collection<Book> books = new ArrayList<Book>(LoadedData.books);
        // remove each book in recommendedBooks from the new books arraylist
        bookReviewController.recommendedBooks.forEach(books::remove);

        // set the data in the table
        TCbookName.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TWbooks.setItems(FXCollections.observableArrayList(books));
    }

    /**
     * This method is called when a row of the table of {@code Books} is clicked.
     * It checks which row is clicked and gets the Book, then adds the book to the recommendedBooks.
     * And closes the window.
     */
    @FXML
    protected void onBookTableRowClick(MouseEvent event) {
        // adds the clicked row book to the recommended books array
        bookReviewController.recommendedBooks.add(TWbooks.getSelectionModel().getSelectedItem());

        // closes the window
        Stage stage = (Stage) TWbooks.getScene().getWindow();
        stage.close();
    }

    /**
     * This method is called when the search button is clicked.
     * It sets the table data to the new searched data or reset the table data if the
     * {@code TextField} is empty
     */
    @FXML
    protected void onSearchButtonClick() {
        // gets the name of the book to search
        String title = TFbookName.getText();

        // if the textfield is empty
        if (title.isEmpty()) {
            // resets the table to the old data
            TWbooks.setItems(FXCollections.observableArrayList(LoadedData.books));
        } else {
            // sets the table to the new searched data
            TWbooks.setItems(FXCollections.observableArrayList(BookUtils.searchBookByTitle(title)));
            TWbooks.refresh();
        }
    }
}
