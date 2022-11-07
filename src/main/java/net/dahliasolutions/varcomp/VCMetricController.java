package net.dahliasolutions.varcomp;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import net.dahliasolutions.varcomp.connectors.MetricConnector;
import net.dahliasolutions.varcomp.models.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VCMetricController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private Button btnMetricHome;
    @FXML
    private VBox paneParent;
    @FXML
    private ScrollPane spnMetricDetail;
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
    private VBox paneFormNewMetric;
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

    @FXML
    private VBox paneMetricDetail;
    @FXML
    private TextField txtDetailMetricLabel;
    @FXML
    private TextField txtDetailMetricYear;
    @FXML
    private TextField txtDetailMetricPeriod;
    @FXML
    private CheckBox chkDetailMetricLocked;
    @FXML
    private TableView<MetricDetail> tblDetailMetricPeriods;
    @FXML
    private TableColumn<Metric, BigDecimal> tbcDetailBudget;
    @FXML
    private TableColumn<Metric, BigDecimal> tbcDetailActual;
    @FXML
    private TableColumn<Metric, BigDecimal> tbcDetailEarnings;
    @FXML
    private Button btnDetailAddMetricDetail;
    @FXML
    private Button btnDetailRemoveMetricDetail;
    @FXML
    private Button btnMetricSave;
    @FXML
    private TextField txtDetailMetricEarning;
    @FXML
    private TextField txtDetailMetricFunding;
    @FXML
    private TextField txtDetailMetricPayout;
    @FXML
    private TextField txtDetailMetricShares;
    @FXML
    private TextField txtDetailMetricEPS;
    @FXML
    private Label lblDetailMetricYearWarn;
    @FXML
    private Label lblDetailMetricPeriodWarn;

    @FXML
    private TableView<EmployeeScore> tblDetailEmployeeScores;

    ObservableList<Metric> metricList;
    ObservableList<MetricDetail> metricDetailList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());

        btnMetricHome.setOnAction(event -> navMetricHome());
        bntNewMetric.setOnAction(event -> newMetric());
        txtFormNewYear.setOnKeyPressed(keyEvent -> updateFormNewMetricLabel(keyEvent));
        txtFormNewPeriod.setOnKeyPressed(keyEvent -> updateFormNewMetricLabel(keyEvent));
        btnFormNewMetric_cancel.setOnAction(event -> cancelNewMetric());

    /* Metric Table */
        tbcMetricLabel.setCellValueFactory(new PropertyValueFactory<Metric, String>("metric_label"));
        tbcMetricEarnings.setCellValueFactory(new PropertyValueFactory<Metric, BigDecimal>("metric_earnings"));
        tbcMetricPayout.setCellValueFactory(new PropertyValueFactory<Metric, BigDecimal>("metric_payout"));
        tbcMetricStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Metric, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Metric, String> param) {
                String cellValue = "Un-locked";
                if(param.getValue().getLocked()) {
                    cellValue = "Locked";
                }
                return new SimpleObjectProperty<String>(cellValue);
            }
        });
        tblMetrics.setItems(metricList);
        tblMetrics.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    navMetricDetail((Metric) tblMetrics.getSelectionModel().getSelectedItem());
                }
            }
        });
        btnFormNewMetric_save.setOnAction(event -> saveNewMetric());
        txtDetailMetricYear.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            focusState(newValue);
        });
        txtDetailMetricPeriod.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            focusState(newValue);
        });

    }

    private void focusState(Boolean b) {
      if(!b) {
          metricDetailUpdateLabel();
      }
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
        paneFormNewMetric.setLayoutX(tblMetrics.getLayoutX());
        paneFormNewMetric.setLayoutY(tblMetrics.getLayoutY());
        paneFormNewMetric.setPrefHeight(tblMetrics.getPrefHeight());
        paneFormNewMetric.setPrefWidth(tblMetrics.getPrefWidth());
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
        metric.setMetric_label(txtFormNewLabel.getText());
        metric.setMetric_year(Integer.parseInt(txtFormNewYear.getText()));
        metric.setMetric_period(Integer.parseInt(txtFormNewPeriod.getText()));
        metric.insertMetric();

        cancelNewMetric();
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        tblMetrics.getItems().removeAll();
        tblMetrics.setItems(metricList);
    }

    private void navMetricHome(){
        fillPaneMetricDetail(new Metric());
        showPaneMetricDetail(false);
    }
    private void navMetricDetail(Metric metric){
        fillPaneMetricDetail(metric);
        showPaneMetricDetail(true);
    }

    private void fillPaneMetricDetail(Metric metric) {
        txtDetailMetricLabel.setText(metric.getMetric_label());
        txtDetailMetricYear.setText(metric.getMetric_year().toString());
        txtDetailMetricPeriod.setText(metric.getMetric_period().toString());
        chkDetailMetricLocked.setSelected(metric.getLocked());
        txtDetailMetricEarning.setText(metric.getMetric_earnings().toString());
        txtDetailMetricFunding.setText(metric.getMetric_funding().toString());
        txtDetailMetricPayout.setText(metric.getMetric_payout().toString());
        txtDetailMetricShares.setText(metric.getMetric_shares().toString());
        txtDetailMetricEPS.setText(metric.getMetric_eps().toString());
    }

    private void showPaneMetricDetail(Boolean show) {
        lblDetailMetricYearWarn.setVisible(false);
        lblDetailMetricPeriodWarn.setVisible(false);

        btnMetricSave.setVisible(show);
        btnMetricHome.setVisible(show);

        paneMetricTable.setVisible(!show);
        paneMetricDetail.setVisible(show);
    }

     private void metricDetailUpdateLabel() {
         lblDetailMetricYearWarn.setVisible(false);
         lblDetailMetricPeriodWarn.setVisible(false);
        try {
             Integer nY = Integer.parseInt(txtDetailMetricYear.getText());
         } catch (Exception e) {
            lblDetailMetricYearWarn.setVisible(true);
            lblDetailMetricYearWarn.setTooltip(new Tooltip("Must be a number!"));
            return;
         }
         try {
             Integer nY = Integer.parseInt(txtDetailMetricPeriod.getText());
         } catch (Exception e) {
             lblDetailMetricPeriodWarn.setVisible(true);
             lblDetailMetricPeriodWarn.setTooltip(new Tooltip("Must be a number!"));
             return;
         }
         txtDetailMetricLabel.setText(txtDetailMetricYear.getText()+" - "+txtDetailMetricPeriod.getText());
     }
}
