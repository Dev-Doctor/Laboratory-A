/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.controllers;

import io.github.devdoctor.BookRecommender.*;
import io.github.devdoctor.BookRecommender.CommonObjects.User;
import io.github.devdoctor.BookRecommender.utility.APIUtils;
import io.github.devdoctor.BookRecommender.utility.UserUtils;
import io.github.devdoctor.BookRecommender.utility.WindowsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The login window controller.
 *
 * @author DevDoctor
 */
public class loginController {

    @FXML
    protected PasswordField PFpassword;

    @FXML
    protected TextField TFemail;


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
        String email = TFemail.getText();
        // get the user password
        String password = PFpassword.getText();
        // prepares the alert message
        Alert alert = new Alert(Alert.AlertType.WARNING, "Password e/o Email sbagliati.", ButtonType.OK);

        // creates a temp user to check
        User u = new User(email);
        // checks if the user exists

        // if the user exists
        if (UserUtils.doesUserExist(u)) {
            String token = UserUtils.getUserToken(u);
            u = APIUtils.fetchUserData(token);
            // check if the request was successful
            if (u != null) {
                // run the login event
                LoadedData.loginEvent.onLogin(u);
                // close window
                Stage stage = (Stage) TFemail.getScene().getWindow();
                stage.close();
            } else {
                System.err.println("Password sbagliata!");
                alert.showAndWait();
            }
        } else {
            System.err.println("Esiste di gia' un account con questa email.");
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
