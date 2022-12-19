package net.dahliasolutions.varcomp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.dahliasolutions.varcomp.connectors.EmployeeConnector;
import net.dahliasolutions.varcomp.connectors.EmployeeScoreConnector;
import net.dahliasolutions.varcomp.connectors.MetricConnector;
import net.dahliasolutions.varcomp.connectors.PositionsConnector;
import net.dahliasolutions.varcomp.models.Employee;
import net.dahliasolutions.varcomp.models.EmployeeScore;
import net.dahliasolutions.varcomp.models.Position;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.Objects.isNull;

public class EmployeePrintController implements Initializable {

    @FXML
    private AnchorPane PrintPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label lblCompany;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblEmployeeID;
    @FXML
    private Label lblEmployeeFirstName;
    @FXML
    private Label lblEmployeeLastName;
    @FXML
    private Label lblEmployeePosition;
    @FXML
    private Label lblEmployeeSharesStarting;
    @FXML
    private Label lblEmployeeSharesAssigned;
    @FXML
    private Label lblEmployeeActive;
    @FXML
    private Label lblEmployeeStartDate;
    @FXML
    private Label lblFilter;
    @FXML
    private Label lblTotalBonus;
    @FXML
    private TableView<EmployeeScore> tblEmployeeMetrics;
    @FXML
    private TableColumn<EmployeeScore, String> tbcDetailMetric;
    @FXML
    private TableColumn<EmployeeScore, String> tbcDetailShares;
    @FXML
    private TableColumn<EmployeeScore, String> tbcDetailGrade;
    @FXML
    private TableColumn<EmployeeScore, String> tbcDetailBonus;
    @FXML
    private ImageView imgCompanyLogo;

    private Employee selectedEmployee = new Employee();
    private String filterYear = "All";
    ObservableList<EmployeeScore> employeeScores;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbcDetailMetric.setCellValueFactory(param -> new SimpleObjectProperty<>(MetricConnector.getMetric(param.getValue().getMetric_id()).getMetric_label()));
        tbcDetailMetric.setPrefWidth((tblEmployeeMetrics.getPrefWidth()-4)/4);
        tbcDetailShares.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getNumberInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getShares()));
        });
        tbcDetailShares.setPrefWidth((tblEmployeeMetrics.getPrefWidth()-4)/4);
        tbcDetailGrade.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getPercentInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getGrade()));
        });
        tbcDetailGrade.setPrefWidth((tblEmployeeMetrics.getPrefWidth()-4)/4);
        tbcDetailBonus.setCellValueFactory(param -> {
            NumberFormat fm = NumberFormat.getCurrencyInstance();
            return new SimpleObjectProperty<>(fm.format(param.getValue().getBonus()));
        });
        tbcDetailBonus.setPrefWidth((tblEmployeeMetrics.getPrefWidth()-4)/4);
    }

    public boolean init(String employeeID, String year, boolean preview) {
        selectedEmployee = EmployeeConnector.getEmployee(employeeID);
        filterYear = year;
        fillEmployee();

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        lblCompany.setText(VarComp.getCurrentCompany().getCompany_name());
        loadCompanyLogo();

        imgCompanyLogo.setVisible(VarComp.getCurrentCompany().getCompany_logo_show());
        imgCompanyLogo.setManaged(VarComp.getCurrentCompany().getCompany_logo_show());
        lblCompany.setVisible(!VarComp.getCurrentCompany().getCompany_logo_show());
        lblCompany.setManaged(!VarComp.getCurrentCompany().getCompany_logo_show());

        lblTitle.setText("- Employee Report -");
        if(!preview) return printSpool();
        return true;
    }

    private void fillEmployee() {
        lblEmployeeID.setText(selectedEmployee.getEmployee_id());
        lblEmployeeFirstName.setText(selectedEmployee.getFirst_name());
        lblEmployeeLastName.setText(selectedEmployee.getLast_name());
        lblEmployeeSharesStarting.setText(selectedEmployee.getStarting_shares().toString());
        lblEmployeeSharesAssigned.setText(selectedEmployee.getShares_assigned().toString());
        if (selectedEmployee.getIs_active()) {
            lblEmployeeActive.setText("Active");
        } else {
            lblEmployeeActive.setText("Inactive");
        }
        lblEmployeeStartDate.setText(selectedEmployee.getStart_date().toString());
        Position p = PositionsConnector.getPosition(selectedEmployee.getPosition());
        lblEmployeePosition.setText(p.getPosition_id()+": "+p.getPosition_name());

        tblEmployeeMetrics.getItems().removeAll();
        employeeScores = FXCollections.observableArrayList(EmployeeScoreConnector.getEmployeeScoreByEmployee(selectedEmployee.getEmployee_id()));
        tblEmployeeMetrics.setItems(employeeScores);

        if(!filterYear.equals("All")){
            lblFilter.setVisible(true);
            lblFilter.setText("Filtered by year "+filterYear);
            try {
                employeeScores = FXCollections.observableArrayList(EmployeeScoreConnector
                        .getEmployeeScoreByFiltered(selectedEmployee.getEmployee_id(), Integer.parseInt(filterYear)));
            } catch (Exception e) {
                employeeScores = FXCollections.observableArrayList(EmployeeScoreConnector
                        .getEmployeeScoreByEmployee(selectedEmployee.getEmployee_id()));
            }
        } else {
            lblFilter.setVisible(false);
            employeeScores = FXCollections.observableArrayList(EmployeeScoreConnector
                    .getEmployeeScoreByEmployee(selectedEmployee.getEmployee_id()));
        }
        tblEmployeeMetrics.setItems(employeeScores);

        BigDecimal totalBonus = new BigDecimal("0.00");
        NumberFormat fm = NumberFormat.getCurrencyInstance();

        /* Total the Bonus */
        for ( EmployeeScore es : employeeScores ) {
            totalBonus = totalBonus.add(es.getBonus());
        }
        lblTotalBonus.setText(fm.format(totalBonus.doubleValue()));
    }

    private void loadCompanyLogo() {
        String logoPath = DBUtils.getCompanyDir()+DBUtils.getFS()+"companyLogo.png";

        File logoFile = new File(logoPath);
        if(logoFile.exists()) {
            try {
                InputStream stream = new FileInputStream(logoFile);
                imgCompanyLogo.setImage(new Image(stream));
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
