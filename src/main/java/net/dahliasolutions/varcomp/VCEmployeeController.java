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
    private Pane paneEmployeeTable;
    @FXML
    private Button bntNewEmployee;
    @FXML
    private TableView tblEmployees;
    @FXML
    private TableColumn<Employee, Integer> tbcEmployeeID;
    @FXML
    private TableColumn<Employee, String> tbcEmployeeName;
    @FXML
    private TableColumn<Employee, String> tbcEmployeePosition;
    @FXML
    private TableColumn<Employee, Integer> tbcEmployeeShares;
    @FXML
    private TableColumn<Employee, CheckBox> tbcEmployeeActive;
    @FXML
    private Pane paneFormEmployee;
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
    ObservableList<Employee> employeeList;
    ObservableList<Position> positionList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());
        positionList = FXCollections.observableArrayList(PositionsConnector.getPositions());

        bntNewEmployee.setOnAction(event -> setFormEmployee(new Employee()));

        tbcEmployeeID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employee_id"));
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
        tbcEmployeeActive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee, CheckBox>, ObservableValue<CheckBox>>() {
            @Override
            public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Employee, CheckBox> param) {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(param.getValue().getIs_active());
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });
        tblEmployees.setItems(employeeList);
        tblEmployees.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    Employee employee = (Employee) tblEmployees.getSelectionModel().getSelectedItem();
                    //setFormClassKPI(employee);
                }
            }
        });

        btnFormEmployee_cancel.setOnAction(event -> {
            clearFormEmployee();
            hidePaneFormEmployee();
        });
        btnFormEmployee_save.setOnAction(event -> saveEmployee());
    }

    private void setFormEmployee(Employee employee) {
        fillFormEmployee(employee);
        showPaneFormEmployee();
    }
    private void fillFormEmployee(Employee employee) {
        txtFormEmployeeLastName.setText(employee.getFirst_name());
        txtFormEmployeeLastName.setText(employee.getLast_name());
        txtFormEmployeeShares.setText(employee.getShares_assigned().toString());
        chkFormEmployeeActive.setSelected(employee.getIs_active());

        // create combobox items
        cmbFormEmployeePosition.getItems().clear();
        for (Position p: positionList) {
            cmbFormEmployeePosition.getItems().add(p.getPosition_id()+": "+p.getPosition_name());
        }
    }

    private void showPaneFormEmployee() {
        paneFormEmployee.setVisible(true);
    }

    private void hidePaneFormEmployee() {
        paneFormEmployee.setVisible(false);
    }

    private void clearFormEmployee() {
        txtFormEmployeeLastName.setText("");
        txtFormEmployeeLastName.setText("");
        txtFormEmployeeShares.setText("");
        cmbFormEmployeePosition.setValue("");
        chkFormEmployeeActive.setSelected(false);
    }

    private void saveEmployee() {
        String split[] = cmbFormEmployeePosition.getSelectionModel().getSelectedItem().toString().split(":");
        ZoneId tz = ZoneId.systemDefault();
        Date startDate = Date.from(pkrStartDate.getValue().atStartOfDay(tz).toInstant());

        Employee employee = new Employee(0, Integer.parseInt(split[0]), txtFormEmployeeLastName.getText(), txtFormEmployeeLastName.getText(),
                startDate, chkFormEmployeeActive.isSelected(), Integer.parseInt(txtFormEmployeeShares.getText()));

        Integer i = employee.insertEmployee();

        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());
        tblEmployees.getItems().removeAll();
        tblEmployees.setItems(employeeList);
        clearFormEmployee();
        hidePaneFormEmployee();
    }
}
