package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import io.github.devdoctor.deltabooks.events.LoginEventListener;
import io.github.devdoctor.deltabooks.utility.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class bookpageController implements Initializable, LoginEventListener {

    @FXML
    protected Accordion ACreviewsTable;

    @FXML
    protected Button B_writeReview;

    @FXML
    protected GridPane GPaverageReviews;

    @FXML
    protected Label Lauthors;
    @FXML
    protected Label Lcategories;
    @FXML
    protected Label Ldescription;
    @FXML
    protected Label Lprice;
    @FXML
    protected Label Lpublisher;
    @FXML
    protected Label Ltitle;

    @FXML
    protected Hyperlink HLuuid;

    @FXML
    protected HBox HBstyleStars, HBcontentStars, HBnicenessStars, HBoriginalityStars, HBeditionStars;

    @FXML
    private TableColumn<Review, String> TCuser;
    @FXML
    private TableColumn<Review, String> TCfinalVote;
    @FXML
    private TableColumn<Review, String> TCstyleVote;
    @FXML
    private TableColumn<Review, String> TCcontentVote;
    @FXML
    private TableColumn<Review, String> TCnicenessVote;
    @FXML
    private TableColumn<Review, String> TCoriginalityVote;
    @FXML
    private TableColumn<Review, String> TCeditionVote;

    @FXML
    protected TableView<Review> TVreviews;

    @FXML
    protected VBox VBaverageVotesCotnainer;

    private ArrayList<Review> reviews;

    Book current_book;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        current_book = LoadedData.current_looked_book;
        Lauthors.setText(current_book.getAuthors().toString());
        Lcategories.setText(current_book.getCategory().toString());
        Ldescription.setText((
                current_book.getDescription().isEmpty()) ? "Non c'è una descrizione per questo libro." : current_book.getDescription()
        );
        Lpublisher.setText(current_book.getPublisher());
        Ltitle.setText(current_book.getTitle());
        Lprice.setText(String.valueOf(current_book.getPrice()) + "€");
        HLuuid.setText(current_book.getUuid());

        // is casted to arraylist to use addfirst later
        reviews = new ArrayList<Review>(FileUtils.loadReviewsForBook(current_book));

        reloadAverageVotes();

        LoadedData.loginEvent.addListener(this);

        // set the data in the columbs
        initializeColumbsData();

        if (!reviews.isEmpty()) {
            reloadReviews();
        } else {
            VBaverageVotesCotnainer.getChildren().clear();
            VBaverageVotesCotnainer.getChildren().add(new Label("Non ci sono ancora recensioni per questo libro."));
            ACreviewsTable.setVisible(false);
            ACreviewsTable.setDisable(true);
        }

        if (!LoadedData.config.isDebugOn()) {
            HLuuid.setVisible(false);
        }

        if (LoadedData.logged_user != null && !BookUtils.doesReviewExist(current_book, UUID.fromString(LoadedData.logged_user.getUUID()))
                && LibraryUtils.isBookInLibraries(current_book.getRealUUID())) {
            B_writeReview.setDisable(false);
        }
    }

    private void reloadAverageVotes() {
        // sets the stars in the boxes
        Float[] reviewsValues = BookUtils.calculateAverageReviewVotes(reviews);
        Star.generateStars(HBstyleStars, reviewsValues[0]);
        Star.generateStars(HBcontentStars, reviewsValues[1]);
        Star.generateStars(HBnicenessStars, reviewsValues[2]);
        Star.generateStars(HBoriginalityStars, reviewsValues[3]);
        Star.generateStars(HBeditionStars, reviewsValues[4]);
    }

    private void initializeColumbsData() {
        TCuser.setCellValueFactory(cellData -> {
            User creator = UserUtils.getUserFromUUID(UUID.fromString(cellData.getValue().getCreator_uuid()));
            if (creator == null) {
                return new SimpleStringProperty("Utente Rimosso");
            }
            return new SimpleStringProperty(creator.getFullName());
        });
        TCfinalVote.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    calculateStars((int) cellData.getValue().getFinalVote())
            );
        });
        TCstyleVote.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    calculateStars(cellData.getValue().getStyle().getValue())
            );
        });
        TCcontentVote.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    calculateStars(cellData.getValue().getContent().getValue())
            );
        });
        TCnicenessVote.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    calculateStars(cellData.getValue().getNiceness().getValue())
            );
        });
        TCoriginalityVote.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    calculateStars(cellData.getValue().getOriginality().getValue())
            );
        });
        TCeditionVote.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(
                    calculateStars(cellData.getValue().getEdition().getValue())
            );
        });
    }

    private String calculateStars(Integer review) {
        String result = String.join("", Collections.nCopies(review, "★"));
        result += String.join("", Collections.nCopies(5 - review, "☆"));
        return result;
    }

    public void onWriteReviewButtonClick(ActionEvent event) {
        WindowsUtils.openDialogWindow(event, Windows.BOOK_REVIEW);
        if (LoadedData.last_review != null) {
            reviews.add(0, LoadedData.last_review);
            LoadedData.last_review = null;
            reloadReviews();
            B_writeReview.setDisable(true);
            VBaverageVotesCotnainer.getChildren().clear();
            VBaverageVotesCotnainer.getChildren().add(GPaverageReviews);
            reloadAverageVotes();
        }
    }

    public void onReviewTableMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Review review = TVreviews.getSelectionModel().getSelectedItem();
            Review old_review = LoadedData.last_review;

            LoadedData.last_review = review;
            WindowsUtils.openDialogWindow(mouseEvent, Windows.INSPECT_REVIEW);
            LoadedData.last_review = old_review;
        }
    }

    private void reloadReviews() {
        TVreviews.setItems(FXCollections.observableList(new ArrayList<>()));
        TVreviews.setItems(FXCollections.observableArrayList(reviews));
        ACreviewsTable.setVisible(true);
        ACreviewsTable.setDisable(false);
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
