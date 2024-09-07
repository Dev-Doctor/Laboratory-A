/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.controllers;

import io.github.devdoctor.bookrecommender.*;
import io.github.devdoctor.bookrecommender.events.LoginEvent;
import io.github.devdoctor.bookrecommender.events.LoginEventListener;
import io.github.devdoctor.bookrecommender.events.UpdateUserEventListener;
import io.github.devdoctor.bookrecommender.utility.BookUtils;
import io.github.devdoctor.bookrecommender.utility.LibraryUtils;
import io.github.devdoctor.bookrecommender.utility.Utils;
import io.github.devdoctor.bookrecommender.utility.WindowsUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * The master window controller.
 *
 * @author DevDoctor
 */
public class masterController implements Initializable, LoginEventListener, UpdateUserEventListener {
    @FXML
    protected BorderPane BPfirstTabPane;
    @FXML
    protected BorderPane BPmaster;

    @FXML
    protected Button BaddToLibrary;
    @FXML
    protected Button BaddLibrary;
    @FXML
    protected Button BdeleteLibrary;
    @FXML
    protected Button Blogin;
    @FXML
    protected Button Bregister;

    @FXML
    protected Hyperlink HLlicense;
    @FXML
    protected Hyperlink HLmyWebsite;
    @FXML
    protected Hyperlink HLissue;

    @FXML
    protected Label LpageTitle;
    @FXML
    protected Label LaboutData;

    @FXML
    protected Tab TsearchBook;
    @FXML
    protected Tab TopenedBooks;

    /**
     * Author column of the book table
     *
     * @see #TWbooks
     */
    @FXML
    protected TableColumn<Book, String> TC_author;
    /**
     * Title column of the book table
     *
     * @see #TWbooks
     */
    @FXML
    protected TableColumn<Book, String> TC_title;
    /**
     * Number of books column of the libraries table
     *
     * @see #TWlibraries
     */
    @FXML
    protected TableColumn<Library, String> TCnumberOfBooks;
    /**
     * Library name column of the libraries table
     *
     * @see #TWlibraries
     */
    @FXML
    protected TableColumn<Library, String> TClibraryNames;

    /**
     * Table of the books collection
     */
    @FXML
    protected TableView<Book> TWbooks;
    /**
     * Table of the libraries list
     */
    @FXML
    protected TableView<Library> TWlibraries;

    @FXML
    protected TabPane TP_books;
    @FXML
    protected TabPane TPmaster;

    @FXML
    protected TextField TF_author;
    @FXML
    protected TextField TF_title;
    @FXML
    protected TextField TF_year;

    @FXML
    protected ToolBar TB_userSide;


