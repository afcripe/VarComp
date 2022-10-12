package net.dahliasolutions.varcomp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.dahliasolutions.varcomp.models.Company;
import net.dahliasolutions.varcomp.models.User;

import java.io.IOException;

public class VarComp extends Application {

    private static final User loggedInUser = new User();
    private static final Company currentCompany = new Company();


    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        Image appIcon = new Image(VarComp.class.getResource("VcompIcon.png").openStream());
        stage.getIcons().add(appIcon);

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

    public static void main(String[] args) {
        DBSetup.initializeDB();
        launch();
    }
}