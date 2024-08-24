package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class masterController implements Initializable {
    @FXML
    private Button B_login;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        WindowsUtils.openDialogWindow(event, Windows.LOGIN);
        if(LoadedData.logged_user != null) {
            System.out.println("Logged in successfully!");
            B_login.setVisible(false);
        }
    }
}