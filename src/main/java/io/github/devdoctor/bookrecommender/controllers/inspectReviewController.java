/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.controllers;

import io.github.devdoctor.bookrecommender.*;
import io.github.devdoctor.bookrecommender.utility.BookUtils;
import io.github.devdoctor.bookrecommender.utility.UserUtils;
import io.github.devdoctor.bookrecommender.utility.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * The Inspect Review window controller.
 *
 * @author DevDoctor
 */
public class inspectReviewController implements Initializable {

    @FXML
    protected HBox HBstyleStars, HBcontentStars, HBnicenessStars, HBoriginalityStars, HBeditionStars;

    @FXML
    protected Label Ltitle;

    @FXML
    protected ListView<String> LVreaderAdvices;

    @FXML
    protected TextArea TAstyleNote, TAcontentNote, TAnicenessNote, TAoriginalityNote, TAeditionNote;

    Review review;

    /**
     * It is called when the window is created.
     *
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        review = LoadedData.last_review;

        // gets the creator of the current review
        User user = UserUtils.getUserFromUUID(review.getCreatorUuidAsObj());

        // if the user exists set is name at the top of the page
        if (user != null) {
            Ltitle.setText("Recensione di " + Utils.capitalize(user.getName()) + " " + Utils.capitalize(user.getLastname()));
        }

        // if the review has book recommendations
        if (!review.getBooks_uuid().isEmpty()) {
            // for each book recommendation
            review.getBooks_uuid().forEach(s -> {
                // get the book
                Book book = BookUtils.searchBookByUUID(UUID.fromString(s));
                // add the book title to the recommended books list
                LVreaderAdvices.getItems().add(book.getTitle());
            });
        } else {
            // write that the user didn't suggest any other books
            LVreaderAdvices.getItems().add(Utils.capitalize(user.getName()) + " non ha consigliato libri.");
        }

        // sets the note of the review for each evaluation
        TAstyleNote.setText(review.getStyle().getNote());
        TAcontentNote.setText(review.getContent().getNote());
        TAnicenessNote.setText(review.getNiceness().getNote());
        TAoriginalityNote.setText(review.getOriginality().getNote());
        TAeditionNote.setText(review.getEdition().getNote());

        // loads the stars for each evaluation
        loadStarsVotes();
    }

    /**
     * generates the stars for each review score.
     */
    private void loadStarsVotes() {
        Star.generateStars(HBstyleStars, Float.valueOf(review.getStyle().getValue()));
        Star.generateStars(HBcontentStars, Float.valueOf(review.getContent().getValue()));
        Star.generateStars(HBnicenessStars, Float.valueOf(review.getNiceness().getValue()));
        Star.generateStars(HBoriginalityStars, Float.valueOf(review.getOriginality().getValue()));
        Star.generateStars(HBeditionStars, Float.valueOf(review.getEdition().getValue()));
    }
}
