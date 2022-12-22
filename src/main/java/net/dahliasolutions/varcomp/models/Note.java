package net.dahliasolutions.varcomp.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import net.dahliasolutions.varcomp.connectors.NoteConnector;
import net.dahliasolutions.varcomp.connectors.UserConnector;

public class Note {
    private Integer note_id;
    private String note_title;
    private String note_text;
    private Integer metric_id;
    private String employee_id;

    public Note() {
        setNote_id(0);
        setNote_title("");
        setNote_text("");
        setMetric_id(0);
        setEmployee_id("");
    }

    public Note(Integer noteID, String noteTitle, String noteText, Integer metricID, String employeeID) {
        setNote_id(noteID);
        setNote_title(noteTitle);
        setNote_text(noteText);
        setMetric_id(metricID);
        setEmployee_id(employeeID);
    }


    public Integer getNote_id() {
        return note_id;
    }

    public void setNote_id(Integer note_id) {
        this.note_id = note_id;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_text() {
        return note_text;
    }

    public void setNote_text(String note_text) {
        this.note_text = note_text;
    }

    public Integer getMetric_id() {
        return metric_id;
    }

    public void setMetric_id(Integer metric_id) {
        this.metric_id = metric_id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public Note getNote() { return this; }

    public void insertNote() {
        NoteConnector.insertNote(this);
    }

    public void updateNote() {
        NoteConnector.updateNote(this);
    }

    public void deleteNote() {
        NoteConnector.deleteNote(this.getNote_id());
    }

}
