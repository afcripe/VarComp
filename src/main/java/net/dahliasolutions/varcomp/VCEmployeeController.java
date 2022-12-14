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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.dahliasolutions.varcomp.connectors.*;
import net.dahliasolutions.varcomp.models.Employee;
import net.dahliasolutions.varcomp.models.EmployeeScore;
import net.dahliasolutions.varcomp.models.Metric;
import net.dahliasolutions.varcomp.models.Position;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;

public class VCEmployeeController implements Initializable {

    @FXML
    private Button btnEmployeeHome;
    @FXML
    private Pane paneEmployeeTable;
    @FXML
    private Button bntNewEmployee;
    @FXML
    private CheckBox chkPrintPreview;
    @FXML
    private Button btnEmployeePrint;
    @FXML
    private Label lblPrintJobStatus;
    @FXML
    private TableView<Employee> tblEmployees;
    @FXML
    private TableColumn<Employee, String> tbcEmployeeID;
    @FXML
    private TableColumn<Employee, String> tbcEmployeeName;
    @FXML
    private TableColumn<Employee, String> tbcEmployeePosition;
    @FXML
    private TableColumn<Employee, Integer> tbcEmployeeShares;
    @FXML
    private TableColumn<Employee, String> tbcEmployeeActive;
    @FXML
    private Pane paneFormEmployee;
    @FXML
    private Button btnNotes;
    @FXML
    private TextField txtFormEmployeeID;
    @FXML
    private TextField txtFormEmployeeFirstName;
    @FXML
    private TextField txtFormEmployeeLastName;
    @FXML
    private ComboBox<String> cmbFormEmployeePosition;
    @FXML
    private TextField txtFormEmployeeStartingShares;
    @FXML
    private TextField txtFormEmployeeShares;
    @FXML
    private CheckBox chkFormEmployeeActive;
    @FXML
    private DatePicker pkrStartDate;
    @FXML
    private Button btnFormEmployee_cancel;
    @FXML
    private Button btnFormEmployee_save;

    @FXML
    private Pane paneEmployeeDetail;

    @FXML
    private TextField txtEmployeeID;
    @FXML
    private TextField txtEmployeeFirstName;
    @FXML
    private TextField txtEmployeeLastName;
    @FXML
    private ComboBox<String> cmbEmployeePosition;
    @FXML
    private TextField txtEmployeeShares;
    @FXML
    private CheckBox chkEmployeeActive;
    @FXML
    private DatePicker pkrEmployeeStartDate;
    @FXML
    private Label lblEmployeeShares;
    @FXML
    private Button btnEditEmployee_save;
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
    private ComboBox<String> cmbFilterYear;
    @FXML
    private Label lblEmployeeTotalBonus;

    private final Employee selectedEmployee = new Employee();
    ObservableList<Employee> employeeList;
    ObservableList<Position> positionList;
    ObservableList<EmployeeScore> employeeScores;

