package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import io.github.devdoctor.deltabooks.utility.UserUtils;
import io.github.devdoctor.deltabooks.utility.WindowsUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;


/**
 * The type Register controller.
 *
 * @author Davide Restelli
 */
public class registerController {
    @FXML
    private TextField TF_email;

    @FXML
    private TextField TF_name;

    @FXML
    private TextField TF_lastname;

    @FXML
    private TextField TF_fiscalcode;

    @FXML
    private PasswordField PF_password;

    @FXML
    private PasswordField PF_confirm_pass;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        WindowsUtils.changeCurrentWindow(event, Windows.LOGIN);
    }

    @FXML
    protected void onRegisterButtonClick() {
        String email = TF_email.getText();
        String name = TF_name.getText();
        String lastname = TF_lastname.getText();
        String fiscalcode = TF_fiscalcode.getText();
        String password = PF_password.getText();
        String confirmPassword = PF_confirm_pass.getText();

        if(name.isEmpty() || lastname.isEmpty()) {
            System.err.println("Nome o Cognome non validi!");
        }

        if(!Objects.equals(password, confirmPassword) && !password.isEmpty()) {
            System.err.println("Le passwords non coincidono!");
            return;
        }

        if(!UserUtils.checkEmail(email)) {
            System.err.println("La Mail non e' valida!");
            return;
        }

        if(!UserUtils.checkFiscalCode(fiscalcode)) {
            System.err.println("Il Codice Fiscale non e' valido!");
            return;
        }

        Pair<Boolean, User> wasRegistered = UserUtils.createUser(name, lastname, fiscalcode, email, password);

        if(!wasRegistered.getKey()) {
            // error message to the user
            System.err.println("La Mail e' gia' in uso!");
        } else {
            LoadedData.loginEvent.onLogin(wasRegistered.getValue());
            Stage stage = (Stage) TF_email.getScene().getWindow();
            stage.close();
        }
    }
}
