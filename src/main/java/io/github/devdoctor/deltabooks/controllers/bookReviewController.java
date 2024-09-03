/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import io.github.devdoctor.deltabooks.utility.BookUtils;
import io.github.devdoctor.deltabooks.utility.Utils;
import io.github.devdoctor.deltabooks.utility.WindowsUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


/**
 * The book review window controller.
 */
public class bookReviewController implements Initializable {
    static ArrayList<Book> recommendedBooks;

    @FXML
    protected HBox HBbookRecomParent;

    @FXML
    protected Label LstyleCharCounter, LcontentCharCounter, LnicenessCharCounter, LoriginalityCharCounter, LeditionCharCounter;

    @FXML
    protected RadioButton RBStyle1, RBStyle2, RBStyle3, RBStyle4, RBStyle5;
    @FXML
    protected RadioButton RBContent1, RBContent2, RBContent3, RBContent4, RBContent5;
    @FXML
    protected RadioButton RBNiceness1, RBNiceness2, RBNiceness3, RBNiceness4, RBNiceness5;
    @FXML
    protected RadioButton RBOriginality1, RBOriginality2, RBOriginality3, RBOriginality4, RBOriginality5;
    @FXML
    protected RadioButton RBEdition1, RBEdition2, RBEdition3, RBEdition4, RBEdition5;

    @FXML
    protected TextArea TAstyleNote, TAcontentNote, TAnicenessNote, TAoriginalityNote, TAeditionNote;

    ToggleGroup styleToggleGroup;
    ToggleGroup contentToggleGroup;
    ToggleGroup nicenessToggleGroup;
    ToggleGroup originalityToggleGroup;
    ToggleGroup editionToggleGroup;

    protected Book REVIEW_BOOK;

    /**
     * It is called when the window is created.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recommendedBooks = new ArrayList<Book>();
        // current book
        REVIEW_BOOK = LoadedData.current_looked_book;
        // adds the current book to the recommended ones for later
        recommendedBooks.add(REVIEW_BOOK);

        // prepare different array with same vote type radio buttons
        RadioButton[] styleButtons = new RadioButton[]{RBStyle1, RBStyle2, RBStyle3, RBStyle4, RBStyle5};
        RadioButton[] contentButtons = new RadioButton[]{RBContent1, RBContent2, RBContent3, RBContent4, RBContent5};
        RadioButton[] nicenessButtons = new RadioButton[]{RBNiceness1, RBNiceness2, RBNiceness3, RBNiceness4, RBNiceness5};
        RadioButton[] originalityButtons = new RadioButton[]{RBOriginality1, RBOriginality2, RBOriginality3, RBOriginality4, RBOriginality5};
        RadioButton[] editionButtons = new RadioButton[]{RBEdition1, RBEdition2, RBEdition3, RBEdition4, RBEdition5};

        TextArea[] textAreas = new TextArea[]{TAstyleNote, TAcontentNote, TAnicenessNote, TAoriginalityNote, TAeditionNote};
        Label[] labels = new Label[]{LstyleCharCounter, LcontentCharCounter, LnicenessCharCounter, LoriginalityCharCounter, LeditionCharCounter};

        // initialize toggle groups
        styleToggleGroup = new ToggleGroup();
        contentToggleGroup = new ToggleGroup();
        nicenessToggleGroup = new ToggleGroup();
        originalityToggleGroup = new ToggleGroup();
        editionToggleGroup = new ToggleGroup();

        // set the different groups for the radiobuttons
        setToggleGroup(styleButtons, styleToggleGroup);
        setToggleGroup(contentButtons, contentToggleGroup);
        setToggleGroup(nicenessButtons, nicenessToggleGroup);
        setToggleGroup(originalityButtons, originalityToggleGroup);
        setToggleGroup(editionButtons, editionToggleGroup);

        // gets the new button for the recommended books
        ArrayList<Button> recommendationButtons = getButtons();

        // sets the spacing of the HBox
        HBbookRecomParent.setSpacing(5);
        // adds all the recommendation buttons to the HBox
        HBbookRecomParent.getChildren().addAll(recommendationButtons);

        // create the pattern to limit the note size
        Pattern pattern = Pattern.compile(".{0," + Review.MAX_NOTE_SIZE + "}");

        // set the formatter for each text area
        for (TextArea textArea : textAreas) {
            textArea.setTextFormatter(new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
                return pattern.matcher(change.getControlNewText()).matches() ? change : null;
            }));
        }

        // set the default max size as display text for each label
        for (Label label : labels) {
            label.setText(String.valueOf(Review.MAX_NOTE_SIZE));
        }
    }

    /**
     * Create all the {@code MAX_BOOK_RECOMMENDATIONS} buttons
     *
     * @return a list containing all the recommendation buttons
     * @see bookDialogController
     * @see Utils#cutStringSize(String)
     */
    private static ArrayList<Button> getButtons() {
        // the result array
        ArrayList<Button> recommendationButtons = new ArrayList<Button>();
        // for MAX_BOOK_RECOMMENDATIONS recommended buttons
        for (int i = 0; i < LoadedData.MAX_BOOK_RECOMMENDATIONS; i++) {
            // create a new button with the text "Scegli un libro"
            Button new_btn = new Button("Scegli un libro");
            // sets the action of the new button
            new_btn.setOnAction(event -> {
                // gets itself
                Button this_btn = (Button) event.getSource();
                // gets the already recommended books quantity
                int nBooks = bookReviewController.recommendedBooks.size();
                // opens a new modal window to choose which book is going to be recommended
                WindowsUtils.openDialogWindow(event, Windows.BOOK_DIALOG, false);
                // if the nbook is smaller than the new array size
                if (nBooks != bookReviewController.recommendedBooks.size()) {
                    // gets the last added book as the new book
                    Book book = bookReviewController.recommendedBooks.get(
                            bookReviewController.recommendedBooks.size() - 1
                    );
                    // sets the new button text with the book title
                    this_btn.setText(Utils.cutStringSize(book.getTitle()));
                    // disables the button
                    this_btn.setDisable(true);
                    // sets the tooltip of the button with the full title
                    this_btn.setTooltip(new Tooltip(book.getTitle()));
                }
            });
            // adds the new button to the list
            recommendationButtons.add(new_btn);
        }
        return recommendationButtons;
    }

