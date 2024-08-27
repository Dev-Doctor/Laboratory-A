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
 * Windows Utils, a set of tools to manage windows more easily.
 *
 * @author Davide Restelli
 */
public class WindowsUtils {


    /**
     * Change current window. Throws a new {@link RuntimeException}
     * if the resource is not found.
     *
     * @param event  the action event
     * @param window the window to load
     * @see Windows
     */
    public static void changeCurrentWindow(ActionEvent event, Windows window) {
        Scene new_scene;
        try {
            new_scene = new Scene(new FXMLLoader(DeltaBooks.class.getResource(window.resource + ".fxml")).load(), window.width, window.height);
            Stage stage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
            stage.setTitle(window.title);
            stage.setScene(new_scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens a dialog child of the main window. The user cannot interact
     * with the parent window until the dialog window is closed.
     * The code will stop in the parent window until the dialog is closed.
     * Throws a new {@link RuntimeException} if the resource is not found.
     *
     * @param event  the action event
     * @param window the window to load
     * @see Windows
     */
    public static void openDialogWindow(Event event, Windows window) {
        Window n = ( (Node) event.getSource()).getScene().getWindow();
        openDialogWindow(n, window);
    }

    public static void openDialogWindow(Window parent, Windows window) {
        FXMLLoader fxmlLoader = new FXMLLoader(DeltaBooks.class.getResource(window.resource + ".fxml"));
        Stage dialog = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), window.width, window.height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setScene(scene);
        dialog.setTitle(window.title);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(parent);
        dialog.showAndWait();
    }
}
