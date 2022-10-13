package net.dahliasolutions.varcomp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import net.dahliasolutions.varcomp.connectors.EmployeeConnector;
import net.dahliasolutions.varcomp.connectors.KPIClassConnector;
import net.dahliasolutions.varcomp.connectors.PositionsConnector;
import net.dahliasolutions.varcomp.models.Employee;
import net.dahliasolutions.varcomp.models.KPIClass;
import net.dahliasolutions.varcomp.models.Position;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class VCEmployeeController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private Button btnEmployeeHome;
    @FXML
    private Pane paneEmployeeTable;
    @FXML
    private Button bntNewEmployee;
    @FXML
    private TableView tblEmployees;
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
    private TextField txtFormEmployeeID;
    @FXML
    private TextField txtFormEmployeeFirstName;
    @FXML
    private TextField txtFormEmployeeLastName;
    @FXML
    private ComboBox cmbFormEmployeePosition;
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
    private Button btnEditEmployee;
    @FXML
    private Label lblEmployeeName;
    @FXML
    private Label lblEmployeeID;
    @FXML
    private Label lblEmployeeStartDate;
    @FXML
    private Label lblEmployeePosition;
    @FXML
    private Label lblEmployeeIsActive;
    @FXML
    private Label lblShares;
    @FXML
    private ScrollPane spnEmployeeDetail;

    @FXML
    private Pane paneEmployeeInfo;
    @FXML
    private TextField txtEmployeeID;
    @FXML
    private TextField txtEmployeeFirstName;
    @FXML
    private TextField txtEmployeeLastName;
    @FXML
    private ComboBox cmbEmployeePosition;
    @FXML
    private TextField txtEmployeeShares;
    @FXML
    private CheckBox chkEmployeeActive;
    @FXML
    private DatePicker pkrEmployeeStartDate;
    @FXML
    private Button btnEditEmployee_cancel;
    @FXML
    private Button btnEditEmployee_save;

    private Employee selectedEmployee = new Employee();
    ObservableList<Employee> employeeList;
    ObservableList<Position> positionList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());
        positionList = FXCollections.observableArrayList(PositionsConnector.getPositions());

        btnEmployeeHome.setOnAction(event -> navEmployeeHome());

    /* Employee List */
        bntNewEmployee.setOnAction(event -> setFormEmployee(new Employee()));

        tbcEmployeeID.setCellValueFactory(new PropertyValueFactory<Employee, String>("employee_id"));
        tbcEmployeeName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Employee, String> param) {
                String cellValue = param.getValue().getLast_name() + ", " + param.getValue().getFirst_name();
                return new SimpleObjectProperty<String>(cellValue);
            }
        });
        tbcEmployeePosition.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Employee, String> param) {
                String cellValue = "";
                for (Position p: positionList) {
                    if(p.getPosition_id() == param.getValue().getPosition()){
                        cellValue = p.getPosition_id() + ": " + p.getPosition_name();
                    }
                }
                return new SimpleObjectProperty<String>(cellValue);
            }
        });
        tbcEmployeeShares.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("shares_assigned"));
        tbcEmployeeActive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Employee, String> param) {
                String cellVal = "Inactive";
                if (param.getValue().getIs_active()) {cellVal = "Active";}
                return new SimpleObjectProperty<String>(cellVal);
            }
        });
        tblEmployees.setItems(employeeList);
        tblEmployees.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    fillPaneEmployeeDetail((Employee) tblEmployees.getSelectionModel().getSelectedItem());
                }
            }
        });

        btnFormEmployee_cancel.setOnAction(event -> {
            clearFormEmployee();
            showPaneFormEmployee(false);
        });
        btnFormEmployee_save.setOnAction(event -> saveEmployee());

    /* Employee Detail */
        btnEditEmployee.setOnAction(event -> showEditFormEmployee(true));
        btnEditEmployee_cancel.setOnAction(event -> showEditFormEmployee(false));
        btnEditEmployee_save.setOnAction(event -> updateEmployee());

        txtEmployeeID.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(txtEmployeeID.getText().length() > 5) txtEmployeeID.setText(txtEmployeeID.getText().substring(0,5));
            }
        });

        showPaneEmployeeList(true);
    }

    private void navEmployeeHome() {
        showPaneEmployeeDetail(false);
        showPaneEmployeeList(true);
    }

    private void showPaneEmployeeList(Boolean show) {
        paneEmployeeDetail.setVisible(!show);
        paneEmployeeTable.setVisible(show);
        btnEmployeeHome.setVisible(!show);
        showPaneFormEmployee(false);
    }


    private void setFormEmployee(Employee employee) {
        fillFormEmployee(employee);
        showPaneFormEmployee(true);
    }
    private void fillFormEmployee(Employee employee) {
        txtFormEmployeeID.setText(employee.getEmployee_id());
        txtFormEmployeeFirstName.setText(employee.getFirst_name());
        txtFormEmployeeLastName.setText(employee.getLast_name());
        txtFormEmployeeShares.setText(employee.getShares_assigned().toString());
        chkFormEmployeeActive.setSelected(employee.getIs_active());

        // create combobox items
        cmbFormEmployeePosition.getItems().clear();
        for (Position p: positionList) {
            cmbFormEmployeePosition.getItems().add(p.getPosition_id()+": "+p.getPosition_name());
        }
    }

    private void showPaneFormEmployee(Boolean show) {
        paneFormEmployee.setVisible(show);
    }

    private void clearFormEmployee() {
        txtFormEmployeeID.setText("");
        txtFormEmployeeLastName.setText("");
        txtFormEmployeeLastName.setText("");
        txtFormEmployeeShares.setText("");
        cmbFormEmployeePosition.setValue("");
        chkFormEmployeeActive.setSelected(false);
    }

    private void saveEmployee() {
        String split[] = cmbFormEmployeePosition.getSelectionModel().getSelectedItem().toString().split(":");

        Employee employee = new Employee(txtFormEmployeeID.getText(), Integer.parseInt(split[0]), txtFormEmployeeFirstName.getText(), txtFormEmployeeLastName.getText(),
                pkrStartDate.getValue(), chkFormEmployeeActive.isSelected(), Integer.parseInt(txtFormEmployeeShares.getText()));

        Boolean employeeInsert = employee.insertEmployee();

        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());
        tblEmployees.getItems().removeAll();
        tblEmployees.setItems(employeeList);
        clearFormEmployee();
        showPaneFormEmployee(false);
    }

    private void showPaneEmployeeDetail(Boolean show) {
        paneEmployeeInfo.setVisible(false);
        paneEmployeeTable.setVisible(!show);
        paneEmployeeDetail.setVisible(show);
        btnEmployeeHome.setVisible(show);
    }
    private void fillPaneEmployeeDetail(Employee employee) {
        selectedEmployee.setEmployee(employee);
        lblEmployeeID.setText(selectedEmployee.getEmployee_id());
        lblEmployeeName.setText(selectedEmployee.getLast_name()+ ", "+selectedEmployee.getFirst_name());
        lblEmployeeStartDate.setText(selectedEmployee.getStart_date().toString());
        lblShares.setText(selectedEmployee.getShares_assigned().toString());
        for (Position p: positionList) {
            if (p.getPosition_id() == selectedEmployee.getPosition()) {
                lblEmployeePosition.setText(p.getPosition_id()+": "+p.getPosition_name());
            }
        }
        if(selectedEmployee.getIs_active()) {
            lblEmployeeIsActive.setText("Active");
        } else {
            lblEmployeeIsActive.setText("Inactive");
        }

        showPaneEmployeeDetail(true);
    }

    private void showEditFormEmployee(Boolean show) {
        if (show) fillFormEditEmployee();
        paneEmployeeInfo.setVisible(show);
    }
    private void fillFormEditEmployee() {
        txtEmployeeID.setText(selectedEmployee.getEmployee_id());
        txtEmployeeFirstName.setText(selectedEmployee.getFirst_name());
        txtEmployeeLastName.setText(selectedEmployee.getLast_name());
        txtEmployeeShares.setText(selectedEmployee.getShares_assigned().toString());
        chkEmployeeActive.setSelected(selectedEmployee.getIs_active());
        pkrEmployeeStartDate.setValue(selectedEmployee.getStart_date());

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
    }

    private void updateEmployee() {
        String split[] = cmbEmployeePosition.getSelectionModel().getSelectedItem().toString().split(":");

        Employee employee = new Employee(selectedEmployee.getEmployee_id(), Integer.parseInt(split[0]), txtEmployeeFirstName.getText(), txtEmployeeLastName.getText(),
                pkrEmployeeStartDate.getValue(), chkEmployeeActive.isSelected(), Integer.parseInt(txtEmployeeShares.getText()));

        employee.updateEmployee();
        fillPaneEmployeeDetail(employee);
        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());
        tblEmployees.getItems().removeAll();
        tblEmployees.setItems(employeeList);
        showEditFormEmployee(false);
    }
}
