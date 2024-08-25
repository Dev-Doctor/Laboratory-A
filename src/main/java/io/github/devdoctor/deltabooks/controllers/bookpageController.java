package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class bookpageController implements Initializable {

    @FXML
    protected Button B_writeReview;

    @FXML
    protected Label L_authors;
    @FXML
    protected Label L_categories;
    @FXML
    protected Label L_description;
    @FXML
    protected Label L_price;
    @FXML
    protected Label L_publisher;
    @FXML
    protected Label L_title;
    @FXML
    protected Hyperlink HL_uuid;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Book current_book = LoadedData.current_looked_book;
        L_authors.setText(current_book.getAuthors().toString());
        L_categories.setText(current_book.getCategory().toString());
        L_description.setText(current_book.getDescription());
        L_publisher.setText(current_book.getPublisher());
        L_title.setText(current_book.getTitle());
        L_price.setText(String.valueOf(current_book.getPrice()) + "€");
        HL_uuid.setText(current_book.getUUID());

        if(!LoadedData.config.isDebugOn()) {
            HL_uuid.setVisible(false);
        }

        if(LoadedData.logged_user != null && !BookUtils.doesReviewExist(current_book, UUID.fromString(LoadedData.logged_user.getUUID()))) {
            B_writeReview.setDisable(false);
        }
    }

    public void onWriteReviewButtonClick(ActionEvent event) {
        WindowsUtils.openDialogWindow(event, Windows.BOOK_REVIEW);
    }

    public void onUIDDHyperlinkClick() {
        ClipboardContent content = new ClipboardContent();
        content.putString(HL_uuid.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }
}
