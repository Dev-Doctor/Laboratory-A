package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

public class loginController {
    @FXML
    private TextField TF_email;

    @FXML
    private PasswordField PF_password;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String email = TF_email.getText();
        String password = PF_password.getText();

        User u = new User(email);
        Pair<Boolean, User> result = UserUtils.doesUserExist(u);
        if(result.getKey()) {
            u = result.getValue();
            if(UserUtils.checkPassword(password, u.getPassword())) {
                LoadedData.logged_user = u;
                Stage stage = (Stage) TF_email.getScene().getWindow();
                stage.close();
            } else {
                System.err.println("Password sbagliata!");
            }
        } else {
            System.err.println("Email sbagliata!");
            // PRINT ERROR MESSAGE TO USER
        }
    }

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) {
//        System.out.println("... this doesn't do anything ...");
        WindowsUtils.changeCurrentWindow(event, Windows.REGISTER);
    }
}
