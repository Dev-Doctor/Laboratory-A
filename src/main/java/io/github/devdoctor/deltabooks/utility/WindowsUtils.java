package io.github.devdoctor.deltabooks.utility;

import io.github.devdoctor.deltabooks.DeltaBooks;
import io.github.devdoctor.deltabooks.Windows;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * A set of tools to manage windows more easily.
 *
 * @author Davide Restelli
 * @since 1.0
 */
public class WindowsUtils {


    /**
     * Changes the current window.
     * if the resource is not found.
     *
     * @param event  the action event
     * @param window the window to load
     * @throws RuntimeException if it was unable to load the new window
     * @see Windows
     */
    public static void changeCurrentWindow(ActionEvent event, Windows window) {
        Scene new_scene;
        try {
            // loads the new data and create the new scene
            new_scene = new Scene(new FXMLLoader(DeltaBooks.class.getResource(window.resource + ".fxml")).load(), window.width, window.height);
            // gets the window stage from the event
            Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
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
     * Sets the resizability to false
     *
     * @param event  the event of the current window
     * @param window the window to load
     * @see #openDialogWindow(Window, Windows, Boolean)
     */
    public static void openDialogWindow(Event event, Windows window) {
        Window n = ( (Node) event.getSource()).getScene().getWindow();
        openDialogWindow(n, window, true);
    }

    /**
     *
     * @param event  the event of the current window
     * @param window the window to load
     * @param isResizable the ability to resize a window
     * @see #openDialogWindow(Window, Windows, Boolean)
     */
    public static void openDialogWindow(Event event, Windows window, Boolean isResizable) {
        Window n = ( (Node) event.getSource()).getScene().getWindow();
        openDialogWindow(n, window, isResizable);
    }

    /**
     * Opens a dialog child of the main window. The user cannot interact
     * with the parent window until the dialog window is closed.
     * Sets the ability to resize the window.
     * The code will stop in the parent window until the dialog is closed.
     *
     * @param parent  the action window
     * @param window the window to load
     * @param isResizable the ability to resize a window
     * @throws RuntimeException if the resource is not found.
     * @see Windows
     */
    private static void openDialogWindow(Window parent, Windows window, Boolean isResizable) {
        // loads the new window resource
        FXMLLoader fxmlLoader = new FXMLLoader(DeltaBooks.class.getResource(window.resource + ".fxml"));
        // new window stage
        Stage dialog = new Stage();
        // sets if the dialog is resizable
        dialog.setResizable(isResizable);
        // tries to create the new scene else throws a new RuntimeException
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), window.width, window.height);
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
