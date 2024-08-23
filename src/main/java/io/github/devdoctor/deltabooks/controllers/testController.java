package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.Config;
import io.github.devdoctor.deltabooks.FileUtils;
import javafx.fxml.FXML;

public class testController {

    @FXML
    protected void onLoadUsersButtonClick() {
        Config config = FileUtils.loadConfig();
        try {
            System.out.println(FileUtils.loadUsers(config.getUsers_dataset_location()));
        } catch (Exception ignored){};
    }

    @FXML
    protected void onLoadBooksButtonClick() {
        Config config = FileUtils.loadConfig();
        try {
            System.out.println(FileUtils.loadBooks(config.getBooks_dataset_location()));
        } catch (Exception ignored){};
    }

    @FXML
    protected void onLoadConfigButtonClick() {
        FileUtils.loadConfig();
    }
}
