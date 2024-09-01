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

public class bookReviewController implements Initializable {
    static ArrayList<Book> recommendedBooks;
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
    protected HBox HBbookRecomParent;

    @FXML
    protected TextArea TAstyleNote, TAcontentNote, TAnicenessNote, TAoriginalityNote, TAeditionNote;

    ToggleGroup styleToggleGroup;
    ToggleGroup contentToggleGroup;
    ToggleGroup nicenessToggleGroup;
    ToggleGroup originalityToggleGroup;
    ToggleGroup editionToggleGroup;

    protected Book REVIEW_BOOK;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recommendedBooks = new ArrayList<Book>();
        REVIEW_BOOK = LoadedData.current_looked_book;
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

        ArrayList<Button> recommendationButtons = getButtons();

        HBbookRecomParent.setSpacing(5);
        HBbookRecomParent.getChildren().addAll(recommendationButtons);

        // create the pattern to limit the note size
        Pattern pattern = Pattern.compile(".{0," + Review.MAX_NOTE_SIZE + "}");

        // set the formatter for each text area
        for (TextArea textArea : textAreas) {
            textArea.setTextFormatter(new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->{
                return pattern.matcher(change.getControlNewText()).matches() ? change : null;
            }));
        }

        // set the default max size
        LstyleCharCounter.setText(String.valueOf(Review.MAX_NOTE_SIZE));
    }

    private static ArrayList<Button> getButtons() {
        ArrayList<Button> recommendationButtons = new ArrayList<Button>();
        for (int i = 0; i < LoadedData.MAX_BOOK_RECOMMENDATIONS; i++) {
            Button new_btn = new Button("Scegli un libro");
            new_btn.setOnAction(event -> {
                Button this_btn = (Button) event.getSource();
                int nBooks = bookReviewController.recommendedBooks.size();
                WindowsUtils.openDialogWindow(event, Windows.BOOK_DIALOG, false);
                if(nBooks != bookReviewController.recommendedBooks.size()) {
                    Book book = bookReviewController.recommendedBooks.get(
                            bookReviewController.recommendedBooks.size() - 1
                    );
                    this_btn.setText(Utils.cutStringSize(book.getTitle()));
                    this_btn.setDisable(true);
                    this_btn.setTooltip(new Tooltip(book.getTitle()));
                }
            });
            recommendationButtons.add(new_btn);
        }
        return recommendationButtons;
    }

    @FXML
    public void onSaveReviewButtonClick() {
        if(styleToggleGroup.getSelectedToggle() == null) {
            System.err.println("Non hai votato lo stile!");
            return;
        }
        if(contentToggleGroup.getSelectedToggle() == null) {
            System.err.println("Non hai votato il contenuto!");
            return;
        }
        if(nicenessToggleGroup.getSelectedToggle() == null) {
            System.err.println("Non hai votato la gradevolezza!");
            return;
        }
        if(originalityToggleGroup.getSelectedToggle() == null) {
            System.err.println("Non hai votato l'originalita'!");
            return;
        }
        if(editionToggleGroup.getSelectedToggle() == null) {
            System.err.println("Non hai votato l'edizione!");
            return;
        }

        // MISSING RECOMMENDED BOOK CHECKING

        int styleValue = getRadioValue(styleToggleGroup);
        int contentValue =  getRadioValue(contentToggleGroup);
        int nicenessValue = getRadioValue(nicenessToggleGroup);
        int originalityValue = getRadioValue(originalityToggleGroup);
        int editionValue = getRadioValue(editionToggleGroup);

        ReviewType style = new ReviewType(styleValue, TAstyleNote.getText());
        ReviewType content = new ReviewType(contentValue, TAcontentNote.getText());
        ReviewType niceness = new ReviewType(nicenessValue, TAnicenessNote.getText());
        ReviewType originality = new ReviewType(originalityValue, TAoriginalityNote.getText());
        ReviewType edition = new ReviewType(editionValue, TAeditionNote.getText());

        recommendedBooks.remove(REVIEW_BOOK);
        Collection<String> books_uuids = new ArrayList<String>();
        recommendedBooks.forEach(book -> books_uuids.add(book.getUuid()));

        Review review = new Review(style, content, niceness, originality, edition, books_uuids, LoadedData.logged_user.getUUID());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);

        if(BookUtils.addReview(REVIEW_BOOK, review)) {
            alert.setContentText("Recensione aggiunta con successo!");
            System.out.println("Recensione aggiunta con successo!");
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("C'e' stato un problema a salvare la recensione!");
            System.err.println("C'e' stato un problema a salvare la recensione!");
        }

        alert.show();

        // sets true to check for new review
        LoadedData.last_review = review;

        Stage stage = (Stage) TAstyleNote.getScene().getWindow();
        stage.close();
    }

    private void setToggleGroup(RadioButton[] buttonList, ToggleGroup group) {
        for (RadioButton r : buttonList) {
            r.setToggleGroup(group);
        }
    }

    private int getRadioValue(ToggleGroup tg) {
        return Integer.parseInt(((RadioButton) tg.getSelectedToggle()).getText());
    }

    @FXML
    public void onStyleKeyTyped() {
        updateCharCounter(TAstyleNote, LstyleCharCounter);
    }

    @FXML
    public void onContentKeyTyped() {
        updateCharCounter(TAcontentNote, LcontentCharCounter);
    }

    @FXML
    public void onNicenessKeyTyped() {
        updateCharCounter(TAnicenessNote, LnicenessCharCounter);
    }

    @FXML
    public void onOriginalityKeyTyped() {
        updateCharCounter(TAoriginalityNote, LoriginalityCharCounter);
    }

    @FXML
    public void onEditionKeyTyped() {
        updateCharCounter(TAeditionNote, LoriginalityCharCounter);
    }

    private void updateCharCounter(TextArea textArea, Label label) {
        int value = Review.MAX_NOTE_SIZE - textArea.getText().length();
        label.setText(String.valueOf(value));

        int half = Review.MAX_NOTE_SIZE / 2;
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
