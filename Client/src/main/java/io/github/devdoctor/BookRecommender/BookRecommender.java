/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender;

import io.github.devdoctor.BookRecommender.events.LoginEvent;
import io.github.devdoctor.BookRecommender.events.UpdateUserEvent;
import io.github.devdoctor.BookRecommender.utility.BookUtils;
import io.github.devdoctor.BookRecommender.utility.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The main class of the program. It starts the {@code GUI}
 * @see Application
 * @author DevDoctor
 * @since 1.0
 */
public class BookRecommender extends Application {
    public static final String PROGRAM_VERSION = "1.0";
    public static final String PROGRAM_NAME = "Book Recommender";
    public static final String ICON = "images/icon.png";
    public static final String DEFAULT_CSS = "style/master.css";
    private static final Windows START_WINDOW = Windows.HOME;

    @Override
    public void start(Stage stage) throws IOException {
        // load the main window layout
        FXMLLoader fxmlLoader = new FXMLLoader(BookRecommender.class.getResource(START_WINDOW.resource + ".fxml"));
        // load the css for the window
        String css = this.getClass().getResource(DEFAULT_CSS).toExternalForm();
        // set the window icon
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(ICON))));
        // start the scene
        Scene scene = new Scene(fxmlLoader.load(), START_WINDOW.width, START_WINDOW.height);
        // add the style to the scene
        scene.getStylesheets().add(css);
        // set the title of the window
        stage.setTitle(START_WINDOW.title);
        // set the current scene to the newly created
        stage.setScene(scene);
        // show the scene
        stage.show();
    }

    public static void main(String[] args) {
        initialization();
        launch();
    }

    /**
     * Sets the default state of the program
     */
    private static void initialization() {
        LoadedData.loginEvent = new LoginEvent();
        LoadedData.userEvent = new UpdateUserEvent();
        FileUtils.loadConfig();
//        UserUtils.loadUsers();
        BookUtils.loadBooks();
    }
}