    ObservableList<Metric> metricList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EmployeeUtils.updateAllEmployeeShares();

        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());
        positionList = FXCollections.observableArrayList(PositionsConnector.getPositions());

        btnEmployeeHome.setOnAction(event -> navEmployee("home"));

    /* Employee List */
        bntNewEmployee.setOnAction(event -> setFormEmployee(new Employee()));

        tbcEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        tbcEmployeeName.setCellValueFactory(param -> {
            String cellValue = param.getValue().getLast_name() + ", " + param.getValue().getFirst_name();
            return new SimpleObjectProperty<>(cellValue);
        });
        tbcEmployeePosition.setCellValueFactory(param -> {
            String cellValue = "";
            for (Position p: positionList) {
                if(p.getPosition_id() == param.getValue().getPosition()){
                    cellValue = p.getPosition_id() + ": " + p.getPosition_name();
                }
            }
            return new SimpleObjectProperty<>(cellValue);
        });
        tbcEmployeePosition.setPrefWidth((tblEmployees.getPrefWidth()-304)/3);
        tbcEmployeeShares.setCellValueFactory(new PropertyValueFactory<>("shares_assigned"));
        tbcEmployeeShares.setPrefWidth((tblEmployees.getPrefWidth()-304)/3);
        tbcEmployeeActive.setCellValueFactory(param -> {
            String cellVal = "Inactive";
            if (param.getValue().getIs_active()) {cellVal = "Active";}
            return new SimpleObjectProperty<>(cellVal);
        });
        tbcEmployeeActive.setPrefWidth((tblEmployees.getPrefWidth()-304)/3);
        tblEmployees.setItems(employeeList);
        tblEmployees.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                fillPaneEmployeeDetail(tblEmployees.getSelectionModel().getSelectedItem());
            }
        });

        btnFormEmployee_cancel.setOnAction(event -> {
            clearFormEmployee();
            navEmployee("home");
        });
        btnFormEmployee_save.setOnAction(event -> saveEmployee());
        txtFormEmployeeID.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> employeeFormFocusState(newValue));
        txtFormEmployeeStartingShares.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> employeeFormFocusState(newValue));
        pkrStartDate.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> employeeFormFocusState(newValue));
        cmbFormEmployeePosition.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> employeeFormFocusState(newValue));


        txtFormEmployeeFirstName.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusState(newValue));
        txtFormEmployeeLastName.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> focusState(newValue));

    /* Employee Detail */
        btnEditEmployee_save.setOnAction(event -> updateEmployee());
        btnEmployeePrint.setOnAction(event -> printMetricDetail());
        btnNotes.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PENCIL_SQUARE_ALT, "12px"));
        btnNotes.setOnAction(event -> navNotes());

        txtEmployeeID.setOnKeyPressed(event -> {
            if(txtEmployeeID.getText().length() > 5) txtEmployeeID.setText(txtEmployeeID.getText().substring(0,5));
        });
        txtEmployeeShares.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> employeeDetailFocusState(newValue));
        pkrEmployeeStartDate.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> employeeDetailFocusState(newValue));
        cmbEmployeePosition.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> employeeDetailFocusState(newValue));

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

        cmbFilterYear.setOnAction(event -> applyFilter());

        navEmployee("home");
    }

    private void navEmployee(String loc) {
        paneFormEmployee.setVisible(false);
        paneFormEmployee.setManaged(false);
        paneEmployeeDetail.setVisible(false);
        paneEmployeeDetail.setManaged(false);
        paneEmployeeTable.setVisible(false);
        paneEmployeeTable.setManaged(false);
        btnEditEmployee_save.setVisible(false);
        btnEmployeeHome.setVisible(false);
        btnEmployeePrint.setVisible(false);
        lblPrintJobStatus.setVisible(false);
        chkPrintPreview.setVisible(false);
        bntNewEmployee.setVisible(false);

        btnEditEmployee_save.setManaged(false);
        btnEmployeeHome.setManaged(false);
        btnEmployeePrint.setManaged(false);
        lblPrintJobStatus.setManaged(false);
        chkPrintPreview.setManaged(false);
        bntNewEmployee.setManaged(false);
        switch (loc) {
            case "new" -> {
                paneFormEmployee.setVisible(true);
                paneFormEmployee.setManaged(true);
            }
            case "detail" -> {
                paneEmployeeDetail.setVisible(true);
                paneEmployeeDetail.setManaged(true);
                btnEditEmployee_save.setVisible(true);
                btnEmployeeHome.setVisible(true);
                btnEmployeePrint.setVisible(true);
                lblPrintJobStatus.setVisible(true);
                lblPrintJobStatus.setText("");
                chkPrintPreview.setVisible(true);
                chkPrintPreview.setSelected(false);
                btnEditEmployee_save.setManaged(true);
                btnEmployeeHome.setManaged(true);
                btnEmployeePrint.setManaged(true);
                lblPrintJobStatus.setManaged(true);
                chkPrintPreview.setManaged(true);
            }
            default -> {
                paneEmployeeTable.setVisible(true);
                paneEmployeeTable.setManaged(true);
                bntNewEmployee.setVisible(true);
                bntNewEmployee.setManaged(true);
            }
        }
    }

    private void setFormEmployee(Employee employee) {
        fillFormEmployee(employee);
        navEmployee("new");
    }
    private void fillFormEmployee(Employee employee) {
        txtFormEmployeeID.setText(employee.getEmployee_id());
        txtFormEmployeeFirstName.setText(employee.getFirst_name());
        txtFormEmployeeLastName.setText(employee.getLast_name());
        txtFormEmployeeStartingShares.setText(employee.getStarting_shares().toString());
        txtFormEmployeeShares.setText(employee.getShares_assigned().toString());
        chkFormEmployeeActive.setSelected(employee.getIs_active());

        // create combobox items
        cmbFormEmployeePosition.getItems().clear();
        for (Position p: positionList) {
            cmbFormEmployeePosition.getItems().add(p.getPosition_id()+": "+p.getPosition_name());
        }
    }

    private void focusState(Boolean b) {
        if(!b) {
            formUpdateEmployeeID();
        }
    }
    private void formUpdateEmployeeID() {
        String idA = "";
        String idB = "";
        if ( !txtFormEmployeeFirstName.getText().isEmpty() ) {
            idA = txtFormEmployeeFirstName.getText().substring(0,1);
        }
        if ( !txtFormEmployeeLastName.getText().isEmpty() ) {
            idB = txtFormEmployeeLastName.getText().substring(0,4);
        }
        txtFormEmployeeID.setText(idA.toUpperCase()+idB.toUpperCase());
        System.out.println(idA);
    }

    private void clearFormEmployee() {
        txtFormEmployeeID.setText("");
        txtFormEmployeeLastName.setText("");
        txtFormEmployeeLastName.setText("");
        txtFormEmployeeStartingShares.setText("");
        txtFormEmployeeShares.setText("");
        cmbFormEmployeePosition.setValue("");
        chkFormEmployeeActive.setSelected(false);
        pkrStartDate.setValue(null);
    }

    private void employeeFormFocusState(Boolean b) {
        if(txtFormEmployeeID.getText().length() > 5) {
            System.out.println(txtFormEmployeeID.getText().length());
            txtFormEmployeeID.setText(txtFormEmployeeID.getText().substring(0,5));
            System.out.println(txtFormEmployeeID.getText().length());
        }
        if(!b) {
            if(pkrStartDate.getValue() != null) {
                String[] split = cmbFormEmployeePosition.getSelectionModel().getSelectedItem().split(":");
                Integer newSharesAssigned = EmployeeUtils.getEmployeeShares(pkrStartDate.getValue(),
                        Integer.parseInt(txtFormEmployeeStartingShares.getText()), Integer.parseInt(split[0]));
                txtFormEmployeeShares.setText(newSharesAssigned.toString());
            }
        }
    }

    private void saveEmployee() {
        int pos = 0;
        if (cmbEmployeePosition.getSelectionModel().getSelectedItem() != null) {
            String[] split = cmbFormEmployeePosition.getSelectionModel().getSelectedItem().split(":");
            pos = Integer.parseInt(split[0]);
        }

        Employee employee = new Employee(txtFormEmployeeID.getText(), pos, txtFormEmployeeFirstName.getText(),
                txtFormEmployeeLastName.getText(), pkrStartDate.getValue(), chkFormEmployeeActive.isSelected(),
                Integer.parseInt(txtFormEmployeeStartingShares.getText()), Integer.parseInt(txtFormEmployeeShares.getText()));

        employee.insertEmployee();

        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());
        tblEmployees.getItems().removeAll();
        tblEmployees.setItems(employeeList);
        clearFormEmployee();
        navEmployee("home");
    }
    private void fillPaneEmployeeDetail(Employee employee) {
        selectedEmployee.setEmployee(employee);
        txtEmployeeID.setText(selectedEmployee.getEmployee_id());
        txtEmployeeFirstName.setText(selectedEmployee.getFirst_name());
        txtEmployeeLastName.setText(selectedEmployee.getLast_name());
        txtEmployeeShares.setText(selectedEmployee.getStarting_shares().toString());
        lblEmployeeShares.setText(selectedEmployee.getShares_assigned().toString());
        chkEmployeeActive.setSelected(selectedEmployee.getIs_active());
        pkrEmployeeStartDate.setValue(selectedEmployee.getStart_date());
        btnNotes.setText(NoteConnector.countEmployeeNotes(employee.getEmployee_id()).toString());

        // create combobox items
        cmbEmployeePosition.getItems().clear();
        String cb = "";
        for (Position p: positionList) {
            cmbEmployeePosition.getItems().add(p.getPosition_id()+": "+p.getPosition_name());
            if (selectedEmployee.getPosition().equals(p.getPosition_id())) {
                cb = p.getPosition_id()+": "+p.getPosition_name();
            }
        }
        cmbEmployeePosition.setValue(cb);

        tblEmployeeMetrics.getItems().removeAll();
        employeeScores = FXCollections.observableArrayList(EmployeeScoreConnector.getEmployeeScoreByEmployee(selectedEmployee.getEmployee_id()));
        tblEmployeeMetrics.setItems(employeeScores);
        getTotalBonus();

        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        cmbFilterYear.getItems().clear();
        cmbFilterYear.getItems().add("All");
        for (Metric m : metricList) {
            cmbFilterYear.getItems().add(m.getMetric_year().toString());
        }
        cmbFilterYear.setValue("All");

        navEmployee("detail");
    }

    private void employeeDetailFocusState(Boolean b) {
        if(!b) {
            String[] split = cmbEmployeePosition.getSelectionModel().getSelectedItem().split(":");
            Integer newSharesAssigned = EmployeeUtils.getEmployeeShares(pkrEmployeeStartDate.getValue(),
                    Integer.parseInt(txtEmployeeShares.getText()), Integer.parseInt(split[0]));

            lblEmployeeShares.setText(newSharesAssigned.toString());
        }
    }

    private void updateEmployee() {
        String[] split = cmbEmployeePosition.getSelectionModel().getSelectedItem().split(":");

        Employee employee = new Employee(selectedEmployee.getEmployee_id(), Integer.parseInt(split[0]), txtEmployeeFirstName.getText(),
                txtEmployeeLastName.getText(), pkrEmployeeStartDate.getValue(), chkEmployeeActive.isSelected(),
                Integer.parseInt(txtEmployeeShares.getText()), Integer.parseInt(lblEmployeeShares.getText()));

        employee.updateEmployee();
        fillPaneEmployeeDetail(employee);
        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());
        tblEmployees.getItems().removeAll();
        tblEmployees.setItems(employeeList);
    }

    private void applyFilter() {
        tblEmployeeMetrics.getItems().removeAll();

        try {
            if (cmbFilterYear.getValue().equals("All")) {
                employeeScores = FXCollections.observableArrayList(EmployeeScoreConnector
                        .getEmployeeScoreByEmployee(selectedEmployee.getEmployee_id()));
            } else {
                employeeScores = FXCollections.observableArrayList(EmployeeScoreConnector
                        .getEmployeeScoreByFiltered(selectedEmployee.getEmployee_id(), Integer.parseInt(cmbFilterYear.getValue())));
            }
        } catch (Exception e) {
            employeeScores = FXCollections.observableArrayList(EmployeeScoreConnector
                    .getEmployeeScoreByEmployee(selectedEmployee.getEmployee_id()));
        }

        tblEmployeeMetrics.setItems(employeeScores);
        getTotalBonus();
    }

    private void getTotalBonus() {
        BigDecimal totalBonus = new BigDecimal("0.00");
        NumberFormat fm = NumberFormat.getCurrencyInstance();

        /* Total the Bonus */
        for ( EmployeeScore es : employeeScores ) {
            totalBonus = totalBonus.add(es.getBonus());
        }
        lblEmployeeTotalBonus.setText(fm.format(totalBonus.doubleValue()));
    }

    private void navNotes() {
        String notesTitle = selectedEmployee.getEmployee_id()+" - Notes";

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
            ((ViewFilteredController)loader.getController()).init("", 0, selectedEmployee.getEmployee_id(), notesTitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle(notesTitle);
        stage.show();
    }

    /* Printing */
    private void printMetricDetail() {
        printPreview("vcemployee-print.fxml", "Print Employee");
    }

    public void printPreview(String fxmlFile, String title) {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;
        boolean showPreview = true;

        try {
            loader = new FXMLLoader(VarComp.class.getResource(fxmlFile));
            root = loader.load();
            showPreview = ((EmployeePrintController)loader.getController())
                    .init(selectedEmployee.getEmployee_id(), cmbFilterYear.getValue(), chkPrintPreview.isSelected());
            System.out.println(cmbFilterYear.getValue());
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
