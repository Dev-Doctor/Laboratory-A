package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.Library;
import io.github.devdoctor.deltabooks.LoadedData;
import io.github.devdoctor.deltabooks.utility.LibraryUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addLibraryController {
    @FXML
    private TextField TFname;

    @FXML
    public void onCreateButtonClick() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
        String name = TFname.getText();
        if(name.isEmpty()) {
            alert.setContentText("Nome non valido!");
            alert.showAndWait();
            return;
        }
        if(!LibraryUtils.addNewLibrary(new Library(name))) {
            alert.setContentText("Libreria con nome '" + name + "' gia' esistente!");
            alert.showAndWait();
            return;
        }
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Nuova libreria creata con successo!");
        alert.showAndWait();
        LoadedData.userEvent.fire();
        Stage stage = (Stage) TFname.getScene().getWindow();
        stage.close();
    }
}
