/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.controllers;

import io.github.devdoctor.bookrecommender.Library;
import io.github.devdoctor.bookrecommender.LoadedData;
import io.github.devdoctor.bookrecommender.utility.LibraryUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * The addLibrary page controller.
 *
 * @author DevDoctor
 */
public class addLibraryController {
    @FXML
    private TextField TFname;

    /**
     * This method is called when the create library button is clicked.
     * It checks if the name is valid, then it tries to add the book to the library.
     * if it is successful close the window, and show a message to the user.
     */
    @FXML
    public void onCreateButtonClick() {
        // placeholder alert
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
        // get the library name
        String name = TFname.getText();
        // if it is empty
        if (name.isEmpty()) {
            // show warning message to user
            alert.setContentText("Nome non valido!");
            alert.showAndWait();
            return;
        }
        // check if the library exist
        if (!LibraryUtils.addNewLibrary(new Library(name))) {
            // show warning message
            alert.setContentText("Libreria con nome '" + name + "' gia' esistente!");
            alert.showAndWait();
            return;
        }
        // show information message to user
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Nuova libreria creata con successo!");
        alert.showAndWait();
        // fire the userEvent
        LoadedData.userEvent.fire();
        // close the window
        Stage stage = (Stage) TFname.getScene().getWindow();
        stage.close();
    }
}
