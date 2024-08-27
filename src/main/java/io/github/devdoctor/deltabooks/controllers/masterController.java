package io.github.devdoctor.deltabooks.controllers;

import io.github.devdoctor.deltabooks.*;
import io.github.devdoctor.deltabooks.events.LoginEvent;
import io.github.devdoctor.deltabooks.events.LoginEventListener;
import io.github.devdoctor.deltabooks.events.UpdateUserEventListener;
import io.github.devdoctor.deltabooks.utility.BookUtils;
import io.github.devdoctor.deltabooks.utility.LibraryUtils;
import io.github.devdoctor.deltabooks.utility.WindowsUtils;
import javafx.beans.property.SimpleStringProperty;
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

public class masterController implements Initializable, LoginEventListener, UpdateUserEventListener {

    @FXML
    private BorderPane BP_firstTabPane;
    @FXML
    private BorderPane BP_master;

    @FXML
    private Button BaddToLibrary;
    @FXML
    private Button BdeleteLibrary;
    @FXML
    private Button B_login;
    @FXML
    private Button B_register;

    @FXML
    private Label L_pageTitle;

    /**
     * Author column of the book table
     * @see #TWbooks
     */
    @FXML
    private TableColumn<Book, String> TC_author;
    /**
     * Title column of the book table
     * @see #TWbooks
     */
    @FXML
    private TableColumn<Book, String> TC_title;
    /**
     * Number of books column of the libraries table
     * @see #TWlibraries
     */
    @FXML
    private TableColumn<Library, String> TCnumberOfBooks;
    /**
     * Library name column of the libraries table
     * @see #TWlibraries
     */
    @FXML
    private TableColumn<Library, String> TClibraryNames;

