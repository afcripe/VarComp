package net.dahliasolutions.varcomp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.dahliasolutions.varcomp.models.Company;
import net.dahliasolutions.varcomp.models.User;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class VarComp extends Application {

    private static final User loggedInUser = new User();
    private static final Company currentCompany = new Company();


    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setMinWidth(680);

        Image appIcon = new Image(Objects.requireNonNull(VarComp.class.getResource("VcompIcon.png")).openStream());
        stage.getIcons().add(appIcon);

        //Set icon on the taskbar/dock
        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();

            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage(getClass().getResource("VcompIcon.png"));
                taskbar.setIconImage(dockIcon);
            }

        }

        double recWidth = DBUtils.getStageSize("app_width");
        double recHeight = DBUtils.getStageSize("app_height");

        // GUI
        FXMLLoader fxmlLoader = new FXMLLoader(VarComp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), recWidth, recHeight);
        primaryStage.setTitle("VarComp Login");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> closeApp());
    }

    @Override
    public void init() {
        // Configure Data
        DBSetup.initializeDB();
    }

    public static void closeApp() {
        Boolean tblUpdateComplete = DBUtils.updateDBTable(primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        if (tblUpdateComplete) {
            primaryStage.close();
        } else {
            primaryStage.close();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setUser(User user) {
        loggedInUser.setUser(user);
    }

    public static User getUser() {
        return loggedInUser;
    }
    public static void setCurrentCompany(Company company) { currentCompany.setCompany(company); }
    public static Company getCurrentCompany() { return currentCompany; }

    public static void changeScene(String fxmlFile, String title, Boolean newWindow) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;
        double userWidth;
        double userHeight;

        try {
            loader = new FXMLLoader(VarComp.class.getResource(fxmlFile));
            root = loader.load();
            ((ViewController)loader.getController()).init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!newWindow) { stage = VarComp.getPrimaryStage(); }

        userWidth = stage.getScene().getWidth();
        userHeight = stage.getScene().getHeight();
        stage.setScene(new Scene(root, userWidth, userHeight));
        stage.setTitle(title);
        stage.show();
    }

    public static void main(String[] args) {
        DBSetup.initializeDB();
        launch();
    }

}