/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import io.github.devdoctor.deltabooks.utility.UserUtils;
import io.github.devdoctor.deltabooks.utility.WindowsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * The login window controller.
 *
 * @author DevDoctor
 */
public class loginController {

    @FXML
    protected PasswordField PF_password;

    @FXML
    protected TextField TF_email;


    /**
     * This method is called when the login button is clicked.
     * Check if the user exist and the password is right,
     * if it is able to, closes the window and fires the {@code LoginEvent}
     *
     * @param event The mouse event
     * @see MouseEvent
     */
    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        // get the user email
        String email = TF_email.getText();
        // get the user password
        String password = PF_password.getText();
        // prepares the alert message
        Alert alert = new Alert(Alert.AlertType.WARNING, "Password e/o Email sbagliati.", ButtonType.OK);

        // creates a temp user to check
        User u = new User(email);
        // checks if the user exists
        Pair<Boolean, User> result = UserUtils.doesUserExist(u);
        // if the user exists
        if (result.getKey()) {
            u = result.getValue();
            // check the password if it is correct
            if (UserUtils.checkPassword(password, u.getPassword())) {
                // run the login event
                LoadedData.loginEvent.onLogin(u);
                // close window
                Stage stage = (Stage) TF_email.getScene().getWindow();
                stage.close();
            } else {
                System.err.println("Password sbagliata!");
                alert.showAndWait();
            }
        } else {
            System.err.println("Email sbagliata!");
            alert.showAndWait();
        }
    }

    /**
     * This method is called when the register button is clicked.
     * Switches the current window with the register one
     *
     * @param event the event
     * @see registerController
     */
    @FXML
    protected void onRegisterButtonClick(ActionEvent event) {
        WindowsUtils.changeCurrentWindow(event, Windows.REGISTER);
    }
}
