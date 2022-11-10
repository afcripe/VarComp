package net.dahliasolutions.varcomp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import net.dahliasolutions.varcomp.connectors.*;
import net.dahliasolutions.varcomp.models.*;

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
    private Button btnAddCompanyKPI;
    @FXML
    private Button btnRemoveCompanyKPI;
    @FXML
    private Button btnRefreshCompanyKPI;
    @FXML
    private Button btnAddEmployee;
    @FXML
    private Button btnRemoveEmployee;
    @FXML
    private Button btnRefreshEmployee;

    @FXML
    private TableView<CompanyKPI> tblDetailCompanyKPI;
    @FXML
    private TableColumn<CompanyKPI, String> tbcCompanyKPICode;
    @FXML
    private TableColumn<CompanyKPI, String> tbcCompanyKPIGrade;
    @FXML
    private TableColumn<CompanyKPI, String> tbcCompanyKPIScore;

    @FXML
    private TableView<EmployeeScore> tblDetailEmployeeScores;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeName;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeShares;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeScore;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeBonus;

    ObservableList<Metric> metricList;
    ObservableList<MetricDetail> metricDetailList;
    ObservableList<CompanyKPI> companyKPIObservableList;
    ObservableList<EmployeeScore> employeeScoresObservableList;
    private final Metric metricDetail = new Metric();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());

        btnMetricHome.setOnAction(event -> navMetricHome());
        bntNewMetric.setOnAction(event -> newMetric());
        txtFormNewYear.setOnKeyPressed(this::updateFormNewMetricLabel);
        txtFormNewPeriod.setOnKeyPressed(this::updateFormNewMetricLabel);
        btnFormNewMetric_cancel.setOnAction(event -> cancelNewMetric());

    /* Metric Table */
        tbcMetricLabel.setCellValueFactory(new PropertyValueFactory<Metric, String>("metric_label"));
        tbcMetricEarnings.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Metric, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Metric, String> param) {
                NumberFormat fm = NumberFormat.getCurrencyInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getMetric_earnings()));
            }
        });
        tbcMetricPayout.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Metric, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Metric, String> param) {
                NumberFormat fm = NumberFormat.getCurrencyInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getMetric_earnings()));
            }
        });
        tbcMetricStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Metric, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Metric, String> param) {
                String cellValue = "Open";
                if(param.getValue().getLocked()) {
                    cellValue = "Closed";
                }
                return new SimpleObjectProperty<String>(cellValue);
            }
        });
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

    /* Metric Detail */
        btnMetricSave.setOnAction(event -> saveMetricDetail());
        btnDetailAddMetricDetail.setOnAction(event -> addMetricPeriodDetail());
        btnDetailRemoveMetricDetail.setOnAction(event -> removeMetricPeriodDetail());
        btnSharesRefresh.setOnAction(event -> updateShares());
        chkDetailMetricLocked.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CheckBox chk = (CheckBox) event.getTarget();
                setMetricLock(chk.isSelected());
            }
        });

        tbcDetailPeriod.setCellValueFactory(new PropertyValueFactory<MetricDetail, Integer>("detail_period"));
        tbcDetailBudget.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MetricDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MetricDetail, String> param) {
                NumberFormat fm = NumberFormat.getCurrencyInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getDetail_budget()));
            }
        });
        tbcDetailActual.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MetricDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MetricDetail, String> param) {
                NumberFormat fm = NumberFormat.getCurrencyInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getDetail_actual()));
            }
        });
        tbcDetailEarnings.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MetricDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MetricDetail, String> param) {
                NumberFormat fm = NumberFormat.getCurrencyInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getDetail_earnings()));
            }
        });
        tblDetailMetricPeriods.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    showFormDP((MetricDetail) tblDetailMetricPeriods.getSelectionModel().getSelectedItem());
                }
            }
        });

    /* Period Detail Form */
        txtFormDPBudget.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            dpFocusState(newValue);
        });
        txtFormDPActual.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            dpFocusState(newValue);
        });
        btnFormDP_cancel.setOnAction(event -> showFormDP());
        btnFormDP_save.setOnAction(event -> saveMetricPeriodDetail());

    /* Company KPIs */
        btnAddCompanyKPI.setOnAction(event -> addMissingCompanyKPIs());
        btnRemoveCompanyKPI.setOnAction(event -> removeCompanyKPI());
        tbcCompanyKPICode.setCellValueFactory(new PropertyValueFactory<CompanyKPI, String>("kpi_code"));
        tbcCompanyKPIGrade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyKPI, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CompanyKPI, String> param) {
                NumberFormat fm = NumberFormat.getNumberInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getKpi_grade()));
            }
        });
        tbcCompanyKPIScore.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompanyKPI, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CompanyKPI, String> param) {
                NumberFormat fm = NumberFormat.getNumberInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getKpi_score()));
            }
        });

    /* Employee Scores */
        btnAddEmployee.setOnAction(event -> addMissingEmployees());
        btnRemoveEmployee.setOnAction(event -> removeMissingEmployees());
        tbcEmployeeName.setCellValueFactory(new PropertyValueFactory<EmployeeScore, String>("employee_id"));
        tbcEmployeeShares.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EmployeeScore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<EmployeeScore, String> param) {
                NumberFormat fm = NumberFormat.getNumberInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getShares()));
            }
        });
        tbcEmployeeScore.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EmployeeScore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<EmployeeScore, String> param) {
                NumberFormat fm = NumberFormat.getNumberInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getScore()));
            }
        });
        tbcEmployeeBonus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EmployeeScore, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<EmployeeScore, String> param) {
                NumberFormat fm = NumberFormat.getNumberInstance();
                return new SimpleObjectProperty<String>(fm.format(param.getValue().getBonus()));
            }
        });
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

    private void newMetric() {
        LocalDate localDate = LocalDate.now();
        int nY = localDate.getYear();
        int nP = (localDate.getMonthValue() / 4)+1;

        txtFormNewYear.setText(Integer.toString(nY));
        txtFormNewPeriod.setText(Integer.toString(nP));
        txtFormNewLabel.setText(nY +" - "+ nP);
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
        metric.setMetric_shares(VarComp.getCurrentCompany().getShares_issued_amount());
        metric.setCompany_id(VarComp.getCurrentCompany().getCompany_id());
        metric.insertMetric();

        cancelNewMetric();
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        tblMetrics.getItems().removeAll();
        tblMetrics.setItems(metricList);
    }

    private void navMetricHome(){
        paneFormNewMetric.setVisible(false);
        fillPaneMetricDetail(new Metric());
        showPaneMetricDetail(false);
    }
    private void navMetricDetail(Metric metric){
        metricDetail.setMetric(metric);
        fillPaneMetricDetail(metricDetail);
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
        setLockStyle();
        fillDetailMetricPeriods();
        fillCompanyKPIs();
        fillEmployees();
    }

    private void setLockStyle() {
        if(metricDetail.getLocked()) {
            boxMetricDetailStatus.setStyle("-fx-background-color: DARKRED;");
            btnMetricSave.setDisable(true);
        } else {
            boxMetricDetailStatus.setStyle("-fx-background-color: DARKGREEN;");
            btnMetricSave.setDisable(false);
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

     private void saveMetricDetail() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        metricDetail.setMetric_label(txtDetailMetricLabel.getText());
        metricDetail.setMetric_year(Integer.parseInt(txtDetailMetricYear.getText()));
        metricDetail.setMetric_period(Integer.parseInt(txtDetailMetricPeriod.getText()));
        if( !metricDetail.getLocked() && chkDetailMetricLocked.isSelected() ) {
            metricDetail.setLock_date(LocalDate.now());
        }
        metricDetail.setLocked(chkDetailMetricLocked.isSelected());
        try{
            metricDetail.setMetric_earnings((BigDecimal) decimalFormat.parse(txtDetailMetricEarning.getText()));
        } catch (ParseException e) {
            System.out.println("Earnings is not a number!");
        }
        try{
            metricDetail.setMetric_funding((BigDecimal) decimalFormat.parse(txtDetailMetricFunding.getText()));
        } catch (ParseException e) {
            System.out.println("Funding is not a number!");
        }
        try{
            metricDetail.setMetric_payout((BigDecimal) decimalFormat.parse(txtDetailMetricPayout.getText()));
        } catch (ParseException e) {
            System.out.println("Payout is not a number!");
        }
        try{
            metricDetail.setMetric_eps((BigDecimal) decimalFormat.parse(txtDetailMetricEPS.getText()));
        } catch (ParseException e) {
            System.out.println("EPS is not a number!");
        }
        metricDetail.setMetric_shares(Integer.parseInt(txtDetailMetricShares.getText()));
        metricDetail.updateMetric();
        setLockStyle();
        // navMetricHome();
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        tblMetrics.getItems().removeAll();
        tblMetrics.setItems(metricList);
     }

     private void addMetricPeriodDetail() {
         showFormDP(new MetricDetail());
//        BigDecimal detailBudget = new BigDecimal(0.00);
//        BigDecimal detailActual = new BigDecimal(0.00);
//        BigDecimal detailEarnings = new BigDecimal(0.00);
//        MetricDetail md = new MetricDetail(0, metricDetail.getMetric_id(), 0, detailBudget, detailActual, detailEarnings);
//        Integer i = md.insertMetricDetail();
//        fillDetailMetricPeriods();
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
        formDetailMetricPeriod.setVisible(false);
    }
    private void showFormDP(MetricDetail md) {
        formDetailMetricPeriod.setLayoutX(boxMetricDetailTable.getLayoutX());
        formDetailMetricPeriod.setLayoutY(boxMetricDetailTable.getLayoutY());
        formDetailMetricPeriod.setPrefWidth(tblDetailMetricPeriods.getPrefWidth());
        formDetailMetricPeriod.setVisible(true);

        txtFormDPPeriod.setText(md.getDetail_period().toString());
        txtFormDPBudget.setText(md.getDetail_budget().toString());
        txtFormDPActual.setText(md.getDetail_actual().toString());
        txtFormDPEarnings.setText(md.getDetail_earnings().toString());
        txtFormDPDetailID.setText(md.getMetric_detail_id().toString());
    }

    private void removeMetricPeriodDetail() {
       MetricDetail md = (MetricDetail) tblDetailMetricPeriods.getSelectionModel().getSelectedItem();
       md.deleteMetricDetail();
       fillDetailMetricPeriods();
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
            try {
                BigDecimal dpEarnings = dpBudget.subtract(dpActual);
                txtFormDPEarnings.setText(dpEarnings.toString());
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }

    private void updateShares() {
        metricDetail.setMetric_shares(VarComp.getCurrentCompany().getShares_issued_amount());
        txtDetailMetricShares.setText(metricDetail.getMetric_shares().toString());
        if(metricDetail.getMetric_funding().doubleValue() > 0) {
            double eps = metricDetail.getMetric_funding().doubleValue() / metricDetail.getMetric_shares();
            BigDecimal metricEPS = new BigDecimal(eps);
            metricEPS = metricEPS.setScale(2, RoundingMode.HALF_UP);
            metricDetail.setMetric_eps(metricEPS);
            txtDetailMetricEPS.setText(metricEPS.toString());
        }
    }

    private void calcMetric() {
        BigDecimal metricEarnings = BigDecimal.valueOf(0.00);
        BigDecimal metricFunding = BigDecimal.valueOf(0.00);
        BigDecimal metricEPS = BigDecimal.valueOf(0.00);
        double eps = 0.00;
        BigDecimal metricPayout = calcMetricPayout();
        int metricShares = Integer.parseInt(txtDetailMetricShares.getText());

        for (MetricDetail md : metricDetailList) {
            metricEarnings = metricEarnings.add(md.getDetail_earnings());
        }

        if(metricEarnings.doubleValue() > 0){
            metricFunding = metricEarnings.multiply(VarComp.getCurrentCompany().getFunding_percentage());
            metricFunding = metricFunding.setScale(2, RoundingMode.HALF_UP);
            eps = metricFunding.doubleValue() / metricShares;
            metricEPS = new BigDecimal(eps);
            metricEPS = metricEPS.setScale(2, RoundingMode.HALF_UP);
        }

        metricDetail.setMetric_earnings(metricEarnings);
        metricDetail.setMetric_funding(metricFunding);
        metricDetail.setMetric_eps(metricEPS);
        metricDetail.setMetric_payout(metricPayout);

        txtDetailMetricEarning.setText(metricEarnings.toString());
        txtDetailMetricFunding.setText(metricFunding.toString());
        txtDetailMetricEPS.setText(metricEPS.toString());
        txtDetailMetricPayout.setText(metricPayout.toString());
    }

    private BigDecimal calcMetricPayout() {
        return BigDecimal.valueOf(0.00);
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
        fillEmployees();
    }
    private ArrayList<EmployeeScore> findByEmployeeID(String s) {
        return (ArrayList<EmployeeScore>) employeeScoresObservableList.stream()
                .filter(t -> t.getEmployee_id().equals(s))
                .collect(Collectors.toList());
    }

}
