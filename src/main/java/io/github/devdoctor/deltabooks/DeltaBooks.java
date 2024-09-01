package io.github.devdoctor.deltabooks;
import io.github.devdoctor.deltabooks.events.LoginEvent;
import io.github.devdoctor.deltabooks.events.UpdateUserEvent;
import io.github.devdoctor.deltabooks.utility.FileUtils;
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
 * @author Davide Restelli
 */
public class DeltaBooks extends Application {
    public static final String PROGRAM_NAME = "Book Recommender";
    public static final String ICON = "images/icon.png";
    public static final String DEFAULT_CSS = "style/master.css";
    private static final Windows START_WINDOW = Windows.HOME;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DeltaBooks.class.getResource(START_WINDOW.resource + ".fxml"));
        String css = this.getClass().getResource(DEFAULT_CSS).toExternalForm();
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(ICON))));
        Scene scene = new Scene(fxmlLoader.load(), START_WINDOW.width, START_WINDOW.height);
        scene.getStylesheets().add(css);
        stage.setTitle(START_WINDOW.title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        inizialization();
        launch();
    }

    private static void inizialization() {
        LoadedData.loginEvent = new LoginEvent();
        LoadedData.userEvent = new UpdateUserEvent();
        FileUtils.loadConfig();
        FileUtils.loadUsers();
        FileUtils.loadBooks();
    }
}