    /**
     * It is called when the save review button is clicked. It displays a message if
     * the review is missing data and doesn't save the review, else it creates the new
     * review display a message to the user and closes the window.
     */
    @FXML
    public void onSaveReviewButtonClick() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
        String alert_msg = "";
        if (styleToggleGroup.getSelectedToggle() == null) {
            alert_msg += "Non hai votato lo stile!" + "\n";
            System.err.println("Non hai votato lo stile!");
        }
        if (contentToggleGroup.getSelectedToggle() == null) {
            alert_msg += "Non hai votato il contenuto!" + "\n";
            System.err.println("Non hai votato il contenuto!");
        }
        if (nicenessToggleGroup.getSelectedToggle() == null) {
            alert_msg += "Non hai votato la gradevolezza!" + "\n";
            System.err.println("Non hai votato la gradevolezza!");
        }
        if (originalityToggleGroup.getSelectedToggle() == null) {
            alert_msg += "Non hai votato l'originalita'!" + "\n";
            System.err.println("Non hai votato l'originalita'!");
        }
        if (editionToggleGroup.getSelectedToggle() == null) {
            alert_msg += "Non hai votato l'edizione!" + "\n";
            System.err.println("Non hai votato l'edizione!");
        }

        // if the alert message is not empty it means that there are some errors in the review
        if(!alert_msg.isEmpty()) {
            // set the alert message
            alert.setContentText(alert_msg);
            // shows the alert message
            alert.showAndWait();
            return;
        }

        // gets the radio button values for each evaluation
        int styleValue = getRadioValue(styleToggleGroup);
        int contentValue = getRadioValue(contentToggleGroup);
        int nicenessValue = getRadioValue(nicenessToggleGroup);
        int originalityValue = getRadioValue(originalityToggleGroup);
        int editionValue = getRadioValue(editionToggleGroup);

