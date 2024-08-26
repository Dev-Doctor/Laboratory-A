package io.github.devdoctor.deltabooks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class of the program. It starts the {@code GUI}
 * @see Application
 * @author Davide Restelli
 */
public class DeltaBooks extends Application {
    public static final String PROGRAM_NAME = "Book Recommender";
    private static final Windows START_WINDOW = Windows.HOME;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DeltaBooks.class.getResource(START_WINDOW.resource + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), START_WINDOW.width, START_WINDOW.height);
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
        FileUtils.loadConfig();
        FileUtils.loadUsers();
        FileUtils.loadBooks();
    }
}