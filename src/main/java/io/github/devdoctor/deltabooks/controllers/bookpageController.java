package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import io.github.devdoctor.deltabooks.events.LoginEventListener;
import io.github.devdoctor.deltabooks.utility.BookUtils;
import io.github.devdoctor.deltabooks.utility.FileUtils;
import io.github.devdoctor.deltabooks.utility.UserUtils;
import io.github.devdoctor.deltabooks.utility.WindowsUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.net.URL;
import java.util.*;

public class bookpageController implements Initializable, LoginEventListener {

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
    protected Hyperlink HLuuid;

    @FXML
    private TableColumn<Review, String> TCuser;
    @FXML
    private TableColumn<Review, String> TCvote;

    @FXML
    protected TableView<Review> TVreviews;

    private ArrayList<Review> reviews;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Book current_book = LoadedData.current_looked_book;
        L_authors.setText(current_book.getAuthors().toString());
        L_categories.setText(current_book.getCategory().toString());
        L_description.setText(current_book.getDescription());
        L_publisher.setText(current_book.getPublisher());
        L_title.setText(current_book.getTitle());
        L_price.setText(String.valueOf(current_book.getPrice()) + "€");
        HLuuid.setText(current_book.getUuid());

        LoadedData.loginEvent.addListener(this);

        // is casted to arraylist to use addfirst later
        reviews = new ArrayList<Review>(FileUtils.loadReviewsForBook(current_book));

        TCuser.setCellValueFactory(cellData -> {
            User creator = UserUtils.getUserFromUUID(UUID.fromString(cellData.getValue().getCreator_uuid()));
            if(creator == null) {
                return new SimpleStringProperty("Utente Rimosso");
            }
            return new SimpleStringProperty(creator.getFullName());
        });
        TCvote.setCellValueFactory(cellData -> {
            String stars = calculateStars(cellData.getValue());
            return new SimpleStringProperty(stars);
        });

        reloadReviews();

        if(!LoadedData.config.isDebugOn()) {
            HLuuid.setVisible(false);
        }

        if(LoadedData.logged_user != null && !BookUtils.doesReviewExist(current_book, UUID.fromString(LoadedData.logged_user.getUUID()))) {
            B_writeReview.setDisable(false);
        }
    }

    private String calculateStars(Review review) {
        String result = String.join("", Collections.nCopies(review.getFinalVote(), "★"));
        result += String.join("", Collections.nCopies(5-review.getFinalVote(), "☆"));
        return result;
    }

    public void onWriteReviewButtonClick(ActionEvent event) {
        WindowsUtils.openDialogWindow(event, Windows.BOOK_REVIEW);
        if(LoadedData.last_review != null) {
            reviews.add(0, LoadedData.last_review);
            LoadedData.last_review = null;
            reloadReviews();
            B_writeReview.setDisable(true);
        }
    }

    private void reloadReviews() {
        TVreviews.getItems().clear();
        TVreviews.setItems(FXCollections.observableArrayList(reviews));
    }

    public void onUIDDHyperlinkClick() {
        ClipboardContent content = new ClipboardContent();
        content.putString(HLuuid.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }

    @Override
    public void onLogin() {
        B_writeReview.setDisable(false);
    }

    public void onClose(Event event) {
        LoadedData.loginEvent.removeListener(this);
    }
}
