package net.dahliasolutions.varcomp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import net.dahliasolutions.varcomp.models.Metric;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class VCMetricController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private Button btnMetricHome;
    @FXML
    private Pane paneMetricTable;
    @FXML
    private Button bntNewMetric;
    @FXML
    private TableView<Metric> tblMetrics;
    @FXML
    private TableColumn<Metric, String> tbcMetricLabel;
    @FXML
    private TableColumn<Metric, BigDecimal> tbcMetricEarnings;
    @FXML
    private TableColumn<Metric, BigDecimal> tbcMetricPayout;
    @FXML
    private TableColumn<Metric, String> tbcMetricStatus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
