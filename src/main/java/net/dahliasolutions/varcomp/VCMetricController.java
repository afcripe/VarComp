package net.dahliasolutions.varcomp;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.dahliasolutions.varcomp.connectors.*;
import net.dahliasolutions.varcomp.models.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class VCMetricController implements Initializable {

    @FXML
    private Button btnMetricHome;
    @FXML
    private CheckBox chkPrintPreview;
    @FXML
    private Pane paneMetricTable;
    @FXML
    private Button bntNewMetric;
    @FXML
    private TableView<Metric> tblMetrics;
    @FXML
    private TableColumn<Metric, String> tbcMetricLabel;
    @FXML
    private TableColumn<Metric, String> tbcMetricEarnings;
    @FXML
    private TableColumn<Metric, String> tbcMetricPayout;
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
    private HBox boxMetricDetailStatus;
    @FXML
    private TextField txtDetailMetricLabel;
    @FXML
    private TextField txtDetailMetricYear;
    @FXML
    private TextField txtDetailMetricPeriod;
    @FXML
    private CheckBox chkDetailMetricLocked;
    @FXML
    private Button btnNotes;
    @FXML
    private HBox boxMetricDetailTable;
    @FXML
    private TableView<MetricDetail> tblDetailMetricPeriods;
    @FXML
    private TableColumn<MetricDetail, Integer> tbcDetailPeriod;
    @FXML
    private TableColumn<MetricDetail, String> tbcDetailBudget;
    @FXML
    private TableColumn<MetricDetail, String> tbcDetailActual;
    @FXML
    private TableColumn<MetricDetail, String> tbcDetailEarnings;
    @FXML
    private Button btnDetailAddMetricDetail;
    @FXML
    private Button btnDetailRemoveMetricDetail;
    @FXML
    private Button btnMetricSave;
    @FXML
    private Button btnMetricPrint;
    @FXML
    private Label lblPrintJobStatus;
    @FXML
    private Button btnSharesRefresh;
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
    private VBox formDetailMetricPeriod;
    @FXML
    private TextField txtFormDPPeriod;
    @FXML
    private TextField txtFormDPBudget;
    @FXML
    private TextField txtFormDPActual;
    @FXML
    private TextField txtFormDPEarnings;
    @FXML
    private TextField txtFormDPDetailID;
    @FXML
    private Button btnFormDP_cancel;
    @FXML
    private Button btnFormDP_save;

    @FXML
    private VBox boxDetailCompanyKPI;
    @FXML
    private Button btnAddCompanyKPI;
    @FXML
    private Button btnRemoveCompanyKPI;
    @FXML
    private Button btnAddEmployee;
    @FXML
    private Button btnRemoveEmployee;

    @FXML
    private TableView<CompanyKPI> tblDetailCompanyKPI;
    @FXML
    private TableColumn<CompanyKPI, String> tbcCompanyKPICode;
    @FXML
    private TableColumn<CompanyKPI, String> tbcCompanyKPIGrade;
    @FXML
    private TableColumn<CompanyKPI, String> tbcCompanyKPIScore;

    @FXML
    private VBox paneDetailEditorCompanyKPI;
    @FXML
    private Label lblCompKPIEditorCode;
    @FXML
    private Label lblCompKPIEditorClass;
    @FXML
    private Label lblCompKPIEditorWeight;
    @FXML
    private Label lblCompKPIEditorF1Name;
    @FXML
    private TextField txtCompKPIEditorF1Data;
    @FXML
    private Label lblCompKPIEditorF2Name;
    @FXML
    private TextField txtCompKPIEditorF2Data;
    @FXML
    private Label lblCompKPIEditorF3Name;
    @FXML
    private TextField txtCompKPIEditorF3Data;
    @FXML
    private Label lblCompKPIEditorF4Name;
    @FXML
    private TextField txtCompKPIEditorF4Data;
    @FXML
    private ComboBox<String> cmbCompKPIEditorCalc;
    @FXML
    private VBox boxEditorKPIF1;
    @FXML
    private VBox boxEditorKPIF2;
    @FXML
    private VBox boxEditorKPIF3;
    @FXML
    private VBox boxEditorKPIF4;
    @FXML
    private TextField txtCompKPIEditorGrade;
    @FXML
    private TextField txtCompKPIEditorScore;
    @FXML
    private Button btnCompKPIEditorCancel;
    @FXML
    private Button btnCompKPIEditorSave;
    @FXML
    private Label lblCompKPIEditorID;

    @FXML
    private TableView<EmployeeScore> tblDetailEmployeeScores;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeName;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeShares;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeGrade;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeBonus;

    @FXML
    private ScrollPane paneEmployeeKPI;
    @FXML
    private Button btnEmployeeKPIBack;
    @FXML
    private Label txtEditorEmployeeID;
    @FXML
    private Label txtEditorEmployeeFirstName;
    @FXML
    private Label txtEditorEmployeeLastName;
    @FXML
    private Label txtEditorEmployeePosition;
    @FXML
    private Label txtEditorEmployeeShares;
    @FXML
    private Label txtEditorEmployeeGrade;
    @FXML
    private Label txtEditorEmployeeSBonus;
    @FXML
    private TableView<EmployeeKPI> tblEditorKPIs;
    @FXML
    private TableColumn<EmployeeKPI, String> tbcEditorKPICode;
    @FXML
    private TableColumn<EmployeeKPI, BigDecimal> tbcEditorKPIWeight;
    @FXML
    private TableColumn<EmployeeKPI, String> tbcEditorKPIClass;
    @FXML
    private TableColumn<EmployeeKPI, String> tbcEditorKPIGrade;
    @FXML
    private TableColumn<EmployeeKPI, String> tbcEditorKPIScore;


    @FXML
    private Label lblKPIEditorCode;
    @FXML
    private Label lblKPIEditorClass;
    @FXML
    private Label lblKPIEditorWeight;
    @FXML
    private Label lblKPIEditorF1Name;
    @FXML
    private TextField txtKPIEditorF1Data;
    @FXML
    private Label lblKPIEditorF2Name;
    @FXML
    private TextField txtKPIEditorF2Data;
    @FXML
    private Label lblKPIEditorF3Name;
    @FXML
    private TextField txtKPIEditorF3Data;
    @FXML
    private Label lblKPIEditorF4Name;
    @FXML
    private VBox boxCompKPIF1;
    @FXML
    private VBox boxCompKPIF2;
    @FXML
    private VBox boxCompKPIF3;
    @FXML
    private VBox boxCompKPIF4;
    @FXML
    private TextField txtKPIEditorF4Data;
    @FXML
    private ComboBox<String> cmbKPIEditorCalc;
    @FXML
    private TextField txtKPIEditorGrade;
    @FXML
    private TextField txtKPIEditorScore;
    @FXML
    private Button btnKPIEditorCancel;
    @FXML
    private Button btnKPIEditorSave;
    @FXML
    private Label lblKPIEditorID;
    @FXML
    private Label lblKPIEditorCompany;

    @FXML
    private VBox paneFormEditorKPI;


    ObservableList<Metric> metricList;
    ObservableList<MetricDetail> metricDetailList;
    ObservableList<CompanyKPI> companyKPIObservableList;
    ObservableList<EmployeeScore> employeeScoresObservableList;
    ObservableList<EmployeeKPI> employeeKPIObservableList;
    ObservableList<CalculationOptions> calcList;
    private final Metric metricDetail = new Metric();
    private final EmployeeScore employeeScore = new EmployeeScore();
    private final Employee employeeEdit = new Employee();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());

        btnMetricHome.setOnAction(event -> navMetric(""));
        bntNewMetric.setOnAction(event -> newMetric());
        txtFormNewYear.setOnKeyPressed(this::updateFormNewMetricLabel);
        txtFormNewPeriod.setOnKeyPressed(this::updateFormNewMetricLabel);
        btnFormNewMetric_cancel.setOnAction(event -> cancelNewMetric());
        calcList = FXCollections.observableArrayList(CalculationOptionsConnection.getCalculationOptions());

    /* Metric Table */
        tbcMetricLabel.setCellValueFactory(new PropertyValueFactory<>("metric_label"));
        tbcMetricLabel.setPrefWidth((tblMetrics.getPrefWidth()-104)/3);
        tbcMetricEarnings.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getCurrencyInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getMetric_earnings()));
        });
        tbcMetricEarnings.setPrefWidth((tblMetrics.getPrefWidth()-104)/3);
        tbcMetricPayout.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getCurrencyInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getMetric_payout()));
        });
        tbcMetricPayout.setPrefWidth((tblMetrics.getPrefWidth()-104)/3);
        tbcMetricStatus.setCellValueFactory(param -> {
            String cellValue = "Open";
            if(param.getValue().getLocked()) {
                cellValue = "Closed";
            }
            return new SimpleObjectProperty<>(cellValue);
        });
        tbcMetricStatus.setPrefWidth(100);
        tbcMetricStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item,  empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    if (item.equals("Closed")) {
                        setTextFill(Color.DARKRED); // or use setStyle(String)
                    } else {
                        setTextFill(Color.DARKGREEN); // or use setStyle(String)
                    }
                }
            }
        });
        tblMetrics.setItems(metricList);
        tblMetrics.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                navMetricDetail(tblMetrics.getSelectionModel().getSelectedItem());
            }
        });
        btnFormNewMetric_save.setOnAction(event -> saveNewMetric());
        txtDetailMetricYear.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusState(newValue));
        txtDetailMetricPeriod.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusState(newValue));

    /* Metric Detail */
        btnMetricSave.setOnAction(event -> saveMetricDetail());
        btnMetricPrint.setOnAction(event -> printMetricDetail());
        btnDetailAddMetricDetail.setOnAction(event -> addMetricPeriodDetail());
        btnDetailRemoveMetricDetail.setOnAction(event -> removeMetricPeriodDetail());
        btnSharesRefresh.setOnAction(event -> updateShares());
        chkDetailMetricLocked.setOnAction(event -> {
            CheckBox chk = (CheckBox) event.getTarget();
            setMetricLock(chk.isSelected());
        });

        btnNotes.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL_SQUARE_ALT, "12px"));
        btnNotes.setOnAction(event -> navNotes());

        tbcDetailPeriod.setCellValueFactory(new PropertyValueFactory<>("detail_period"));
        tbcDetailPeriod.setPrefWidth(50);
        tbcDetailBudget.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getCurrencyInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getDetail_budget()));
        });
        tbcDetailBudget.setPrefWidth((tblDetailMetricPeriods.getPrefWidth()-54)/3);
        tbcDetailActual.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getCurrencyInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getDetail_actual()));
        });
        tbcDetailActual.setPrefWidth((tblDetailMetricPeriods.getPrefWidth()-54)/3);
        tbcDetailEarnings.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getCurrencyInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getDetail_earnings()));
        });
        tbcDetailEarnings.setPrefWidth((tblDetailMetricPeriods.getPrefWidth()-54)/3);
        tblDetailMetricPeriods.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                if(!metricDetail.getLocked()) {
                    showFormDP(tblDetailMetricPeriods.getSelectionModel().getSelectedItem());
                }
            }
        });

    /* Period Detail Form */
        txtFormDPBudget.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> dpFocusState(newValue));
        txtFormDPActual.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> dpFocusState(newValue));
        btnFormDP_cancel.setOnAction(event -> showFormDP());
        btnFormDP_save.setOnAction(event -> saveMetricPeriodDetail());

    /* Company KPIs */
        btnAddCompanyKPI.setOnAction(event -> addMissingCompanyKPIs());
        btnRemoveCompanyKPI.setOnAction(event -> removeCompanyKPI());
        tbcCompanyKPICode.setCellValueFactory(new PropertyValueFactory<>("kpi_code"));
        tbcCompanyKPICode.setPrefWidth(100);
        tbcCompanyKPIGrade.setPrefWidth((tblDetailCompanyKPI.getPrefWidth()-104)/3);
        tbcCompanyKPIScore.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getNumberInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getKpi_score()));
        });
        tbcCompanyKPIGrade.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getPercentInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getKpi_grade()));
        });
        tbcCompanyKPIScore.setPrefWidth((tblDetailCompanyKPI.getPrefWidth()-104)/3);
        tblDetailCompanyKPI.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                if(!metricDetail.getLocked()) {
                    showCompKIPEditor(true);
                }
            }
        });
        btnCompKPIEditorCancel.setOnAction(event -> showCompKIPEditor(false));
        btnCompKPIEditorSave.setOnAction(event -> {
            try {
                saveCompanyKPIEditor();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        txtCompKPIEditorF1Data.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusStateKPICompany(newValue));
        txtCompKPIEditorF2Data.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusStateKPICompany(newValue));
        txtCompKPIEditorF3Data.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusStateKPICompany(newValue));
        txtCompKPIEditorF4Data.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusStateKPICompany(newValue));

        /* Employee Scores */
        btnAddEmployee.setOnAction(event -> addMissingEmployees());
        btnRemoveEmployee.setOnAction(event -> removeMissingEmployees());
        tbcEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employee_name"));
        tbcEmployeeName.setPrefWidth((tblDetailEmployeeScores.getPrefWidth()-4)/4);
        tbcEmployeeShares.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getNumberInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getShares()));
        });
        tbcEmployeeShares.setPrefWidth((tblDetailEmployeeScores.getPrefWidth()-4)/4);
        tbcEmployeeGrade.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getPercentInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getGrade()));
        });
        tbcEmployeeGrade.setPrefWidth((tblDetailEmployeeScores.getPrefWidth()-4)/4);
        tbcEmployeeBonus.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getCurrencyInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getBonus()));
        });
        tbcEmployeeBonus.setPrefWidth((tblDetailEmployeeScores.getPrefWidth()-4)/4);
        tblDetailEmployeeScores.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                openEmployeeKPIEditor(tblDetailEmployeeScores.getSelectionModel().getSelectedItem());
            }
        });

    /* Employee KPI Editor */
        btnEmployeeKPIBack.setOnAction(event -> closeEmployeeKPIEdit());
        tbcEditorKPICode.setCellValueFactory(new PropertyValueFactory<>("kpi_code"));
        tbcEditorKPICode.setPrefWidth(85);
        tbcEditorKPIWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        tbcEditorKPIWeight.setPrefWidth((tblEditorKPIs.getPrefWidth()-90)/5);
        tbcEditorKPIClass.setCellValueFactory(param -> new SimpleObjectProperty<>(KPIClassConnector.getKPIClass(param.getValue().getKpi_class()).getName()));
        tbcEditorKPIClass.setPrefWidth((tblEditorKPIs.getPrefWidth()-90)/5);
        tbcEditorKPIScore.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getNumberInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getKpi_score()));
        });
        tbcEditorKPIScore.setPrefWidth((tblEditorKPIs.getPrefWidth()-90)/5);
        tbcEditorKPIGrade.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getPercentInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getKpi_grade()));
        });
        tbcEditorKPIGrade.setPrefWidth((tblEditorKPIs.getPrefWidth()-90)/5);
        tblEditorKPIs.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                if(!metricDetail.getLocked()) {
                    showFormEditorKPI(true);
                }
            }
        });

        btnKPIEditorCancel.setOnAction(event -> showFormEditorKPI(false));
        btnKPIEditorSave.setOnAction(event -> {
            try {
                saveKPIEditor();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        txtKPIEditorF1Data.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusStateKPI(newValue));
        txtKPIEditorF2Data.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusStateKPI(newValue));
        txtKPIEditorF3Data.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusStateKPI(newValue));
        txtKPIEditorF4Data.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusStateKPI(newValue));


        navMetric("");
    }

    private void focusState(Boolean b) {
      if(!b) {
          metricDetailUpdateLabel();
      }
    }
    private void dpFocusState(Boolean b) {
        if(!b) {
            dpFormCalcEarnings();
        }
    }
    private void focusStateKPI(Boolean b) {
        if(!b) {
            gradeKPI();
        }
    }
    private void focusStateKPICompany(Boolean b) {
        if(!b) {
            gradeKPICompany();
        }
    }

    private void newMetric() {
        LocalDate localDate = LocalDate.now();
        int nY = localDate.getYear();
        int nP = (localDate.getMonthValue() / 4)+1;

        txtFormNewYear.setText(Integer.toString(nY));
        txtFormNewPeriod.setText(Integer.toString(nP));
        txtFormNewLabel.setText(nY +" - "+ nP);

        paneFormNewMetric.setLayoutX(tblMetrics.getLayoutX());
        paneFormNewMetric.setLayoutY(tblMetrics.getLayoutY());
        paneFormNewMetric.setPrefHeight(tblMetrics.getPrefHeight());
        paneFormNewMetric.setPrefWidth(tblMetrics.getPrefWidth());
        navMetric("new");
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
        navMetric("");
    }
    private void saveNewMetric() {
        Metric metric = new Metric();
        metric.setMetric_label(txtFormNewLabel.getText());
        metric.setMetric_year(Integer.parseInt(txtFormNewYear.getText()));
        metric.setMetric_period(Integer.parseInt(txtFormNewPeriod.getText()));
        metric.setMetric_shares(VarComp.getCurrentCompany().getShares_outstanding());
        metric.setCompany_id(VarComp.getCurrentCompany().getCompany_id());
        metric.insertMetric();

        cancelNewMetric();
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        tblMetrics.getItems().removeAll();
        tblMetrics.setItems(metricList);
    }

    private void navMetric(String loc){
        paneMetricTable.setVisible(false);
        paneMetricTable.setManaged(false);
        paneFormNewMetric.setVisible(false);
        paneFormNewMetric.setManaged(false);
        paneEmployeeKPI.setVisible(false);
        paneEmployeeKPI.setManaged(false);
        paneMetricDetail.setVisible(false);
        paneMetricDetail.setManaged(false);
        paneFormEditorKPI.setVisible(false);
        paneFormEditorKPI.setManaged(false);
        btnMetricSave.setVisible(false);
        btnMetricHome.setVisible(false);
        btnMetricPrint.setVisible(false);
        chkPrintPreview.setVisible(false);
        lblPrintJobStatus.setVisible(false);
        bntNewMetric.setVisible(false);
        btnMetricSave.setManaged(false);
        btnMetricHome.setManaged(false);
        btnMetricPrint.setManaged(false);
        chkPrintPreview.setManaged(false);
        lblPrintJobStatus.setManaged(false);
        bntNewMetric.setManaged(false);
        showCompKIPEditor(false);
        showFormEditorKPI(false);

        switch (loc) {
            case "new" -> {
                paneFormNewMetric.setVisible(true);
                paneFormNewMetric.setManaged(true);
            }
            case "detail" -> {
                paneMetricDetail.setVisible(true);
                paneMetricDetail.setManaged(true);
                btnMetricSave.setVisible(true);
                btnMetricHome.setVisible(true);
                btnMetricPrint.setVisible(true);
                chkPrintPreview.setVisible(true);
                lblPrintJobStatus.setVisible(true);
                btnMetricSave.setManaged(true);
                btnMetricHome.setManaged(true);
                btnMetricPrint.setManaged(true);
                chkPrintPreview.setManaged(true);
                lblPrintJobStatus.setManaged(true);
                lblPrintJobStatus.setText("");
                lblDetailMetricYearWarn.setVisible(false);
                lblDetailMetricPeriodWarn.setVisible(false);
            }
            default -> {
                paneMetricTable.setVisible(true);
                paneMetricTable.setManaged(true);
                bntNewMetric.setVisible(true);
                bntNewMetric.setManaged(true);
                fillPaneMetricDetail(new Metric());
            }
        }
    }
    private void navMetricDetail(Metric metric){
        metricDetail.setMetric(metric);
        fillPaneMetricDetail(metricDetail);
        navMetric("detail");
    }

    private void fillPaneMetricDetail(Metric metric) {
        NumberFormat fmDollar = NumberFormat.getCurrencyInstance();
        txtDetailMetricLabel.setText(metric.getMetric_label());
        txtDetailMetricYear.setText(metric.getMetric_year().toString());
        txtDetailMetricPeriod.setText(metric.getMetric_period().toString());
        chkDetailMetricLocked.setSelected(metric.getLocked());
        txtDetailMetricEarning.setText(fmDollar.format(metric.getMetric_earnings()));
        txtDetailMetricFunding.setText(fmDollar.format(metric.getMetric_funding()));
        txtDetailMetricPayout.setText(fmDollar.format(metric.getMetric_payout()));
        txtDetailMetricShares.setText(metric.getMetric_shares().toString());
        txtDetailMetricEPS.setText(fmDollar.format(metric.getMetric_eps()));
        btnNotes.setText(NoteConnector.countMetricNotes(metric.getMetric_id()).toString());
        setLockStyle();

        if ( !metric.getMetric_id().equals(0) ) {
            fillDetailMetricPeriods();
            fillCompanyKPIs();
            // Update calculations and fill employee scores
            updateEmployeeScores();
        }
    }

    private void setLockStyle() {
        if(metricDetail.getLocked()) {
            boxMetricDetailStatus.setStyle("-fx-background-color: DARKRED;");
            btnMetricSave.setDisable(true);
            btnDetailAddMetricDetail.setDisable(true);
            btnDetailRemoveMetricDetail.setDisable(true);
            btnAddCompanyKPI.setDisable(true);
            btnAddEmployee.setDisable(true);
            btnRemoveCompanyKPI.setDisable(true);
            btnRemoveEmployee.setDisable(true);
            btnSharesRefresh.setDisable(true);
        } else {
            boxMetricDetailStatus.setStyle("-fx-background-color: DARKGREEN;");
            btnMetricSave.setDisable(false);
            btnDetailAddMetricDetail.setDisable(false);
            btnDetailRemoveMetricDetail.setDisable(false);
            btnAddCompanyKPI.setDisable(false);
            btnAddEmployee.setDisable(false);
            btnRemoveCompanyKPI.setDisable(false);
            btnRemoveEmployee.setDisable(false);
            btnSharesRefresh.setDisable(false);
        }
    }
    private void setMetricLock(Boolean locked) {
        metricDetail.setLocked(locked);
        setLockStyle();
        saveMetricDetail();
    }

    private void fillDetailMetricPeriods() {
        metricDetailList = FXCollections.observableArrayList(MetricDetailConnector.getMetricDetails(metricDetail.getMetric_id()));
        tblDetailMetricPeriods.getItems().removeAll();
        tblDetailMetricPeriods.setItems(metricDetailList);
    }

    private void fillCompanyKPIs() {
        companyKPIObservableList = FXCollections.observableArrayList(CompanyKPIConnector.getCompanyKPIs(metricDetail.getMetric_id()));
        tblDetailCompanyKPI.getItems().removeAll();
        tblDetailCompanyKPI.setItems(companyKPIObservableList);
    }

    private void fillEmployees() {
        employeeScoresObservableList = FXCollections.observableArrayList(EmployeeScoreConnector.getEmployeeScores(metricDetail.getMetric_id()));
        tblDetailEmployeeScores.getItems().removeAll();
        tblDetailEmployeeScores.setItems(employeeScoresObservableList);
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

     private void saveMetricDetail() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "$#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        metricDetail.setMetric_label(txtDetailMetricLabel.getText());
        metricDetail.setMetric_year(Integer.parseInt(txtDetailMetricYear.getText()));
        metricDetail.setMetric_period(Integer.parseInt(txtDetailMetricPeriod.getText()));
        if( !metricDetail.getLocked() && chkDetailMetricLocked.isSelected() ) {
            metricDetail.setLock_date(LocalDate.now());
        }
        metricDetail.setLocked(chkDetailMetricLocked.isSelected());
//        try{
//            metricDetail.setMetric_earnings((BigDecimal) decimalFormat.parse(txtDetailMetricEarning.getText()));
//        } catch (ParseException e) {
//            System.out.println("Earnings is not a number!");
//        }
//        try{
//            metricDetail.setMetric_funding((BigDecimal) decimalFormat.parse(txtDetailMetricFunding.getText()));
//        } catch (ParseException e) {
//            System.out.println("Funding is not a number!");
//        }
//        try{
//            metricDetail.setMetric_payout((BigDecimal) decimalFormat.parse(txtDetailMetricPayout.getText()));
//        } catch (ParseException e) {
//            System.out.println("Payout is not a number!");
//        }
//        try{
//            metricDetail.setMetric_eps((BigDecimal) decimalFormat.parse(txtDetailMetricEPS.getText()));
//        } catch (ParseException e) {
//            System.out.println("EPS is not a number!");
//        }
//        metricDetail.setMetric_shares(Integer.parseInt(txtDetailMetricShares.getText()));
        metricDetail.updateMetric();
        setLockStyle();

        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        tblMetrics.getItems().removeAll();
        tblMetrics.setItems(metricList);
     }

     private void addMetricPeriodDetail() {
         showFormDP(new MetricDetail());
     }

     private void saveMetricPeriodDetail() {
         DecimalFormatSymbols symbols = new DecimalFormatSymbols();
         symbols.setDecimalSeparator('.');
         String pattern = "#0.0#";
         DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
         decimalFormat.setParseBigDecimal(true);

         BigDecimal dpBudget;
         BigDecimal dpActual;
         BigDecimal dpEarnings;

         try{
             dpBudget = ((BigDecimal) decimalFormat.parse(txtFormDPBudget.getText()));
         } catch (ParseException e) {
             System.out.println("Budget is not a number!");
             dpBudget = BigDecimal.valueOf(0);
         }
         try{
             dpActual = ((BigDecimal) decimalFormat.parse(txtFormDPActual.getText()));
         } catch (ParseException e) {
             System.out.println("Actual is not a number!");
             dpActual = BigDecimal.valueOf(0);
         }
         try{
             dpEarnings = ((BigDecimal) decimalFormat.parse(txtFormDPEarnings.getText()));
         } catch (ParseException e) {
             System.out.println("Earnings is not a number!");
             dpEarnings = BigDecimal.valueOf(0);
         }

         MetricDetail md = new MetricDetail(Integer.parseInt(txtFormDPDetailID.getText()),
                 metricDetail.getMetric_id(), Integer.parseInt(txtFormDPPeriod.getText()),
                 dpBudget, dpActual, dpEarnings);

         if(md.getMetric_detail_id().equals(0)) {
             md.insertMetricDetail();
         } else {
             md.updateMetricDetail();
         }
         fillDetailMetricPeriods();
         calcMetric();
         showFormDP();
     }

    private void showFormDP() {
//        paneMetricDetail.setVisible(true);
        boxMetricDetailTable.setVisible(true);
        boxMetricDetailTable.setManaged(true);
        formDetailMetricPeriod.setVisible(false);
        formDetailMetricPeriod.setManaged(false);
    }
    private void showFormDP(MetricDetail md) {
        boxMetricDetailTable.setVisible(false);
        boxMetricDetailTable.setManaged(false);
        formDetailMetricPeriod.setLayoutX(boxMetricDetailTable.getLayoutX());
        formDetailMetricPeriod.setLayoutY(boxMetricDetailTable.getLayoutY());
        formDetailMetricPeriod.setVisible(true);
        formDetailMetricPeriod.setManaged(true);

        txtFormDPPeriod.setText(md.getDetail_period().toString());
        txtFormDPBudget.setText(md.getDetail_budget().toString());
        txtFormDPActual.setText(md.getDetail_actual().toString());
        txtFormDPEarnings.setText(md.getDetail_earnings().toString());
        txtFormDPDetailID.setText(md.getMetric_detail_id().toString());
    }

    private void removeMetricPeriodDetail() {
       MetricDetail md = tblDetailMetricPeriods.getSelectionModel().getSelectedItem();
       md.deleteMetricDetail();
       fillDetailMetricPeriods();
       calcMetric();
    }

    private void dpFormCalcEarnings() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        BigDecimal dpBudget;
        BigDecimal dpActual;

        try{
            dpBudget = ((BigDecimal) decimalFormat.parse(txtFormDPBudget.getText()));
        } catch (ParseException e) {
            System.out.println("Budget is not a number!");
            return;
        }
        try{
            dpActual = ((BigDecimal) decimalFormat.parse(txtFormDPActual.getText()));
        } catch (ParseException e) {
            System.out.println("Actual is not a number!");
            return;
        }

        if(dpBudget.doubleValue() > 0 && dpActual.doubleValue() > 0) {
            txtFormDPEarnings.setText(dpActual.subtract(dpBudget).toString());
        }
    }

    private void updateShares() {
        NumberFormat fmDollar = NumberFormat.getCurrencyInstance();
        metricDetail.setMetric_shares(VarComp.getCurrentCompany().getShares_outstanding());
        txtDetailMetricShares.setText(metricDetail.getMetric_shares().toString());
        if(metricDetail.getMetric_funding().doubleValue() > 0) {
            double eps = metricDetail.getMetric_funding().doubleValue() / metricDetail.getMetric_shares();
            BigDecimal metricEPS = new BigDecimal(eps);
            metricEPS = metricEPS.setScale(2, RoundingMode.HALF_UP);
            metricDetail.setMetric_eps(metricEPS);
            txtDetailMetricEPS.setText(fmDollar.format(metricEPS));
        }
        updateEmployeeScores();
    }

    private void calcMetric() {
        NumberFormat fmDollar = NumberFormat.getCurrencyInstance();
        BigDecimal metricEarnings = BigDecimal.valueOf(0.00);
        BigDecimal metricFunding = BigDecimal.valueOf(0.00);
        BigDecimal metricEPS = BigDecimal.valueOf(0.00);
        double eps;
        int metricShares = Integer.parseInt(txtDetailMetricShares.getText());

        for (MetricDetail md : metricDetailList) {
            metricEarnings = metricEarnings.add(md.getDetail_earnings());
        }

        if(metricEarnings.doubleValue() > 0){
            metricFunding = metricEarnings.multiply(VarComp.getCurrentCompany().getFunding_percentage());
            metricFunding = metricFunding.setScale(2, RoundingMode.HALF_UP);
            try {
                eps = metricFunding.doubleValue() / metricShares;
                metricEPS = new BigDecimal(eps);
                metricEPS = metricEPS.setScale(2, RoundingMode.HALF_UP);
            } catch (Exception e) {
                metricEPS = BigDecimal.valueOf(0.00);
            }

        }

        metricDetail.setMetric_earnings(metricEarnings);
        metricDetail.setMetric_funding(metricFunding);
        metricDetail.setMetric_eps(metricEPS);

        txtDetailMetricEarning.setText(fmDollar.format(metricEarnings));
        txtDetailMetricFunding.setText(fmDollar.format(metricFunding));
        txtDetailMetricEPS.setText(fmDollar.format(metricEPS));
        updateEmployeeScores();
    }

    private void addMissingCompanyKPIs() {
        // get KPIs from master list that are company-wide
        ArrayList<KPIMaster> masterKPIArrayList = KPIMasterConnector.getKPIMasters();
        // create metric KPIs from master list
        for (KPIMaster kpm : masterKPIArrayList) {
            if(kpm.getKpi_class().equals(1)) {
                if (findByCKPIMasterID(kpm.getKpi_master_id()).isEmpty()) {
                    CompanyKPIConnector.insertCompanyKPI(new CompanyKPI(VarComp.getCurrentCompany().getCompany_id(),
                            kpm.getKpi_code(), kpm.getKpi_master_id(), metricDetail.getMetric_id(), kpm.getKpi_class(),
                            kpm.getF1_name(), kpm.getF2_name(), kpm.getF3_name(), kpm.getF4_name(), kpm.getCalc_instructions()));
                }
            }
        }
        fillCompanyKPIs();
        updateEmployeeScores();
    }
    private ArrayList<CompanyKPI> findByCKPIMasterID(Integer i) {
        return (ArrayList<CompanyKPI>) companyKPIObservableList.stream()
                .filter(t -> t.getKpi_master_id().equals(i))
                .collect(Collectors.toList());
    }

    private void removeCompanyKPI() {
        CompanyKPIConnector.deleteCompanyKPI(tblDetailCompanyKPI.getSelectionModel().getSelectedItem().getCompany_kpi_id());
        fillCompanyKPIs();
    }

    private void showCompKIPEditor(Boolean show) {
        if(show) {
            fillFormEditorCompanyKPI();
        }
        boxDetailCompanyKPI.setVisible(!show);
        boxDetailCompanyKPI.setManaged(!show);
        paneDetailEditorCompanyKPI.setVisible(show);
        paneDetailEditorCompanyKPI.setManaged(show);
    }

    private void fillFormEditorCompanyKPI() {
        CompanyKPI cKPI = tblDetailCompanyKPI.getSelectionModel().getSelectedItem();
        paneDetailEditorCompanyKPI.setLayoutX(tblDetailCompanyKPI.getLayoutX());
        paneDetailEditorCompanyKPI.setLayoutY(tblDetailCompanyKPI.getLayoutY());

        lblCompKPIEditorCode.setText(cKPI.getKpi_code());
        lblCompKPIEditorClass.setText(KPIClassConnector.getKPIClass(cKPI.getKpi_class()).getName());
        lblCompKPIEditorWeight.setText(cKPI.getWeight().toString());

        lblCompKPIEditorF1Name.setText(cKPI.getF1_name());
        txtCompKPIEditorF1Data.setText(cKPI.getF1_data().toString());
        if(cKPI.getF1_name().length() > 1){
            boxCompKPIF1.setVisible(true);
            boxCompKPIF1.setManaged(true);
        }else{
            boxCompKPIF1.setVisible(false);
            boxCompKPIF1.setManaged(false);
        }
        lblCompKPIEditorF2Name.setText(cKPI.getF2_name());
        txtCompKPIEditorF2Data.setText(cKPI.getF2_data().toString());
        if(cKPI.getF2_name().length() > 1){
            boxCompKPIF2.setVisible(true);
            boxCompKPIF2.setManaged(true);
        }else{
            boxCompKPIF2.setVisible(false);
            boxCompKPIF2.setManaged(false);
        }
        lblCompKPIEditorF3Name.setText(cKPI.getF3_name());
        txtCompKPIEditorF3Data.setText(cKPI.getF3_data().toString());
        if(cKPI.getF3_name().length() > 1){
            boxCompKPIF3.setVisible(true);
            boxCompKPIF3.setManaged(true);
        }else{
            boxCompKPIF3.setVisible(false);
            boxCompKPIF3.setManaged(false);
        }
        lblCompKPIEditorF4Name.setText(cKPI.getF4_name());
        txtCompKPIEditorF4Data.setText(cKPI.getF4_data().toString());
        if(cKPI.getF4_name().length() > 1){
            boxCompKPIF4.setVisible(true);
            boxCompKPIF4.setManaged(true);
        }else{
            boxCompKPIF4.setVisible(false);
            boxCompKPIF4.setManaged(false);
        }
        txtCompKPIEditorGrade.setText(cKPI.getKpi_grade().toString());
        txtCompKPIEditorScore.setText(cKPI.getKpi_score().toString());
        lblCompKPIEditorID.setText(cKPI.getCompany_kpi_id().toString());

        cmbCompKPIEditorCalc.getItems().clear();
        String cbCalc = "";
        for (CalculationOptions cOption: calcList) {
            cmbCompKPIEditorCalc.getItems().add(cOption.getCalculation_id()+": "+cOption.getCalculation_name());
            if(cOption.getCalculation_id().equals(cKPI.getCalc_instructions())) {
                cbCalc = cOption.getCalculation_id()+": "+cOption.getCalculation_name();
            }
        }
        cmbCompKPIEditorCalc.setValue(cbCalc);
    }
    private void saveCompanyKPIEditor() throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        CompanyKPI cKPI = tblDetailCompanyKPI.getSelectionModel().getSelectedItem();

        cKPI.setF1_data((BigDecimal) decimalFormat.parse(txtCompKPIEditorF1Data.getText()));
        cKPI.setF2_data((BigDecimal) decimalFormat.parse(txtCompKPIEditorF2Data.getText()));
        cKPI.setF3_data((BigDecimal) decimalFormat.parse(txtCompKPIEditorF3Data.getText()));
        cKPI.setF4_data((BigDecimal) decimalFormat.parse(txtCompKPIEditorF4Data.getText()));
        cKPI.calcScore();
        cKPI.calcGrade();

        CompanyKPIConnector.updateCompanyKPI(cKPI);

        showCompKIPEditor(false);
        updateEmployeeScores();
    }
    private void gradeKPICompany() {
        CompanyKPI cKPI = tblDetailCompanyKPI.getSelectionModel().getSelectedItem();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        try {
            cKPI.setF1_data((BigDecimal) decimalFormat.parse(txtCompKPIEditorF1Data.getText()));
            cKPI.setF2_data((BigDecimal) decimalFormat.parse(txtCompKPIEditorF2Data.getText()));
            cKPI.setF3_data((BigDecimal) decimalFormat.parse(txtCompKPIEditorF3Data.getText()));
            cKPI.setF4_data((BigDecimal) decimalFormat.parse(txtCompKPIEditorF4Data.getText()));
            cKPI.calcScore();
            cKPI.calcGrade();
        } catch (ParseException e) {
            System.out.println(e);
        }

        txtCompKPIEditorScore.setText(cKPI.getKpi_score().toString());
        txtCompKPIEditorGrade.setText(cKPI.getKpi_grade().toString());
    }

    private void removeMissingEmployees() {
        Employee employee = EmployeeConnector.getEmployee(tblDetailEmployeeScores.getSelectionModel().getSelectedItem().getEmployee_id());
        employee.removeFromMetric(metricDetail.getMetric_id());
        fillEmployees();
    }

    private void addMissingEmployees() {
        // get active employees
        ArrayList<Employee> employeeArrayList = EmployeeConnector.getEmployees(true);
        // loop over current employees and add missing
        for ( Employee employee : employeeArrayList ) {
            if (findByEmployeeID(employee.getEmployee_id()).isEmpty()) {
                employee.addToMetric(metricDetail.getMetric_id());
            }
        }
        updateEmployeeScores();
    }
    private ArrayList<EmployeeScore> findByEmployeeID(String s) {
        return (ArrayList<EmployeeScore>) employeeScoresObservableList.stream()
                .filter(t -> t.getEmployee_id().equals(s))
                .collect(Collectors.toList());
    }

    private void closeEmployeeKPIEdit() {
        paneEmployeeKPI.setVisible(false);
        paneEmployeeKPI.setManaged(false);
        showFormEditorKPI(false);
    }

    private void openEmployeeKPIEditor(EmployeeScore score) {
        NumberFormat fmPercent = NumberFormat.getPercentInstance();
        employeeScore.setEmployeeScore(score);
        employeeEdit.setEmployee(EmployeeConnector.getEmployee(employeeScore.getEmployee_id()));
        NumberFormat fmDollar = NumberFormat.getCurrencyInstance();

        txtEditorEmployeeID.setText(employeeEdit.getEmployee_id());
        txtEditorEmployeeFirstName.setText(employeeEdit.getFirst_name());
        txtEditorEmployeeLastName.setText(employeeEdit.getLast_name());
        txtEditorEmployeeShares.setText(employeeScore.getShares().toString());
        txtEditorEmployeeGrade.setText(fmPercent.format(employeeScore.getGrade().doubleValue()));
        txtEditorEmployeeSBonus.setText(fmDollar.format(employeeScore.getBonus().doubleValue()));

        ArrayList<Position> positionList = PositionsConnector.getPositions();
        for (Position p: positionList) {
            if (employeeEdit.getPosition().equals(p.getPosition_id())) {
                txtEditorEmployeePosition.setText(p.getPosition_name());
            }
        }

        employeeKPIObservableList = FXCollections.observableArrayList(EmployeeKPIConnector.getEmployeeKPIsByScore(employeeScore.getScore_id()));
        tblEditorKPIs.getItems().removeAll();
        tblEditorKPIs.setItems(employeeKPIObservableList);

        showFormEditorKPI(false);
        paneEmployeeKPI.setVisible(true);
        paneEmployeeKPI.setManaged(true);
    }
    private void showFormEditorKPI(Boolean show) {
        if(show) {
            fillFormEditorKPI();
        }
        tblEditorKPIs.setVisible(!show);
        tblEditorKPIs.setManaged(!show);
        paneFormEditorKPI.setVisible(show);
        paneFormEditorKPI.setManaged(show);
    }

    private void fillFormEditorKPI() {
        EmployeeKPI eKPI = tblEditorKPIs.getSelectionModel().getSelectedItem();

        lblKPIEditorCode.setText(eKPI.getKpi_code());
        lblKPIEditorClass.setText(KPIClassConnector.getKPIClass(eKPI.getKpi_class()).getName());
        lblKPIEditorWeight.setText(eKPI.getWeight().toString());

        lblKPIEditorF1Name.setText(eKPI.getF1_name());
        txtKPIEditorF1Data.setText(eKPI.getF1_data().toString());
        if(eKPI.getF1_name().length() > 1){
            boxEditorKPIF1.setVisible(true);
            boxEditorKPIF1.setManaged(true);
        }else{
            boxEditorKPIF1.setVisible(false);
            boxEditorKPIF1.setManaged(false);
        }

        lblKPIEditorF2Name.setText(eKPI.getF2_name());
        txtKPIEditorF2Data.setText(eKPI.getF2_data().toString());
        if(eKPI.getF2_name().length() > 1){
            boxEditorKPIF2.setVisible(true);
            boxEditorKPIF2.setManaged(true);
        }else{
            boxEditorKPIF2.setVisible(false);
            boxEditorKPIF2.setManaged(false);
        }

        lblKPIEditorF3Name.setText(eKPI.getF3_name());
        txtKPIEditorF3Data.setText(eKPI.getF3_data().toString());
        if(eKPI.getF3_name().length() > 1){
            boxEditorKPIF3.setVisible(true);
            boxEditorKPIF3.setManaged(true);
        }else{
            boxEditorKPIF3.setVisible(false);
            boxEditorKPIF3.setManaged(false);
        }

        lblKPIEditorF4Name.setText(eKPI.getF4_name());
        txtKPIEditorF4Data.setText(eKPI.getF4_data().toString());
        if(eKPI.getF4_name().length() > 1){
            boxEditorKPIF4.setVisible(true);
            boxEditorKPIF4.setManaged(true);
        }else{
            boxEditorKPIF4.setVisible(false);
            boxEditorKPIF4.setManaged(false);
        }

        txtKPIEditorGrade.setText(eKPI.getKpi_grade().toString());
        txtKPIEditorScore.setText(eKPI.getKpi_score().toString());
        lblKPIEditorID.setText(eKPI.getEmployee_kpi_id().toString());

        cmbKPIEditorCalc.getItems().clear();
        String cbCalc = "";
        for (CalculationOptions cOption: calcList) {
            cmbKPIEditorCalc.getItems().add(cOption.getCalculation_id()+": "+cOption.getCalculation_name());
            if(cOption.getCalculation_id().equals(eKPI.getCalc_instructions())) {
                cbCalc = cOption.getCalculation_id()+": "+cOption.getCalculation_name();
            }
        }
        cmbKPIEditorCalc.setValue(cbCalc);

        lblKPIEditorCompany.setVisible(eKPI.getKpi_class().equals(1));
    }
    private void saveKPIEditor() throws ParseException {
        EmployeeKPI eKPI = tblEditorKPIs.getSelectionModel().getSelectedItem();
        if(eKPI.getKpi_class().equals(1)) {
            showFormEditorKPI(false);
            return;
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        eKPI.setF1_data((BigDecimal) decimalFormat.parse(txtKPIEditorF1Data.getText()));
        eKPI.setF2_data((BigDecimal) decimalFormat.parse(txtKPIEditorF2Data.getText()));
        eKPI.setF3_data((BigDecimal) decimalFormat.parse(txtKPIEditorF3Data.getText()));
        eKPI.setF4_data((BigDecimal) decimalFormat.parse(txtKPIEditorF4Data.getText()));
        eKPI.calcScore();
        eKPI.calcGrade();

        EmployeeKPIConnector.updateEmployeeKPI(eKPI);

        showFormEditorKPI(false);
        updateEmployeeScores();
        openEmployeeKPIEditor(EmployeeScoreConnector.getEmployeeScore(eKPI.getScore_id()));
        updateEmployeeScores();
    }
    private void gradeKPI() {
        EmployeeKPI eKPI = tblEditorKPIs.getSelectionModel().getSelectedItem();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        try {
            eKPI.setF1_data((BigDecimal) decimalFormat.parse(txtKPIEditorF1Data.getText()));
            eKPI.setF2_data((BigDecimal) decimalFormat.parse(txtKPIEditorF2Data.getText()));
            eKPI.setF3_data((BigDecimal) decimalFormat.parse(txtKPIEditorF3Data.getText()));
            eKPI.setF4_data((BigDecimal) decimalFormat.parse(txtKPIEditorF4Data.getText()));
            eKPI.calcScore();
            eKPI.calcGrade();
        } catch (ParseException e) {
            System.out.println(e);
        }

        txtKPIEditorScore.setText(eKPI.getKpi_score().toString());
        txtKPIEditorGrade.setText(eKPI.getKpi_grade().toString());
    }

/* Update Employee Scores and Metric Payout */
    private void updateEmployeeScores() {
        NumberFormat fmDollar = NumberFormat.getCurrencyInstance();
        BigDecimal metricPayout = new BigDecimal("0.00");

        ArrayList<EmployeeKPI> employeeKPIList;
        BigDecimal employeeCalcGrade;
        BigDecimal employeeCalcBonus;

        // get employee Scores
        ArrayList<EmployeeScore> employeeScoreList = EmployeeScoreConnector.getEmployeeScores(metricDetail.getMetric_id());

        for ( EmployeeScore employeeScore : employeeScoreList ) {
            // update companyKPIs
            Employee employee = EmployeeConnector.getEmployee(employeeScore.getEmployee_id());
            employee.updateCompanyKPIs(metricDetail.getMetric_id());

            // update all scores
            employeeCalcGrade = new BigDecimal("0.00");
            // loop over employee KPIs
            employeeKPIList = EmployeeKPIConnector.getEmployeeKPIsByScore(employeeScore.getScore_id());
            for ( EmployeeKPI eKPI : employeeKPIList ) {
                // Calculate the Employee KPI Score and then Grade
                // eKPI.calcScore();
                //neKPI.calcGrade();

                // add the KPI Scores
                employeeCalcGrade = employeeCalcGrade.add((eKPI.getKpi_grade().multiply(eKPI.getWeight())));
            }
            //update the employee score
            employeeCalcBonus = employeeCalcGrade.multiply(BigDecimal.valueOf(employeeScore.getShares()));
            employeeCalcBonus = employeeCalcBonus.multiply(metricDetail.getMetric_eps());
            employeeCalcBonus = employeeCalcBonus.setScale(2, RoundingMode.HALF_UP);
            employeeScore.setGrade(employeeCalcGrade);
            employeeScore.setBonus(employeeCalcBonus);
            EmployeeScoreConnector.updateEmployeeScore(employeeScore);
            // add Bonus to Payout
            metricPayout = metricPayout.add(employeeCalcBonus);
        }


        // update metric payout
        metricDetail.setMetric_payout(metricPayout);
        txtDetailMetricPayout.setText(fmDollar.format(metricPayout));

        fillCompanyKPIs();
        fillEmployees();
        saveMetricDetail();
    }

    private void navNotes() {
        String notesTitle = metricDetail.getMetric_label()+" - Notes";

        System.out.println(Window.getWindows());
        List<Window> windows = Window.getWindows();
        for (Window w : windows) {
            if (((Stage) w).getTitle().equals(notesTitle)) {
                System.out.println(w.sceneProperty().getName());
                ((Stage) w).show();
                ((Stage) w).toFront();
                return;
            }
        }

        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(VarComp.class.getResource("vcnotes-view.fxml"));
            root = loader.load();
            ((ViewFilteredController)loader.getController()).init("", metricDetail.getMetric_id(), "ALL", notesTitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle(notesTitle);
        stage.show();
    }

/* Printing */
    private void printMetricDetail() {
        printPreview("vcmetric-print.fxml", "Print Metric");
    }

    public void printPreview(String fxmlFile, String title) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;
        boolean showPreview = true;

        try {
            loader = new FXMLLoader(VarComp.class.getResource(fxmlFile));
            root = loader.load();
            showPreview = ((MetricPrintController)loader.getController()).init(metricDetail.getMetric_id(), chkPrintPreview.isSelected());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ( showPreview ) {
            stage.setScene(new Scene(root, 395, 450));
            stage.setTitle(title);
            stage.show();
        }
    }

}
