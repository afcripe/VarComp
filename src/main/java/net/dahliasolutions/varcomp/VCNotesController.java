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
import net.dahliasolutions.varcomp.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VCNotesController extends ViewFilteredController implements Initializable {

    @FXML
    private Button btnNoteHome;
    @FXML
    private Button btnPopOut;
    @FXML
    private Button btnReload;
    @FXML
    private Button btnClearFilter;
    @FXML
    private Button btnEditNote_save;
    @FXML
    private Button btnEditNote_delete;
    @FXML
    private Button btnNewNote;
    @FXML
    private Pane paneNoteTable;
    @FXML
    private TextField txtTitleFilter;
    @FXML
    private ComboBox<String> cmbMetricFilter;
    @FXML
    private ComboBox<String> cmbEmployeeFilter;
    @FXML
    private TableView<Note> tblNotes;
    @FXML
    private TableColumn<Note, Integer> tbcNoteID;
    @FXML
    private TableColumn<Note, String> tbcNoteTitle;
    @FXML
    private TableColumn<Note, String> tbcNoteText;

    @FXML
    private Pane paneNoteDetail;

    @FXML
    private TextField txtNoteID;
    @FXML
    private TextField txtNoteTitle;
    @FXML
    private TextArea txaNoteText;
    @FXML
    private ComboBox<String> cmbNoteMetric;
    @FXML
    private ComboBox<String> cmbNoteEmployee;

    private Note selectedNote = new Note();
    ObservableList<Note> noteList;
    ObservableList<Employee> employeeList;
    ObservableList<Metric> metricList;
    private String windowTitle;
    boolean allowPopOut = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noteList = FXCollections.observableArrayList(NoteConnector.getNotes());
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());

        btnNoteHome.setOnAction(event -> navNotes("home"));
        btnNewNote.setOnAction(event -> fillNoteDetail(new Note()));
        btnEditNote_save.setOnAction(event -> saveNote_Click());
        btnEditNote_delete.setOnAction(event -> deleteNote_Click());
        btnPopOut.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.ARROWS_ALT, "12px"));
        btnPopOut.setOnAction(event -> setPopout());
        btnReload.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REFRESH, "12px"));
        btnReload.setOnAction(event -> reloadWindow());
        btnClearFilter.setOnAction(event -> clearFilter());

    /* Note List */
        tbcNoteID.setCellValueFactory(new PropertyValueFactory<>("note_id"));
        tbcNoteTitle.setCellValueFactory(new PropertyValueFactory<>("note_title"));
        // tbcNoteText.setCellValueFactory(new PropertyValueFactory<>("note_text"));
        tbcNoteText.setCellValueFactory(param -> {
            String cellValue = param.getValue().getNote_text();

            if (cellValue.length()>150) {
                cellValue = cellValue.substring(1,148)+"...";
            }
            String[] split = cellValue.split("\\n");

            return new SimpleObjectProperty<>(split[0]+"...");
        });
        tblNotes.setItems(noteList);
        tblNotes.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                fillNoteDetail(tblNotes.getSelectionModel().getSelectedItem());
            }
        });


        cmbMetricFilter.getItems().clear();
        cmbMetricFilter.getItems().add("0: All Metrics");
        for (Metric m : metricList) {
            cmbMetricFilter.getItems().add(m.getMetric_id().toString()+": "+m.getMetric_label());
        }
        cmbMetricFilter.setValue("0: All Metrics");

        cmbEmployeeFilter.getItems().clear();
        cmbEmployeeFilter.getItems().add("ALL: All Employees");
        for (Employee e : employeeList) {
            cmbEmployeeFilter.getItems().add(e.getEmployee_id()+": "+e.getFull_name());
        }
        cmbEmployeeFilter.setValue("ALL: All Employees");


        txtTitleFilter.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> titleFilterFocusState(newValue));
        cmbMetricFilter.setOnAction(event -> applyFilter());
        cmbEmployeeFilter.setOnAction(event -> applyFilter());

        navNotes("home");
    }

    @Override
    public void init(String filterTitle, Integer filterMetric, String filterEmployee, String title) {
        allowPopOut = false;
        btnPopOut.setVisible(false);
        btnPopOut.setManaged(false);
        windowTitle = title;

        txtTitleFilter.setText(filterTitle);

        cmbMetricFilter.getItems().clear();
        cmbMetricFilter.getItems().add("0: All Metrics");
        cmbMetricFilter.setValue("0: All Metrics");
        for (Metric m : metricList) {
            cmbMetricFilter.getItems().add(m.getMetric_id().toString()+": "+m.getMetric_label());
            if (m.getMetric_id().equals(filterMetric)) {
                cmbMetricFilter.setValue(m.getMetric_id().toString()+": "+m.getMetric_label());
            }
        }

        cmbEmployeeFilter.getItems().clear();
        cmbEmployeeFilter.getItems().add("ALL: All Employees");
        cmbEmployeeFilter.setValue("ALL: All Employees");
        for (Employee e : employeeList) {
            cmbEmployeeFilter.getItems().add(e.getEmployee_id()+": "+e.getFull_name());
            if (e.getEmployee_id().equals(filterEmployee)) {
                cmbEmployeeFilter.setValue(e.getEmployee_id()+": "+e.getFull_name());
            }
        }

        applyFilter();
    }

    private void navNotes(String loc) {
        paneNoteTable.setVisible(false);
        paneNoteTable.setManaged(false);
        paneNoteDetail.setVisible(false);
        paneNoteDetail.setManaged(false);
        btnNoteHome.setVisible(false);
        btnNoteHome.setManaged(false);
        btnEditNote_save.setVisible(false);
        btnEditNote_save.setManaged(false);
        btnEditNote_delete.setVisible(false);
        btnEditNote_delete.setManaged(false);
        btnNewNote.setVisible(false);
        btnNewNote.setManaged(false);
        btnPopOut.setVisible(false);
        btnPopOut.setManaged(false);
        btnReload.setVisible(false);
        btnReload.setManaged(false);


        switch (loc) {
            case "detail" -> {
                paneNoteDetail.setVisible(true);
                paneNoteDetail.setManaged(true);
                btnEditNote_save.setVisible(true);
                btnEditNote_save.setManaged(true);
                btnEditNote_delete.setVisible(true);
                btnEditNote_delete.setManaged(true);
                btnNoteHome.setVisible(true);
                btnNoteHome.setManaged(true);
            }
            default -> {
                paneNoteTable.setVisible(true);
                paneNoteTable.setManaged(true);
                btnNewNote.setVisible(true);
                btnNewNote.setManaged(true);
                btnReload.setVisible(true);
                btnReload.setManaged(true);
                if (allowPopOut) btnPopOut.setVisible(true);
                if (allowPopOut) btnPopOut.setManaged(true);
                applyFilter();
            }
        }
    }

    private void fillNoteDetail(Note note) {
        selectedNote = note;

        if (note.getNote_id().equals(0)) {
            String[] splitM = cmbMetricFilter.getSelectionModel().getSelectedItem().split(":");
            String[] splitE = cmbEmployeeFilter.getSelectionModel().getSelectedItem().split(":");
            note.setNote_title(txtTitleFilter.getText());
            note.setMetric_id(Integer.parseInt(splitM[0]));
            note.setEmployee_id(splitE[0]);
        }

        txtNoteID.setText(selectedNote.getNote_id().toString());
        txtNoteTitle.setText(selectedNote.getNote_title());
        txaNoteText.setText(selectedNote.getNote_text());

        cmbNoteMetric.getItems().clear();
        cmbNoteMetric.getItems().add("0: None");
        cmbNoteMetric.setValue("0: None");
        for (Metric m : metricList) {
            cmbNoteMetric.getItems().add(m.getMetric_id().toString()+": "+m.getMetric_label());
            if (m.getMetric_id().equals(selectedNote.getMetric_id())) {
                cmbNoteMetric.setValue(m.getMetric_id().toString()+": "+m.getMetric_label());
            }
        }

        cmbNoteEmployee.getItems().clear();
        cmbNoteEmployee.getItems().add("ALL: None");
        cmbNoteEmployee.setValue("ALL: None");
        for (Employee e : employeeList) {
            cmbNoteEmployee.getItems().add(e.getEmployee_id()+": "+e.getFull_name());
            if (e.getEmployee_id().equals(selectedNote.getEmployee_id())) {
                cmbNoteEmployee.setValue(e.getEmployee_id()+": "+e.getFull_name());
            }
        }

        navNotes("detail");
    }

    private void saveNote_Click() {
        String[] splitM = cmbNoteMetric.getSelectionModel().getSelectedItem().split(":");
        String[] splitE = cmbNoteEmployee.getSelectionModel().getSelectedItem().split(":");
        selectedNote.setNote_title(txtNoteTitle.getText());
        selectedNote.setNote_text(txaNoteText.getText());
        selectedNote.setMetric_id(Integer.parseInt(splitM[0]));
        selectedNote.setEmployee_id(splitE[0]);

        if (selectedNote.getNote_id().equals(0)) {
            selectedNote.insertNote();
        } else {
            selectedNote.updateNote();
        }

        applyFilter();
        navNotes("home");
    }

    private void deleteNote_Click() {
        selectedNote.deleteNote();

        applyFilter();
        navNotes("home");
    }

    private void titleFilterFocusState(Boolean b) {
        if(!b) {
            applyFilter();
        }
    }

    private void applyFilter() {
        try {
            String[] splitM = cmbMetricFilter.getSelectionModel().getSelectedItem().split(":");
            String[] splitE = cmbEmployeeFilter.getSelectionModel().getSelectedItem().split(":");

            noteList = FXCollections.observableArrayList(NoteConnector.getNotes(txtTitleFilter.getText(), Integer.parseInt(splitM[0]), splitE[0]));
            tblNotes.getItems().removeAll();
            tblNotes.setItems(noteList);
        } catch (Exception e) {
            System.out.println("no filter applied!");
        }
    }

    private void clearFilter() {
        // reset the window title if popped out from metric or employee
        List<Window> windows = Window.getWindows();
        for (Window w : windows) {
            if (((Stage) w).getTitle().equals(windowTitle)) {
                ((Stage) w).setTitle("Notes");
                ((Stage) w).show();
                ((Stage) w).toFront();
                windowTitle = "Notes";
                return;
            }
        }
        cmbMetricFilter.setValue("0: All Metrics");
        cmbEmployeeFilter.setValue("ALL: All Employees");
        txtTitleFilter.setText("");
        reloadWindow();
    }

    private void reloadWindow() {
        String selectedMetric = cmbMetricFilter.getValue();
        String selectedEmployee = cmbEmployeeFilter.getValue();

        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());

        cmbMetricFilter.getItems().clear();;
        cmbMetricFilter.getItems().add("0: All Metrics");
        for (Metric m : metricList) {
            cmbMetricFilter.getItems().add(m.getMetric_id().toString()+": "+m.getMetric_label());
        }
        cmbMetricFilter.setValue(selectedMetric);

        cmbEmployeeFilter.getItems().clear();
        cmbEmployeeFilter.getItems().add("ALL: All Employees");
        for (Employee e : employeeList) {
            cmbEmployeeFilter.getItems().add(e.getEmployee_id()+": "+e.getFull_name());
        }
        cmbEmployeeFilter.setValue(selectedEmployee);

        applyFilter();
    }

    private void setPopout() {
        List<Window> windows = Window.getWindows();
        for (Window w : windows) {
            if (((Stage) w).getTitle().equals("Notes")) {
                System.out.println(w.sceneProperty().getName());
                ((Stage) w).show();
                ((Stage) w).toFront();
                return;
            }
        }

        String[] splitM = cmbMetricFilter.getSelectionModel().getSelectedItem().split(":");
        String[] splitE = cmbEmployeeFilter.getSelectionModel().getSelectedItem().split(":");
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(VarComp.class.getResource("vcnotes-view.fxml"));
            root = loader.load();
            ((ViewFilteredController)loader.getController()).init(txtTitleFilter.getText(), Integer.parseInt(splitM[0]), splitE[0], "Notes");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle("Notes");
        stage.show();
    }

}
