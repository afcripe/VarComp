package net.dahliasolutions.varcomp;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    private VBox vboxNavigation;
    @FXML
    private Button btnNavHome;
    @FXML
    private Button btnNavMetrics;
    @FXML
    private Button btnNavEmployees;
    @FXML
    private Button btnNavSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuLogout.setOnAction(actionEvent -> NavigationUtils.changeScene(actionEvent, "login-view.fxml", "Please, Login", false));
        menuExit.setOnAction(actionEvent -> VarComp.closeApp());

//        btnNavHome.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavMetrics.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavEmployees.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavSettings.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));

//        btnNavHome.setOnAction(event -> setCenterView("vchome-view"));
        btnNavMetrics.setOnAction(event -> setCenterView("vcmetric-view"));
        btnNavEmployees.setOnAction(event -> setCenterView("vcemployee-view"));
        btnNavSettings.setOnAction(event -> setCenterView("settings-view"));
        if (VarComp.getUser().getUser_type().equals("admin")) {
            btnNavSettings.setVisible(true);
        } else {
            btnNavSettings.setVisible(false);
        }


        setCenterView("vcmetric-view");
    }

    @Override
    public void init() {
//        System.out.println(VarComp.getCurrentCompany().getCompany_name());
    }

    public void setCenterView(String fxmlFile) {
        try {
            FXMLLoader centerLoader = new FXMLLoader(NavigationUtils.class.getResource(fxmlFile + ".fxml"));
            Pane viewPane = centerLoader.load();
            viewMain.setCenter(viewPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}