        // gets the note for each evaluation
        ReviewType style = new ReviewType(styleValue, TAstyleNote.getText());
        ReviewType content = new ReviewType(contentValue, TAcontentNote.getText());
        ReviewType niceness = new ReviewType(nicenessValue, TAnicenessNote.getText());
        ReviewType originality = new ReviewType(originalityValue, TAoriginalityNote.getText());
        ReviewType edition = new ReviewType(editionValue, TAeditionNote.getText());

        // removes this book from the recommendations
        recommendedBooks.remove(REVIEW_BOOK);

        // create a new book uuid list that will contain each recommended book
        Collection<String> books_uuids = new ArrayList<String>();
        // adds each book uuid to the newly created list
        recommendedBooks.forEach(book -> books_uuids.add(book.getUuid()));

        // creates a new review with the provided user data
        Review review = new Review(style, content, niceness, originality, edition, books_uuids, LoadedData.logged_user.getUUID());

        if (BookUtils.addReview(REVIEW_BOOK, review)) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Recensione aggiunta con successo!");
            System.out.println("Recensione aggiunta con successo!");
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("C'e' stato un problema a salvare la recensione!");
            System.err.println("C'e' stato un problema a salvare la recensione!");
        }

        // show the alert message
        alert.show();

        // sets true to check for new review
        LoadedData.last_review = review;

        // close the window
        Stage stage = (Stage) TAstyleNote.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets a {@code Toggle Group} to a given list of {@code Radio Buttons}
     * @param buttonList the list of buttons
     * @param group the toggle group
     */
    private void setToggleGroup(RadioButton[] buttonList, ToggleGroup group) {
        for (RadioButton r : buttonList) {
            r.setToggleGroup(group);
        }
    }

    /**
     * Gets the radio value from the radio button name, and it parses it to an integer.
     * @param tg the toggle group
     * @return the integer value of the name
     */
    private int getRadioValue(ToggleGroup tg) {
        return Integer.parseInt(((RadioButton) tg.getSelectedToggle()).getText());
    }

    /**
     * This method is called when the user is typing in the note of the style.
     */
    @FXML
    public void onStyleKeyTyped() {
        updateCharCounter(TAstyleNote, LstyleCharCounter);
    }

    /**
     * This method is called when the user is typing in the note of the content.
     */
    @FXML
    public void onContentKeyTyped() {
        updateCharCounter(TAcontentNote, LcontentCharCounter);
    }

    /**
     * This method is called when the user is typing in the note of the niceness.
     */
    @FXML
    public void onNicenessKeyTyped() {
        updateCharCounter(TAnicenessNote, LnicenessCharCounter);
    }

    /**
     * This method is called when the user is typing in the note of the originality.
     */
    @FXML
    public void onOriginalityKeyTyped() {
        updateCharCounter(TAoriginalityNote, LoriginalityCharCounter);
    }

    /**
     * This method is called when the user is typing in the note of the edition.
     */
    @FXML
    public void onEditionKeyTyped() {
        updateCharCounter(TAeditionNote, LoriginalityCharCounter);
    }

    /**
     * Changes the value and the color of the passed {@code label}
     * using the number of characters from the passed {@code textArea}
     * @param textArea the passed textArea
     * @param label the label to change
     */
    private void updateCharCounter(TextArea textArea, Label label) {
        // gets the last remaining writable characters
        int value = Review.MAX_NOTE_SIZE - textArea.getText().length();
        // sets the last remaining writable characters as the label text
        label.setText(String.valueOf(value));

        // gets the half of the max note size
        int half = Review.MAX_NOTE_SIZE / 2;
        // changes the color of the label depending on the number of characters remaining
        if (value > half + (half / 2)) {
            label.setTextFill(Color.BLACK);
        } else if (value > half) {
            label.setTextFill(Color.GREEN);
        } else if (value > half / 2) {
            label.setTextFill(Color.ORANGE);
        } else {
            label.setTextFill(Color.RED);
        }
    }
}
