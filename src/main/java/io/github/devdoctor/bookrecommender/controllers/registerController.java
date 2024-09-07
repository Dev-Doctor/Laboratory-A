/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.controllers;

import io.github.devdoctor.bookrecommender.*;
import io.github.devdoctor.bookrecommender.utility.UserUtils;
import io.github.devdoctor.bookrecommender.utility.WindowsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;


/**
 * The Register window controller.
 *
 * @author Davide Restelli
 */
public class registerController {

    @FXML
    private TextField TFemail;
    @FXML
    private TextField TFfiscalcode;
    @FXML
    private TextField TFname;
    @FXML
    private TextField TFlastname;

    @FXML
    private PasswordField PFconfirm_pass;
    @FXML
    private PasswordField PFpassword;

    /**
     * This method is called when the login button is clicked.
     * Switches the current window with the login one
     *
     * @param event the event
     * @see registerController
     */
    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        WindowsUtils.changeCurrentWindow(event, Windows.LOGIN);
    }

    /**
     * This method is called when the register button is clicked.
     * Check if the user doesn't exist and the inputted values are right,
     * if it is able to, register the user, close the window and fires the {@code LoginEvent}
     *
     * @see MouseEvent
     */
    @FXML
    protected void onRegisterButtonClick() {
        String email = TFemail.getText();
        String name = TFname.getText();
        String lastname = TFlastname.getText();
        String fiscalcode = TFfiscalcode.getText();
        String password = PFpassword.getText();
        String confirmPassword = PFconfirm_pass.getText();

        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
        String alert_msg = "";

        // check if the name and the lastname are valid
        if (name.isEmpty() || lastname.isEmpty()) {
            alert_msg += "Nome o Cognome non validi!" + "\n";
            System.err.println("Nome o Cognome non validi!");
        }

        // check if the email is valid
        if (!UserUtils.checkEmail(email)) {
            alert_msg += "La Mail non e' valida!" + "\n";
            System.err.println("La Mail non e' valida!");
        }

        // check if the fiscal code is valid
        if (!UserUtils.checkFiscalCode(fiscalcode)) {
            alert_msg += "Il Codice Fiscale non e' valido!" + "\n";
            System.err.println("Il Codice Fiscale non e' valido!");
        }

        // check if the password are equal
        if (!Objects.equals(password, confirmPassword) && !password.isEmpty()) {
            alert_msg += "Le passwords non coincidono!" + "\n";
            System.err.println("Le passwords non coincidono!");
        }

        // show error messages if there are
        if(!alert_msg.isEmpty()) {
            alert.setContentText(alert_msg);
            alert.showAndWait();
            return;
        }

        // check if the user already exist
        Pair<Boolean, User> wasRegistered = UserUtils.createUser(name, lastname, fiscalcode, email, password);


        if (!wasRegistered.getKey()) {
            // error message to the user
            alert.setContentText("La Mail e' gia' in uso!");
            System.err.println("La Mail e' gia' in uso!");
            alert.showAndWait();
        } else {
            LoadedData.loginEvent.onLogin(wasRegistered.getValue());
            Stage stage = (Stage) TFemail.getScene().getWindow();
            stage.close();
        }
    }
}