    /** Table of the books collection */
    @FXML
    private TableView<Book> TWbooks;
    /** Table of the libraries list */
    @FXML
    private TableView<Library> TWlibraries;

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
        // sets the name of the program
        L_pageTitle.setText(DeltaBooks.PROGRAM_NAME);
        // sets the columbs of the Books Table
        TC_title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TC_author.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        // sets the columbs of the Libraries Table
        TClibraryNames.setCellValueFactory(new PropertyValueFactory<Library, String>("name"));
        TCnumberOfBooks.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(cellData.getValue().getBooks_uuids().size()));
        });

        TWlibraries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // adds this Object as a listener of the login event
        LoadedData.loginEvent.addListener(this);
        LoadedData.userEvent.addListener(this);

        // loads the data to the Books Table
        TWbooks.setItems(FXCollections.observableArrayList(LoadedData.books));
    }


    /**
     * This method is called when a row of the table of {@code Books} is clicked.
     * It checks for double click, if found opens a new book tab in its {@code TabPane}.
     *
     * @param event The mouse event
     * @see #TWbooks
     * @see MouseEvent
     */
    @FXML
    protected void clickItem(MouseEvent event) {
        //Checking double click
        if (event.getClickCount() == 2) {
            LoadedData.current_looked_book = TWbooks.getSelectionModel().getSelectedItem();
            addNewTab();
            TPmaster.getSelectionModel().select(0);
//            WindowsUtils.openDialogWindow(event, Windows.BOOK_OVERVIEW);
//            LoadedData.current_looked_book = null;
        }
    }
    @FXML
    protected void onCreateLibraryButtonClick() {
        Book selected = TWbooks.getSelectionModel().getSelectedItem();
    }

    @FXML
    protected void onDeleteLibraryButtonClick() {
        List<Library> selected_items = TWlibraries.getSelectionModel().getSelectedItems();

        if(selected_items == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Seleziona una o piu' librerie per cancellarla/e.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);;

        if(selected_items.size() > 1) {
            alert.setContentText("Sei sicuro di voler cancellare: " + selected_items.size() + " librerie?");
        } else {
            alert.setContentText("Sei sicuro di voler cancellare la libreria: \'" + selected_items.get(0).getName() + "\'?");
        }
        alert.showAndWait();

        if(alert.getResult() == ButtonType.YES) {
            LibraryUtils.deleteLibraries(selected_items);
            TWlibraries.setItems(FXCollections.observableArrayList(LoadedData.logged_user_libraries));
        }
    }

    @FXML
    protected void onAddToLibraryButtonClick(ActionEvent event) {
        Book candidate = TWbooks.getSelectionModel().getSelectedItem();
        System.out.println(candidate);
        if(candidate == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Scegli un libro da aggiungere.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        LoadedData.current_looked_book = candidate;
        WindowsUtils.openDialogWindow(event, Windows.LIBRARY_MODAL);
    }

    @FXML
    protected void onTestButtonClick() {
        for (User c : LoadedData.users) {
            System.out.println(c.print());
        }
    }

    /**
     * On the reset search button click it empties the search fields,
     * clears the Book collection and reloads the loaded Book collection.
     *
     * @see LoadedData#books
     */
    @FXML
    protected void onResetSearchButtonClick() {
        // empties the search fields
        TF_author.setText("");
        TF_title.setText("");
        TF_year.setText("");
        // clears the table data
        TWbooks.getItems().clear();
        // and reloads the book collection
        TWbooks.setItems(FXCollections.observableArrayList(LoadedData.books));
    }


    /**
     * On the search button click gets the search data,
     * it passes it to the search function and sets it as the table data.
     *
     * @see BookUtils#searchBook(String, String, String)
     */
    @FXML
    protected void onSearchButtonClick() {
        String author = TF_author.getText();
        String title = TF_title.getText();
        String year = TF_year.getText();

        TWbooks.setItems(FXCollections.observableArrayList(BookUtils.searchBook(title, author, year)));
    }


    /**
     * On the login button click opens a new login page as a {@code Modal}.
     *
     * @param event the action event
     * @see ActionEvent
     */
    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        WindowsUtils.openDialogWindow(event, Windows.LOGIN);
    }

    /**
     * On the register button click, if the user is not logged in,
     * opens a new register page as a {@code Modal} else opens the User Profile
     *
     * @param event the action event
     * @see ActionEvent
     */
    @FXML
    protected void onRegisterButtonClick(ActionEvent event) {
        if(LoadedData.logged_user == null) {
            WindowsUtils.openDialogWindow(event, Windows.REGISTER);
            return;
        }
    }

    /**
     * It adds a new tab to the Book {@code TabPane} with the current selected book.
     *
     */
    private void addNewTab() {
        // initializes the resource
        FXMLLoader loader = new FXMLLoader(DeltaBooks.class.getResource("bookTab.fxml"));
        Parent tabContent = null;
        try {
            // tries to load the resource
            tabContent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // gets the book title
        String new_title = LoadedData.current_looked_book.getTitle();
        // tries to cut the lenght of the title to 10 and adds ".." at the end of it.
        try {
            new_title = new_title.substring(0, 10);
            new_title += "..";
        } catch (Exception ignored){}
        // creates a new empty tab with the cut book title
        Tab tab = new Tab(new_title);
        // sets the method when the tab is closed
        tab.setOnClosed(((bookpageController) loader.getController())::onClose);
        // sets the tooltip of the tab to match the full book name
        tab.setTooltip(new Tooltip(LoadedData.current_looked_book.getTitle()));
        // sets the empty tab content to the loaded one
        tab.setContent(tabContent);

        // adds the tab to the Books TabPane
        TP_books.getTabs().add(tab);
        // selects it has the current viewed tab
        TP_books.getSelectionModel().select(tab);
    }

    /**
     * This method it's the implementation of the logginEvent
     * it is called when the User logs in, and disables the login button,
     * changes the register button and loads the libraries
     *
     * @see LoginEvent
     */
    @Override
    public void onLogin() {
        // disable the login button
        B_login.setVisible(false);
        B_login.setDisable(true);

        // enable library commands
        BaddToLibrary.setDisable(false);
        BdeleteLibrary.setDisable(false);

        // change the register button
        B_register.setText("User Profile");

        // load libraries
        loadLibraries();
    }

    /**
     * Loads the libraries of the logged User
     */
    private void loadLibraries() {
        List<Library> libraries = LoadedData.logged_user_libraries;
        TWlibraries.setItems(FXCollections.observableList(libraries));
    }

    @Deprecated(since = "0.5", forRemoval = true)
    private void setUUIDs() {
        Collection<Book> update = new ArrayList<Book>();
        for(Book book : LoadedData.books) {
            UUID uuid = UUID.randomUUID();
            book.setUuid(uuid.toString());
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

    @Override
    public void onUpdate() {
        TWlibraries.getItems().clear();
        System.out.println(LoadedData.logged_user_libraries);
        TWlibraries.setItems(FXCollections.observableList(LoadedData.logged_user_libraries));
    }
}