    /**
     * When the window is created, sets the window title, initializes the table columns,
     * add this class as a listener of the {@code loginEvent} and the {@code userEvent}.
     * Sets the Table data and selection mode and initializes the about tab.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // sets the name of the program
        LpageTitle.setText(BookRecommender.PROGRAM_NAME);
        // sets the columbs of the Books Table
        TC_title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        TC_author.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        // sets the columbs of the Libraries Table
        TClibraryNames.setCellValueFactory(new PropertyValueFactory<Library, String>("name"));
        TCnumberOfBooks.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(cellData.getValue().getBooks_uuids().size()));
        });

        // change the selection mode for the table from single to multiple
        TWlibraries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // adds this Object as a listener of the login event
        LoadedData.loginEvent.addListener(this);
        LoadedData.userEvent.addListener(this);

        // loads the data to the Books Table
        TWbooks.setItems(FXCollections.observableArrayList(LoadedData.books));

        initializeAbout();
    }

    /**
     * Initializes the about tab with data
     *
     * @see Utils#openWebsite(String)
     */
    private void initializeAbout() {
        // sets the website for the creator
        HLmyWebsite.setOnAction(actionEvent -> {
            Utils.openWebsite("https://dev-doctor.github.io/");
        });
        // sets the license website
        HLlicense.setOnAction(actionEvent -> {
            Utils.openWebsite("https://github.com/Dev-Doctor/Laboratory-A/blob/main/LICENSE");
        });
        // sets the bug report website
        HLissue.setOnAction(actionEvent -> {
            Utils.openWebsite("https://github.com/Dev-Doctor/Laboratory-A/issues");
        });
        // sets some interesting data about the program
        LaboutData.setText("Numero di libri: " + LoadedData.books.size()
                + "\nUtenti registrati: " + LoadedData.users.size()
                + "\nVersion: " + BookRecommender.PROGRAM_VERSION
        );
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
            // get the row book
            Book this_book = TWbooks.getSelectionModel().getSelectedItem();
            // sets it as the current looked book
            LoadedData.current_looked_book = this_book;
            // if the tab is not already open
            if (!LoadedData.loaded_book_tabs.contains(this_book.getUuid())) {
                // add the book to the list of looked books
                LoadedData.loaded_book_tabs.add(this_book.getUuid());
                // add a new tab with the current book
                addNewTab(this_book);
                // select the books tab
                TPmaster.getSelectionModel().select(TopenedBooks);
            }
        }
    }

    /**
     * This method is called when the create library button is clicked.
     * It opens a modal window, not resizable, that will add a new library.
     *
     * @param event The action event
     * @see addLibraryController
     * @see ActionEvent
     */
    @FXML
    protected void onCreateLibraryButtonClick(ActionEvent event) {
        WindowsUtils.openDialogWindow(event, Windows.ADD_LIBRARY, false);
    }

    /**
     * This method is called when the delete library/ies button is clicked.
     * It gets the selected libraries, ask the user for confirmation and then deletes the libraries.
     *
     * @see ActionEvent
     */
    @FXML
    protected void onDeleteLibraryButtonClick() {
        // create a list with the selected libraries
        List<Library> selected_items = TWlibraries.getSelectionModel().getSelectedItems();

        // if the list is empty tell the user to select more libraries
        if (selected_items.isEmpty()) {
            // prepare and show message to user
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Seleziona una o piu' librerie per cancellarla/e.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // prepare confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);

        // if the selected library is only more than one, display message for more than one else for only one
        if (selected_items.size() > 1) {
            alert.setContentText("Sei sicuro di voler cancellare: " + selected_items.size() + " librerie?");
        } else {
            alert.setContentText("Sei sicuro di voler cancellare la libreria: \'" + selected_items.get(0).getName() + "\'?");
        }
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            LibraryUtils.deleteLibraries(selected_items);
            TWlibraries.setItems(FXCollections.observableArrayList(LoadedData.logged_user_libraries));
        }
    }

    /**
     * This method is called when the add book to library button is clicked.
     * It gets the selected book, if its null asks the user to select a book.
     * Otherwise, opens a window for the user to choose a library.
     *
     * @see ActionEvent
     * @see addToLibraryController
     */
    @FXML
    protected void onAddToLibraryButtonClick(ActionEvent event) {
        // get the selected book
        Book candidate = TWbooks.getSelectionModel().getSelectedItem();

        // if its null ask the user to choose a book
        if (candidate == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Scegli un libro da aggiungere.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        LoadedData.current_looked_book = candidate;
        // open the window as a modal
        WindowsUtils.openDialogWindow(event, Windows.LIBRARY_MODAL);
    }

    /**
     * ONLY FOR TEST PURPOSES, NOTHING TO SEE HERE
     */
    @FXML
    protected void onTestButtonClick() {
    }

    /**
     * This method is called when the reset search button is clicked.
     * it empties the search fields, clears the Book collection and reloads the loaded Book collection.
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
     * This method is called when the search button is clicked.
     * gets the search data, it passes it to the search function and sets it as the table data.
     *
     * @see BookUtils#searchBook(String, String, String)
     */
    @FXML
    protected void onSearchButtonClick() {
        String author = TF_author.getText();
        String title = TF_title.getText();
        String year = TF_year.getText();

        TWbooks.setItems(FXCollections.observableArrayList(BookUtils.searchBook(title, author, year)));
        TWbooks.refresh();
    }


    /**
     * This method is called when the login button is clicked.
     * It opens a new login page as a {@code Modal}.
     *
     * @param event the action event
     * @see loginController
     * @see ActionEvent
     */
    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        WindowsUtils.openDialogWindow(event, Windows.LOGIN, false);
    }

    /**
     * This method is called when the register button is clicked.
     * if the user is not logged in, opens a new register page as a {@code Modal} else opens the User Profile
     *
     * @param event the action event
     * @see ActionEvent
     */
    @FXML
    protected void onRegisterButtonClick(ActionEvent event) {
        if (LoadedData.logged_user == null) {
            WindowsUtils.openDialogWindow(event, Windows.REGISTER, false);
            return;
        }
    }

    /**
     * It adds a new tab to the Book {@code TabPane} with the current selected book.
     */
    private void addNewTab(Book book) {
        // initializes the resource
        FXMLLoader loader = new FXMLLoader(BookRecommender.class.getResource("bookTab.fxml"));
        Parent tabContent = null;
        try {
            // tries to load the resource
            tabContent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // tries to cut the lenght of the title to 10 and adds ".." at the end of it.
        String new_title = Utils.cutStringSize(LoadedData.current_looked_book.getTitle());
        // creates a new empty tab with the cut book title
        Tab tab = new Tab(new_title);
        // sets the method when the tab is closed
        tab.setOnClosed(((bookpageController) loader.getController())::onClose);
        // sets the tooltip of the tab to match the full book name
        tab.setTooltip(new Tooltip(LoadedData.current_looked_book.getTitle()));
        // sets the empty tab content to the loaded one
        tab.setContent(tabContent);

        tab.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                LoadedData.loaded_book_tabs.remove(book.getUuid());
            }
        });

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
        Blogin.setVisible(false);
        Blogin.setDisable(true);

        // disable the register button
        Bregister.setVisible(false);
        Bregister.setDisable(true);

        // enable library commands
        BaddToLibrary.setDisable(false);
        BdeleteLibrary.setDisable(false);
        BaddLibrary.setDisable(false);

        // change the register button
        Bregister.setText("User Profile");

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
    
    /**
     * The implementation of the UpdateUserEvent.
     * Refreshes the library table.
     */
    @Override
    public void onUpdate() {
        TWlibraries.refresh();
        TWlibraries.setItems(FXCollections.observableList(new ArrayList<>()));
        TWlibraries.setItems(FXCollections.observableList(LoadedData.logged_user_libraries));
    }
}
