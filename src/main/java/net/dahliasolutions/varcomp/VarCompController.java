package net.dahliasolutions.varcomp;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VarCompController extends ViewController implements Initializable {

    @FXML
    private BorderPane viewMain;
    @FXML
    private MenuItem menuLogout;
    @FXML
    private MenuItem menuExit;
    @FXML
    private MenuItem menuHelp;
    @FXML
    private MenuItem menuAbout;
    @FXML
    private VBox vboxNavigation;
    @FXML
    private Button btnNavMetrics;
    @FXML
    private Button btnNavEmployees;
    @FXML
    private Button btnNavSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuLogout.setOnAction(actionEvent -> VarComp.changeScene("login-view.fxml", "Login", false));
        menuExit.setOnAction(actionEvent -> VarComp.closeApp());
        menuHelp.setOnAction(event -> showHelp());
        menuAbout.setOnAction(event -> showAbout());

        btnNavMetrics.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavEmployees.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavSettings.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));

        btnNavMetrics.setOnAction(event -> setCenterView("vcmetric-view"));
        btnNavEmployees.setOnAction(event -> setCenterView("vcemployee-view"));
        btnNavSettings.setOnAction(event -> setCenterView("settings-view"));
        btnNavSettings.setVisible(VarComp.getUser().getUser_type().equals("admin"));

        setCenterView("vcmetric-view");
    }

    @Override
    public void init() {
        System.out.println("Logged in to: "+VarComp.getCurrentCompany().getCompany_name());
    }

    public void setCenterView(String fxmlFile) {
        try {
            FXMLLoader centerLoader = new FXMLLoader(VarComp.class.getResource(fxmlFile + ".fxml"));
            Pane viewPane = centerLoader.load();
            viewMain.setCenter(viewPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void showAbout() {
        //ToDo
    }

    private void showHelp() {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(VarComp.class.getResource("home.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 600, 700));
        stage.setTitle("Help");
        stage.show();
    }

}