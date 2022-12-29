package net.dahliasolutions.varcomp;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
    private Button btnNavigation;
    @FXML
    private Button btnNavMetrics;
    @FXML
    private Button btnNavEmployees;
    @FXML
    private Button btnNavNotes;
    @FXML
    private Button btnNavSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the menubar
        if(System.getProperty("os.name").startsWith("Mac")) {
            // Mac OS, menubar set at login
            // disable this one
            viewMain.setTop(null);
        } else {
            menuLogout.setOnAction(actionEvent -> VarComp.changeScene("login-view.fxml", "Login", false));
            menuExit.setOnAction(actionEvent -> VarComp.closeApp());
            menuHelp.setOnAction(event -> showHelp());
            menuAbout.setOnAction(event -> showAbout());
        }

        btnNavigation.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.ARROW_RIGHT, "12px"));
        btnNavMetrics.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.BAR_CHART_ALT, "12px"));
        btnNavEmployees.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.USERS, "12px"));
        btnNavNotes.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL_SQUARE_ALT, "12px"));
        btnNavSettings.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.COGS, "12px"));

        btnNavigation.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavMetrics.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavEmployees.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavNotes.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavSettings.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));

        btnNavigation.setOnAction(event -> collapseNav());
        btnNavMetrics.setOnAction(event -> setCenterView("vcmetric-view"));
        btnNavEmployees.setOnAction(event -> setCenterView("vcemployee-view"));
        btnNavNotes.setOnAction(event -> setCenterView("vcnotes-view"));
        btnNavSettings.setOnAction(event -> setCenterView("settings-view"));
        btnNavSettings.setVisible(VarComp.getUser().getUser_type().equals("admin"));

        setCenterView("vcmetric-view");
    }

    @Override
    public void init() {
        System.out.println("Logged in to: "+VarComp.getCurrentCompany().getCompany_name());
        collapseNav();

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
    private void collapseNav() {
        if (vboxNavigation.getPrefWidth() == 100) {
            vboxNavigation.setPrefWidth(30);
            vboxNavigation.setMinWidth(30);
            btnNavigation.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.ARROW_RIGHT, "12px"));
            btnNavMetrics.setText("");
            btnNavEmployees.setText("");
            btnNavNotes.setText("");
            btnNavSettings.setText("");
        } else {
            vboxNavigation.setPrefWidth(100);
            vboxNavigation.setMinWidth(100);
            btnNavigation.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.ARROW_LEFT, "12px"));
            btnNavMetrics.setText("Metrics");
            btnNavEmployees.setText("Employees");
            btnNavNotes.setText("Notes");
            btnNavSettings.setText("Settings");
        }
        btnNavigation.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavMetrics.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavEmployees.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavNotes.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
        btnNavSettings.prefWidthProperty().bind(Bindings.subtract(vboxNavigation.widthProperty(), 4));
    }

    private void showAbout() {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(VarComp.class.getResource("aboutView.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 500, 400));
        stage.setTitle("About VarComp");
        stage.show();
    }

    private void showHelp() {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(VarComp.class.getResource("helpView.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle("Help");
        stage.show();
    }

}