/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
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
import java.util.stream.Collectors;

/**
 * The bookpage tab controller.
 *
 * @author DevDoctor
 */
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
    protected ListView<String> LVreaderAdvices;

    @FXML
    protected Hyperlink HLuuid;

    @FXML
    protected HBox HBstyleStars, HBcontentStars, HBnicenessStars, HBoriginalityStars, HBeditionStars;

    @FXML
    protected TableColumn<Review, String> TCcontentVote;
    @FXML
    protected TableColumn<Review, String> TCeditionVote;
    @FXML
    protected TableColumn<Review, String> TCfinalVote;
    @FXML
    protected TableColumn<Review, String> TCnicenessVote;
    @FXML
    protected TableColumn<Review, String> TCstyleVote;
    @FXML
    protected TableColumn<Review, String> TCuser;

    @FXML
    protected TableColumn<Review, String> TCoriginalityVote;

    @FXML
    protected TableView<Review> TVreviews;

    @FXML
    protected VBox VBaverageVotesCotnainer;

    private ArrayList<Review> reviews;

    Book current_book;

    /**
     * It is called when the tab is created.
     * Remove from the table data the list of books that were already
     * recommended. And adds the new data to the table.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        current_book = LoadedData.current_looked_book;

        setBookData();

        // is casted to arraylist to use addfirst later
        reviews = new ArrayList<Review>(FileUtils.loadReviewsForBook(current_book));

        HashMap<Book, Integer> votes = BookUtils.getAverageRecommendedBooks(reviews);

        // get the top 10 of the most voted book recommendation
        Map<Book, Integer> topTen = votes.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // for the ten books, add them to the list view
        topTen.forEach((book, quantity) -> {
            LVreaderAdvices.getItems().add(book.getTitle() + " - (" + quantity + " raccomandazioni)");
        });

        // reload the average votes
        reloadAverageVotes();

        // add this class as a listener for the login event
        LoadedData.loginEvent.addListener(this);

        // set the data in the columbs
        initializeColumnsData();

        // if there are review for this book
        if (!reviews.isEmpty()) {
            // reload the review
            reloadReviews();
        } else {
            // write a message to tell that there are no reviews
            VBaverageVotesCotnainer.getChildren().clear();
            VBaverageVotesCotnainer.getChildren().add(new Label("Non ci sono ancora recensioni per questo libro."));
            ACreviewsTable.setVisible(false);
            ACreviewsTable.setDisable(true);
        }

        // if the debug option is off disable the uuid visibility
        if (!LoadedData.config.isDebugOn()) {
            HLuuid.setVisible(false);
        }

        tryEnablingReviewButton();
    }

    /**
     * if the user is not logged in and there is no review from the logged user and the book is in one of the logged user libraries
     * then enables the review button.
     */
    private void tryEnablingReviewButton() {
        // if the user is not logged in and there is no review from the logged user and the book is in one of the logged user libraries
        if (LoadedData.logged_user != null && !BookUtils.doesReviewExist(current_book, UUID.fromString(LoadedData.logged_user.getUUID()))
                && LibraryUtils.isBookInLibraries(current_book.getRealUUID())) {
            // enable the write review button
            B_writeReview.setDisable(false);
        }
    }

    /**
     * Populates the bookpage with the current book data
     */
    private void setBookData() {
        Lauthors.setText(current_book.getAuthors().toString());
        Lcategories.setText(current_book.getCategory().toString());
        Ldescription.setText((
                current_book.getDescription().isEmpty()) ? "Non c'è una descrizione per questo libro." : current_book.getDescription()
        );
        Lpublisher.setText(current_book.getPublisher());
        Ltitle.setText(current_book.getTitle());
        Lprice.setText(String.valueOf(current_book.getPrice()) + "€");
        HLuuid.setText(current_book.getUuid());
    }

    /**
     * Reloads the average review votes stars.
     */
    private void reloadAverageVotes() {
        // sets the stars in the boxes
        Float[] reviewsValues = BookUtils.calculateAverageReviewVotes(reviews);
        Star.generateStars(HBstyleStars, reviewsValues[0]);
        Star.generateStars(HBcontentStars, reviewsValues[1]);
        Star.generateStars(HBnicenessStars, reviewsValues[2]);
        Star.generateStars(HBoriginalityStars, reviewsValues[3]);
        Star.generateStars(HBeditionStars, reviewsValues[4]);
    }

    /**
     * Format each colum of the table to the correct data.
     */
    private void initializeColumnsData() {
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

    /**
     * Calculate the stars for a review with the passed review as value.
     * @param review the value of the review
     * @return a string containing review number of filled stars plus {@code 5-review} numbers of empty stars.
     */
    private String calculateStars(Integer review) {
        String result = String.join("", Collections.nCopies(review, "★"));
        result += String.join("", Collections.nCopies(5 - review, "☆"));
        return result;
    }

    /**
     * This method is called when the write review button is clicked.
     * It opens a modal after the user interacts with the other window, it checks
     * if the {@code last_review} is set, if it is it adds it to the review, sets back the
     * {@code last_review} to {@code null}, reloads the review, disables the review button,
     * clears the average reviews and reloads them.
     * @param event the action event
     * @see bookReviewController
     */
    @FXML
    public void onWriteReviewButtonClick(ActionEvent event) {
        // opens a modal of the book_review window
        WindowsUtils.openDialogWindow(event, Windows.BOOK_REVIEW);
        // if the last review is set
        if (LoadedData.last_review != null) {
            // add the review to the list
            reviews.add(0, LoadedData.last_review);
            // removes the last review
            LoadedData.last_review = null;
            // reloads the reviews
            reloadReviews();
            // disable the write review button
            B_writeReview.setDisable(true);
            // clears and reloads the average stars
            VBaverageVotesCotnainer.getChildren().clear();
            VBaverageVotesCotnainer.getChildren().add(GPaverageReviews);
            reloadAverageVotes();
        }
    }

    /**
     * This method is called when a row of the reviews table is clicked.
     * It checks for double mouse click, gets the current row review, saves the {@code last_review} just in case.
     * Opens a modal to inspect the review and after it sets back the old review as the last one.
     * @param mouseEvent the mouse event
     * @see inspectReviewController
     * @see MouseEvent
     */
    @FXML
    public void onReviewTableMouseClick(MouseEvent mouseEvent) {
        // checks for double click
        if (mouseEvent.getClickCount() == 2) {
            // gets the current row review
            Review review = TVreviews.getSelectionModel().getSelectedItem();
            // saves the old review
            Review old_review = LoadedData.last_review;

            // puts new current row review as the last one
            LoadedData.last_review = review;
            // opens a modal window so the user can inspect the review
            WindowsUtils.openDialogWindow(mouseEvent, Windows.INSPECT_REVIEW);
            // sets back the old review
            LoadedData.last_review = old_review;
        }
    }

    /**
     * Reloads the reviews in the table by setting the data in the table to an empty list,
     * and then to the review list. And by enabling and then disabling the {@code ACreviewsTable}
     */
    private void reloadReviews() {
        TVreviews.setItems(FXCollections.observableList(new ArrayList<>()));
        TVreviews.setItems(FXCollections.observableArrayList(reviews));
        ACreviewsTable.setVisible(true);
        ACreviewsTable.setDisable(false);
    }


    /**
     * This method is called when the UUID hyperlink is clicked.
     * It copies the book uuid to the clipboard.
     */
    @FXML
    public void onUIDDHyperlinkClick() {
        ClipboardContent content = new ClipboardContent();
        content.putString(HLuuid.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }

    /**
     * The implementation of the login event
     */
    @Override
    public void onLogin() {
        tryEnablingReviewButton();
    }


    /**
     * On close of the tab.
     * Remove it from the {@code loginEvent} listeners
     *
     * @param event the event
     * @see io.github.devdoctor.deltabooks.events.LoginEvent
     */
    public void onClose(Event event) {
        LoadedData.loginEvent.removeListener(this);
    }
}
