/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.utility;

import io.github.devdoctor.bookrecommender.BookRecommender;
import io.github.devdoctor.bookrecommender.Windows;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

/**
 * Utility class for managing windows in a JavaFX application.
 * <p>
 * This class provides static methods to change the current window and open dialog windows.
 * It simplifies the process of handling window transitions and dialogs, ensuring consistent styling and behavior.
 * </p>
 *
 * <p>
 * This class cannot be instantiated and is intended to be used in a static context.
 * </p>
 *
 * <p><b>Important:</b> The constructor is private to prevent instantiation.</p>
 *
 * @author DevDoctor
 * @since 1.0
 */
public class WindowsUtils {

    // Private constructor to prevent instantiation
    private WindowsUtils() {
        throw new UnsupportedOperationException("WindowsUtils cannot be instantiated");
    }

    /**
     * Changes the current window to a new scene.
     * <p>
     * Loads a new scene from the specified FXML resource and applies the default stylesheet.
     * The title of the stage is updated, and the new scene is set.
     * </p>
     *
     * @param event  the action event that triggered the window change
     * @param window the {@link Windows} object containing information about the new window
     * @throws RuntimeException if there is an issue loading the new window's FXML resource
     * @see Windows
     */
    public static void changeCurrentWindow(ActionEvent event, Windows window) {
        Scene new_scene;
        try {
            String css = BookRecommender.class.getResource(BookRecommender.DEFAULT_CSS).toExternalForm();
            // loads the new data and create the new scene
            new_scene = new Scene(new FXMLLoader(BookRecommender.class.getResource(window.resource + ".fxml")).load(), window.width, window.height);
            new_scene.getStylesheets().add(css);
            // gets the window stage from the event
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            // sets the title
            stage.setTitle(window.title);
            // sets the new scene
            stage.setScene(new_scene);
            // shows the new stage
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Opens a dialog window as a child of the current window with default resizability settings.
     * <p>
     * This method makes use of the {@link #openDialogWindow(Window, Windows, Boolean)} method with
     * the default resizable setting set to {@code true}.
     * </p>
     *
     * @param event  the event that triggered the dialog window opening
     * @param window the {@link Windows} object containing information about the dialog window
     * @see #openDialogWindow(Window, Windows, Boolean)
     */
    public static void openDialogWindow(Event event, Windows window) {
        Window n = ((Node) event.getSource()).getScene().getWindow();
        openDialogWindow(n, window, true);
    }

    /**
     * Opens a dialog window as a child of the current window with specified resizability settings.
     * <p>
     * This method allows specifying whether the dialog window should be resizable.
     * It delegates to the {@link #openDialogWindow(Window, Windows, Boolean)} method to perform the actual work.
     * </p>
     *
     * @param event       the event that triggered the dialog window opening
     * @param window      the {@link Windows} object containing information about the dialog window
     * @param isResizable whether the dialog window should be resizable
     * @see #openDialogWindow(Window, Windows, Boolean)
     */
    public static void openDialogWindow(Event event, Windows window, Boolean isResizable) {
        Window n = ((Node) event.getSource()).getScene().getWindow();
        openDialogWindow(n, window, isResizable);
    }

    /**
     * Opens a dialog window as a child of the specified parent window.
     * <p>
     * The dialog window will block interaction with the parent window until it is closed.
     * It can be set to be resizable or not based on the {@code isResizable} parameter.
     * </p>
     *
     * @param parent      the parent window that owns the dialog
     * @param window      the {@link Windows} object containing information about the dialog window
     * @param isResizable whether the dialog window should be resizable
     * @throws RuntimeException if there is an issue loading the dialog window's FXML resource
     * @see Windows
     */
    private static void openDialogWindow(Window parent, Windows window, Boolean isResizable) {
        // loads the new window resource
        FXMLLoader fxmlLoader = new FXMLLoader(BookRecommender.class.getResource(window.resource + ".fxml"));
        // new window stage
        Stage dialog = new Stage();
        // sets if the dialog is resizable
        dialog.setResizable(isResizable);
        // set the window icon
        dialog.getIcons().add(new Image(Objects.requireNonNull(BookRecommender.class.getResourceAsStream(BookRecommender.ICON))));
        // tries to create the new scene else throws a new RuntimeException
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), window.width, window.height);
            String css = BookRecommender.class.getResource(BookRecommender.DEFAULT_CSS).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // set the dialog scene
        dialog.setScene(scene);
        // changes the dialog title
        dialog.setTitle(window.title);
        // sets the type of modality of the window
        dialog.initModality(Modality.WINDOW_MODAL);
        // the parent of the window
        dialog.initOwner(parent);
        // tells the parent to wait until it is closed
        dialog.showAndWait();
    }
}
