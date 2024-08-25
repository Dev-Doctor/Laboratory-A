package io.github.devdoctor.deltabooks.controllers;

import com.google.gson.Gson;
import io.github.devdoctor.deltabooks.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.UUID;

public class masterController implements Initializable {

    @FXML
    private BorderPane BP_firstTabPane;
    @FXML
    private BorderPane BP_master;

    @FXML
    private Button B_login;
    @FXML
    private Button B_register;

    @FXML
    private Label L_pageTitle;

    @FXML
    private TableColumn<Book, String> TC_author;
    @FXML
    private TableColumn<Book, String> TC_title;

    @FXML
    private TableView<Book> TW_books;

    @FXML
    private TextField TF_author;
    @FXML
    private TextField TF_title;
    @FXML
    private TextField TF_year;

    @FXML
    private ToolBar TB_userSide;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        L_pageTitle.setText(DeltaBooks.PROGRAM_NAME);
        TC_title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TC_author.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        TW_books.setItems(FXCollections.observableArrayList(LoadedData.books));
    }

    @FXML
    public void clickItem(MouseEvent event)
    {
        //Checking double click
        if (event.getClickCount() == 2) {
            LoadedData.current_looked_book = TW_books.getSelectionModel().getSelectedItem();
            WindowsUtils.openDialogWindow(event, Windows.BOOK_OVERVIEW);
            LoadedData.current_looked_book = null;
        }
    }

    @FXML
    protected void onTestButtonClick() {
        TW_books.setItems(FXCollections.observableArrayList(new ArrayList<Book>()));
    }

    @FXML
    protected void onResetSearchButtonClick() {
        TF_author.setText("");
        TF_title.setText("");
        TF_year.setText("");
        TW_books.setItems(FXCollections.observableArrayList(LoadedData.books));
    }

    @FXML
    protected void onSearchButtonClick() {
        String author = TF_author.getText();
        String title = TF_title.getText();
        String year = TF_year.getText();

        TW_books.setItems(FXCollections.observableArrayList(BookUtils.searchBookByTitle(title)));
    }


    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        login(event, Windows.LOGIN);
    }

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) {
        if(LoadedData.logged_user == null) {
            login(event, Windows.REGISTER);
            return;
        }
    }

    private void login(ActionEvent event, Windows window) {
        WindowsUtils.openDialogWindow(event, window);
        if(LoadedData.logged_user != null) {
            B_login.setVisible(false);
            B_login.setDisable(true);
            B_register.setText("User Profile");
        }
    }

    /*
     *
     *
     */
    @Deprecated(since = "0.5", forRemoval = true)
    private void setUUIDs() {
        Collection<Book> update = new ArrayList<Book>();
        for(Book book : LoadedData.books) {
            UUID uuid = UUID.randomUUID();
            book.setUUID(uuid.toString());
            update.add(book);
        }

        String path = LoadedData.config.getBooks_dataset_location() + "/book-catalog.json";
        File f = new File(path);

        if(f.exists()) {
//            String json = new Gson().toJson(update, FileUtils.BOOK_COLLECTION_TYPE.getType());
//            try {
//                FileUtils.createFileWithData(json, path);
//            } catch (IOException e) {
//                System.err.print("Unable to write/edit the file. Check permissions!");
//            }
        }
    }
}
