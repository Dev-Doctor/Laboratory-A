package io.github.devdoctor.deltabooks.controllers;

import com.google.gson.Gson;
import io.github.devdoctor.deltabooks.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private Tab TAB_placeholder;

    @FXML
    private TextField TF_author;
    @FXML
    private TextField TF_title;
    @FXML
    private TextField TF_year;

    @FXML
    private ToolBar TB_userSide;

    @FXML
    private TabPane TP_books;
    @FXML
    private TabPane TPmaster;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        L_pageTitle.setText(DeltaBooks.PROGRAM_NAME);
        TC_title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TC_author.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        TW_books.setItems(FXCollections.observableArrayList(LoadedData.books));

    }

    @FXML
    public void clickItem(MouseEvent event) {
        //Checking double click
        if (event.getClickCount() == 2) {
            LoadedData.current_looked_book = TW_books.getSelectionModel().getSelectedItem();
            addNewTab();
            TPmaster.getSelectionModel().select(2);
//            WindowsUtils.openDialogWindow(event, Windows.BOOK_OVERVIEW);
//            LoadedData.current_looked_book = null;
        }
    }


    @FXML
    protected void onTestButtonClick() {
        Random rnd = new Random();
        LoadedData.current_looked_book = (Book) LoadedData.books.toArray()[rnd.nextInt(LoadedData.books.size())];
    }

    @FXML
    protected void onResetSearchButtonClick() {
        TF_author.setText("");
        TF_title.setText("");
        TF_year.setText("");
        TW_books.getItems().clear();
        TW_books.setItems(FXCollections.observableArrayList(LoadedData.books));
    }

    @FXML
    protected void onSearchButtonClick() {
        String author = TF_author.getText();
        String title = TF_title.getText();
        String year = TF_year.getText();

        TW_books.setItems(FXCollections.observableArrayList(BookUtils.searchBook(title, author, year)));
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

    private void addNewTab() {
        FXMLLoader loader = new FXMLLoader(DeltaBooks.class.getResource("bookTab.fxml"));
        Parent tabContent = null;
        try {
            tabContent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String new_title = LoadedData.current_looked_book.getTitle();
        try {
            new_title = new_title.substring(0, 10);
            new_title += "..";
        } catch (Exception e){

        }
//        Tab tab = new Tab("Libro " + TP_books.getTabs().size());
        Tab tab = new Tab(new_title);
        tab.setOnClosed(((bookpageController) loader.getController())::onClose);
        tab.setTooltip(new Tooltip(LoadedData.current_looked_book.getTitle()));
        tab.setContent(tabContent);

        TP_books.getTabs().add(tab);
        TP_books.getSelectionModel().select(tab);
    }

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
