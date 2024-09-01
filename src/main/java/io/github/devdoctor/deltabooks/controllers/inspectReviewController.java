package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import io.github.devdoctor.deltabooks.utility.BookUtils;
import io.github.devdoctor.deltabooks.utility.UserUtils;
import io.github.devdoctor.deltabooks.utility.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class inspectReviewController implements Initializable {

    @FXML
    protected Label Ltitle;

    @FXML
    protected ListView<String> LVreaderAdvices;

    @FXML
    protected TextArea TAstyleNote, TAcontentNote, TAnicenessNote, TAoriginalityNote, TAeditionNote;

    @FXML
    protected HBox HBstyleStars, HBcontentStars, HBnicenessStars, HBoriginalityStars, HBeditionStars;

    Review review;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        review = LoadedData.last_review;

        User user = UserUtils.getUserFromUUID(review.getCreatorUuidAsObj());

        if(user != null) {
            Ltitle.setText("Recensione di " + Utils.capitalize(user.getName()) + " " + Utils.capitalize(user.getLastname()));
        }

        if(!review.getBooks_uuid().isEmpty()) {
            review.getBooks_uuid().forEach(s -> {
                Book book = BookUtils.searchBookByUUID(UUID.fromString(s));
                LVreaderAdvices.getItems().add(book.getTitle());
            });
        } else {
            LVreaderAdvices.getItems().add(Utils.capitalize(user.getName()) + " non ha consigliato libri.");
        }

        TAstyleNote.setText(review.getStyle().getNote());
        TAcontentNote.setText(review.getContent().getNote());
        TAnicenessNote.setText(review.getNiceness().getNote());
        TAoriginalityNote.setText(review.getOriginality().getNote());
        TAeditionNote.setText(review.getEdition().getNote());

        loadStarsVotes();
    }

    private void loadStarsVotes() {
        Star.generateStars(HBstyleStars, Float.valueOf(review.getStyle().getValue()));
        Star.generateStars(HBcontentStars, Float.valueOf(review.getContent().getValue()));
        Star.generateStars(HBnicenessStars, Float.valueOf(review.getNiceness().getValue()));
        Star.generateStars(HBoriginalityStars, Float.valueOf(review.getOriginality().getValue()));
        Star.generateStars(HBeditionStars, Float.valueOf(review.getEdition().getValue()));
    }
}
