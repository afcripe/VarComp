package net.dahliasolutions.varcomp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.dahliasolutions.varcomp.connectors.*;
import net.dahliasolutions.varcomp.models.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class MetricPrintController implements Initializable {

    @FXML
    private AnchorPane PrintPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label lblCompany;
    @FXML
    private ImageView imgCompanyLogo;
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
    String fs;
    String userHomeDir;

    ObservableList<MetricDetail> metricDetailList;
    ObservableList<CompanyKPI> companyKPIObservableList;
    ObservableList<EmployeeScore> employeeScoresObservableList;
    private Metric metricDetail = new Metric();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fs = System.getProperty("file.separator");
        userHomeDir = System.getProperty("user.home");

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
        tbcCompanyKPICode.setPrefWidth((tblDetailCompanyKPI.getPrefWidth()-4)/3);
        tbcCompanyKPIGrade.setPrefWidth((tblDetailCompanyKPI.getPrefWidth()-4)/3);
        tbcCompanyKPIScore.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getNumberInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getKpi_score()));
        });
        tbcCompanyKPIGrade.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getPercentInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getKpi_grade()));
        });
        tbcCompanyKPIScore.setPrefWidth((tblDetailCompanyKPI.getPrefWidth()-4)/3);

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

    public boolean init(Integer metricID, boolean preview) {
        metricDetail = MetricConnector.getMetric(metricID);
        fillMetricDetail();
        fillDetailMetricPeriods();
        fillCompanyKPIs();
        fillEmployees();

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        lblCompany.setText(VarComp.getCurrentCompany().getCompany_name());
        loadCompanyLogo();

        imgCompanyLogo.setVisible(VarComp.getCurrentCompany().getCompany_logo_show());
        imgCompanyLogo.setManaged(VarComp.getCurrentCompany().getCompany_logo_show());
        lblCompany.setVisible(!VarComp.getCurrentCompany().getCompany_logo_show());
        lblCompany.setManaged(!VarComp.getCurrentCompany().getCompany_logo_show());

        lblTitle.setText("- Metric Report -");
        if(!preview) return printSpool();
        return true;
    }

    private void loadCompanyLogo() {
        String logoPath = DBUtils.getCompanyDir()+fs+"companyLogo.png";

        File logoFile = new File(logoPath);
        if(logoFile.exists()) {
            try {
                InputStream stream = new FileInputStream(logoFile);
                imgCompanyLogo.setImage(new Image(stream));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
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
    private boolean printSpool() {
        Printer printer = getPrinter();

        if (!isNull(printer)) {
            // Create a printer job for the default printer
            PrinterJob job = PrinterJob.createPrinterJob(printer);

            // Print the node
            boolean printed = job.printPage(PrintPane);
            if (printed) {
                job.endJob();
            } else {
                System.out.println("Print job failed");
            }
            return !printed;
        } else {
            return false;
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
        return null;
    }
}
