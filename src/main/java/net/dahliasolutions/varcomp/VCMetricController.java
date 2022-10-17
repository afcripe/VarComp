package net.dahliasolutions.varcomp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import net.dahliasolutions.varcomp.models.Metric;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    @FXML
    private Pane paneFormNewMetric;
    @FXML
    private TextField txtFormNewLabel;
    @FXML
    private TextField txtFormNewYear;
    @FXML
    private TextField txtFormNewPeriod;
    @FXML
    private Label lblFormNewYearError;
    @FXML
    private Label lblFormNewPeriodError;
    @FXML
    private Button btnFormNewMetric_cancel;
    @FXML
    private Button btnFormNewMetric_save;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnMetricHome.setOnAction(event -> newMetric());
        bntNewMetric.setOnAction(event -> newMetric());
        txtFormNewYear.setOnKeyPressed(keyEvent -> updateFormNewMetricLabel(keyEvent));
        txtFormNewPeriod.setOnKeyPressed(keyEvent -> updateFormNewMetricLabel(keyEvent));
        btnFormNewMetric_cancel.setOnAction(event -> cancelNewMetric());
    }

    private void newMetric() {
        LocalDate localDate = LocalDate.now();
        Integer nY = localDate.getYear();
        Integer nP = (localDate.getMonthValue() / 4)+1;

        txtFormNewYear.setText(nY.toString());
        txtFormNewPeriod.setText(nP.toString());
        txtFormNewLabel.setText(nY.toString()+" - "+nP.toString());
        showFormNewMetric(true);
    }

    private void showFormNewMetric(Boolean show) {
        paneFormNewMetric.setVisible(show);
    }

    private void updateFormNewMetricLabel(KeyEvent keyEvent) {
        lblFormNewYearError.setVisible(false);
        lblFormNewPeriodError.setVisible(false);
        try {
            Integer nY = Integer.parseInt(txtFormNewYear.getText());
        } catch (Exception e) {
            lblFormNewYearError.setVisible(true);
        }
        try {
            Integer nY = Integer.parseInt(txtFormNewPeriod.getText());
        } catch (Exception e) {
            lblFormNewPeriodError.setVisible(true);
        }
        txtFormNewLabel.setText(txtFormNewYear.getText()+" - "+txtFormNewPeriod.getText());
    }

    private void cancelNewMetric() {
        txtFormNewYear.setText("");
        txtFormNewPeriod.setText("");
        txtFormNewLabel.setText("");
        showFormNewMetric(false);
    }
    private void saveNewMetric() {
        Metric metric = new Metric();
    }
}
