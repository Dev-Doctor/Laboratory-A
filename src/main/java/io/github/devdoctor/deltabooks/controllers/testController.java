package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.Config;
import io.github.devdoctor.deltabooks.FileUtils;
import javafx.fxml.FXML;

public class testController {

    @FXML
    protected void onLoadUsersButtonClick() {
        FileUtils.loadUsers();
    }

    @FXML
    protected void onLoadBooksButtonClick() {
        FileUtils.loadBooks();
    }

    @FXML
    protected void onLoadConfigButtonClick() {
        FileUtils.loadConfig();
    }
}
