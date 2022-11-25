package net.dahliasolutions.varcomp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MetricPrintController implements Initializable {

    @FXML
    private AnchorPane PrintPane;
    @FXML
    private Label lblCompany;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblLabel;
    @FXML
    private Label lblYear;
    @FXML
    private Label lblPeriod;
    @FXML
    private Label lblClosed;
    @FXML
    private Label lblClosedDate;
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
    private Label lblEarning;
    @FXML
    private Label lblFunding;
    @FXML
    private Label lblPayout;
    @FXML
    private Label lblShares;
    @FXML
    private Label lblEPS;
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
    private TableColumn<EmployeeScore, String> tbcEmployeeGrade;
    @FXML
    private TableColumn<EmployeeScore, String> tbcEmployeeBonus;

    ObservableList<MetricDetail> metricDetailList;
    ObservableList<CompanyKPI> companyKPIObservableList;
    ObservableList<EmployeeScore> employeeScoresObservableList;
    private Metric metricDetail = new Metric();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    /* Metric Detail */
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

    /* Company KPIs */
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

    /* Employee Scores */
        tbcEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
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

    }

    public void init(Integer metricID, boolean preview) {
        metricDetail = MetricConnector.getMetric(metricID);
        fillMetricDetail();
        fillDetailMetricPeriods();
        fillCompanyKPIs();
        fillEmployees();

        lblCompany.setText(VarComp.getCurrentCompany().getCompany_name());
        lblTitle.setText("- Metric Report -");
        if(!preview) printSpool();
    }

    private void fillMetricDetail() {
        NumberFormat fmDollar = NumberFormat.getCurrencyInstance();
        lblLabel.setText(metricDetail.getMetric_label());
        lblYear.setText(metricDetail.getMetric_year().toString());
        lblPeriod.setText(metricDetail.getMetric_period().toString());
        if(metricDetail.getLocked()){
            lblClosed.setText("Closed");
            lblClosedDate.setText(metricDetail.getLock_date().toString());
        } else {
            lblClosed.setText("Open");
            lblClosedDate.setText("");
        }
        lblEarning.setText(fmDollar.format(metricDetail.getMetric_earnings()));
        lblFunding.setText(fmDollar.format(metricDetail.getMetric_funding()));
        lblPayout.setText(fmDollar.format(metricDetail.getMetric_payout()));
        lblShares.setText(metricDetail.getMetric_shares().toString());
        lblEPS.setText(fmDollar.format(metricDetail.getMetric_eps()));

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


/* Printing */
    private void printSpool() {

        // ToDo
        Printer printer = getPrinter();

        // Create a printer job for the default printer
        PrinterJob job = PrinterJob.createPrinterJob(printer);

        if (job != null)
        {
            // Print the node
            boolean printed = job.printPage(PrintPane);

            if (printed)
            {
                // End the printer job
                job.endJob();
            }
            else
            {
                // Write Error Message
                System.out.println("Print job failed");
            }
        }
        else
        {
            // Write Error Message
            System.out.println("Could not create a printer job.");
        }

    }

    private Printer getPrinter() {
        ChoiceDialog dialog = new ChoiceDialog(Printer.getDefaultPrinter(), Printer.getAllPrinters());
        dialog.setHeaderText("Choose Printer");
        dialog.setContentText("Choose a printer from the available printers.");
        dialog.setTitle("Printer Choice");
        Optional<Printer> opt = dialog.showAndWait();
        if (opt.isPresent()) {
            Printer printer = opt.get();
            String nameOfPrinter = printer.getName();
            return printer;
        }
        return Printer.getDefaultPrinter();
    }
}
