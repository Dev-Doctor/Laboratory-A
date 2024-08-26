package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class bookReviewController implements Initializable {

    @FXML
    protected Label L_charCounter;

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
    protected TextArea TA_note;
//    private List<RadioButton> styleButtons;
//    private ArrayList<RadioButton> contentButtons;
//    private ArrayList<RadioButton> nicenessButtons;
//    private ArrayList<RadioButton> originalityButtons;
//    private ArrayList<RadioButton> editionButtons;

    ToggleGroup styleToggleGroup;
    ToggleGroup contentToggleGroup;
    ToggleGroup nicenessToggleGroup;
    ToggleGroup originalityToggleGroup;
    ToggleGroup editionToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RadioButton[] styleButtons = new RadioButton[]{RBStyle1, RBStyle2, RBStyle3, RBStyle4, RBStyle5};
        RadioButton[] contentButtons = new RadioButton[]{RBContent1, RBContent2, RBContent3, RBContent4, RBContent5};
        RadioButton[] nicenessButtons = new RadioButton[]{RBNiceness1, RBNiceness2, RBNiceness3, RBNiceness4, RBNiceness5};
        RadioButton[] originalityButtons = new RadioButton[]{RBOriginality1, RBOriginality2, RBOriginality3, RBOriginality4, RBOriginality5};
        RadioButton[] editionButtons = new RadioButton[]{RBEdition1, RBEdition2, RBEdition3, RBEdition4, RBEdition5};

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

        // create the pattern to limit the note size
        Pattern pattern = Pattern.compile(".{0," + Review.MAX_NOTE_SIZE + "}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change ->{
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        TA_note.setTextFormatter(formatter);

        // set the default max size
        L_charCounter.setText(String.valueOf(Review.MAX_NOTE_SIZE));
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

        int style = getRadioValue(styleToggleGroup);
        int content =  getRadioValue(contentToggleGroup);
        int niceness = getRadioValue(nicenessToggleGroup);
        int originality = getRadioValue(originalityToggleGroup);
        int edition = getRadioValue(editionToggleGroup);

        Review review = new Review(style, content, niceness, originality, edition, TA_note.getText(), new ArrayList<Book>(), LoadedData.logged_user.getUUID());
        if(BookUtils.addReview(LoadedData.current_looked_book, review)) {
            System.out.println("Recensione aggiunta con successo!");
        } else {
            System.err.println("C'e' stato un problema a salvare la recensione!");
        }

        // sets true to check for new review
        LoadedData.last_review = review;

        Stage stage = (Stage) L_charCounter.getScene().getWindow();
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
    public void onNoteKeyTyped() {
        int value = Review.MAX_NOTE_SIZE - TA_note.getText().length();
        L_charCounter.setText(String.valueOf(value));

        int half = Review.MAX_NOTE_SIZE / 2;
        if (value > half + (half / 2)) {
            L_charCounter.setTextFill(Color.BLACK);
        } else if (value > half) {
            L_charCounter.setTextFill(Color.GREEN);
        } else if (value > half / 2) {
            L_charCounter.setTextFill(Color.ORANGE);
        } else {
            L_charCounter.setTextFill(Color.RED);
        }
    }
}
