package net.dahliasolutions.varcomp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import net.dahliasolutions.varcomp.connectors.*;
import net.dahliasolutions.varcomp.models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class VCNotesController implements Initializable {

    @FXML
    private Button btnNoteHome;
    @FXML
    private Button btnEditNote_save;
    @FXML
    private Button btnNewNote;
    @FXML
    private Pane paneNoteTable;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noteList = FXCollections.observableArrayList(NoteConnector.getNotes());
        metricList = FXCollections.observableArrayList(MetricConnector.getMetrics());
        employeeList = FXCollections.observableArrayList(EmployeeConnector.getEmployees());

        btnNoteHome.setOnAction(event -> navNotes("home"));
        btnNewNote.setOnAction(event -> fillNoteDetail(new Note()));
        btnEditNote_save.setOnAction(event -> saveNote_Click());

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

    /* Employee Detail */
        navNotes("home");
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
        btnNewNote.setVisible(false);
        btnNewNote.setManaged(false);


        switch (loc) {
            case "detail" -> {
                paneNoteDetail.setVisible(true);
                paneNoteDetail.setManaged(true);
                btnEditNote_save.setVisible(true);
                btnEditNote_save.setManaged(true);
                btnNoteHome.setVisible(true);
                btnNoteHome.setManaged(true);
            }
            default -> {
                paneNoteTable.setVisible(true);
                paneNoteTable.setManaged(true);
                btnNewNote.setVisible(true);
                btnNewNote.setManaged(true);
            }
        }
    }

    private void fillNoteTable() {
        noteList = FXCollections.observableArrayList(NoteConnector.getNotes());
        tblNotes.getItems().removeAll();
        tblNotes.setItems(noteList);
    }

    private void fillNoteDetail(Note note) {
        selectedNote = note;

        txtNoteID.setText(selectedNote.getNote_id().toString());
        txtNoteTitle.setText(selectedNote.getNote_title());
        txaNoteText.setText(selectedNote.getNote_text());

        cmbNoteMetric.getItems().removeAll();
        cmbNoteMetric.getItems().add("0: None");
        cmbNoteMetric.setValue("0: None");
        for (Metric m : metricList) {
            cmbNoteMetric.getItems().add(m.getMetric_id().toString()+": "+m.getMetric_label());
            if (m.getMetric_id().equals(selectedNote.getMetric_id())) {
                cmbNoteMetric.setValue(m.getMetric_id().toString()+": "+m.getMetric_label());
            }
        }

        cmbNoteEmployee.getItems().removeAll();
        cmbNoteEmployee.getItems().add("0: None");
        cmbNoteEmployee.setValue("0: None");
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
        String[] splitE = cmbNoteMetric.getSelectionModel().getSelectedItem().split(":");
        selectedNote.setNote_title(txtNoteTitle.getText());
        selectedNote.setNote_text(txaNoteText.getText());
        selectedNote.setMetric_id(Integer.parseInt(splitM[0]));
        selectedNote.setEmployee_id(splitE[0]);

        if (selectedNote.getNote_id().equals(0)) {
            selectedNote.insertNote();
        } else {
            selectedNote.updateNote();
        }

        fillNoteTable();
        navNotes("home");
    }

}
