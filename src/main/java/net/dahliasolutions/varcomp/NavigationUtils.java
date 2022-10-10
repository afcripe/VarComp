package net.dahliasolutions.varcomp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.dahliasolutions.varcomp.models.User;

import java.io.IOException;

public class NavigationUtils {


    public static void changeScene(ActionEvent event, String fxmlFile, String title, Boolean newWindow) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = null;
        double userWidth = 600;
        double userHeight = 400;

        try {
            loader = new FXMLLoader(NavigationUtils.class.getResource(fxmlFile));
            root = loader.load();
            ((ViewController)loader.getController()).init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.addEventFilter(CustomEvent.ANY, ((ViewController)loader.getController())::handleObjectEvent);

        if (!newWindow) { stage = VarComp.getPrimaryStage(); }

        userWidth = stage.getScene().getWidth();
        userHeight = stage.getScene().getHeight();
        stage.setScene(new Scene(root, userWidth, userHeight));
        stage.setTitle(title);
        stage.show();
    }

    public static void loginUser(ActionEvent event, String fxmlFile, String title, Boolean newWindow, User user) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader = null;
        double userWidth = 600;
        double userHeight = 400;

        try {
            loader = new FXMLLoader(NavigationUtils.class.getResource(fxmlFile));
            root = loader.load();
            ((ViewController)loader.getController()).setUser(user);
            ((ViewController)loader.getController()).init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.addEventFilter(CustomEvent.ANY, ((ViewController)loader.getController())::handleObjectEvent);

        if (!newWindow) { stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); }

        userWidth = ((Node) event.getSource()).getScene().getWidth();
        userHeight = ((Node) event.getSource()).getScene().getHeight();
        stage.setScene(new Scene(root, userWidth, userHeight));
        stage.setTitle(title);
        stage.show();
    }

}
