/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
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


/**
 * The AddToLibrary window controller.
 * @author DevDoctor
 */
public class addToLibraryController implements Initializable {
    /**
     * Library name column of the libraries table
     *
     * @see #TWlibraries
     */
    @FXML
    protected TableColumn<Library, String> TClibraryNames;
    /**
     * Table of the libraries list
     */
    @FXML
    protected TableView<Library> TWlibraries;

    private Book bookToAdd;

    /**
     * When the window is created. Removes the libraries that already
     * contain the book and set the data in the Table.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookToAdd = LoadedData.current_looked_book;
        LoadedData.current_looked_book = null;

        // set the Table data
        TClibraryNames.setCellValueFactory(new PropertyValueFactory<Library, String>("name"));

        List<Library> edited_libraries = new ArrayList<Library>();

        // remove the libraries that contain already the book
        LoadedData.logged_user_libraries.forEach(library -> {
            if (!library.doesContainBook(bookToAdd.getUuid())) {
                edited_libraries.add(library);
            }
        });

        // set the data in the table
        TWlibraries.setItems(FXCollections.observableList(edited_libraries));
    }


    /**
     * This method is called when a row of the table of {@code Libraries} is clicked.
     * It checks which row is clicked and gets the library, then it tries to add the book to the library.
     * if it is successful close the window, and show a message to the user.
     *
     * @param event The mouse event
     * @see MouseEvent
     */
    @FXML
    protected void clickItem(MouseEvent event) {
        // create a popup telling the user that the action was not successful
        Alert alert = new Alert(Alert.AlertType.ERROR, "C'e' stato un problema sconosciuto.", ButtonType.OK);
        // gets the candidate library
        Library candidate = TWlibraries.getSelectionModel().getSelectedItem();
        // checks if the book is not already added
        Boolean wasSuccessful = candidate.addBook(bookToAdd.getUuid());
        if (wasSuccessful) {
            // udate the popup message to tell the user that was successfully executed
            alert.setContentText("Il libro e' stato aggiunto con successo!");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            // update the library
            LibraryUtils.updateLibrary(candidate);
            // save to file the library
            LibraryUtils.saveLibraries();
            // call the update libraries event
            LoadedData.userEvent.fire();
        }
        // preparation for closing the window
        Stage stage = (Stage) TWlibraries.getScene().getWindow();
        // close the window
        stage.close();
        // show the popup message
        alert.show();
    }
}
