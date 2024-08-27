package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.Book;
import io.github.devdoctor.deltabooks.Library;
import io.github.devdoctor.deltabooks.LoadedData;
import io.github.devdoctor.deltabooks.utility.LibraryUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addToLibraryController implements Initializable {
    /**
     * Library name column of the libraries table
     * @see #TWlibraries
     */
    @FXML
    private TableColumn<Library, String> TClibraryNames;
    /** Table of the libraries list */
    @FXML
    private TableView<Library> TWlibraries;

    private Book bookToAdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookToAdd = LoadedData.current_looked_book;
        LoadedData.current_looked_book = null;

        TClibraryNames.setCellValueFactory(new PropertyValueFactory<Library, String>("name"));
        List<Library> edited_libraries = new ArrayList<Library>();

        LoadedData.logged_user_libraries.forEach(library -> {
            if(!library.doesContainBook(bookToAdd.getUuid())) {
                edited_libraries.add(library);
            }
        });

        TWlibraries.setItems(FXCollections.observableList(edited_libraries));
    }


    /**
     * This method is called when a row of the table of {@code Books} is clicked.
     * It checks for double click, if found opens a new book tab in its {@code TabPane}.
     *
     * @param event The mouse event
     * @see #TWbooks
     * @see MouseEvent
     */
    @FXML
    protected void clickItem(MouseEvent event) {
        //Checking double click
        if (event.getClickCount() == 1) {
            Library candidate = TWlibraries.getSelectionModel().getSelectedItem();
            Boolean wasSuccessful = candidate.addBook(bookToAdd.getUuid());
            if(wasSuccessful) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il libro e' stato aggiunto con successo!", ButtonType.OK);
                LibraryUtils.updateLibrary(candidate);
                LibraryUtils.saveLibraries();
                LoadedData.userEvent.fire();
                alert.show();
                Stage stage = (Stage) TWlibraries.getScene().getWindow();
                stage.close();
            }
        }
    }
}
