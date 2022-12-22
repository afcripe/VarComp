package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.EmployeeUtils;
import net.dahliasolutions.varcomp.models.Employee;
import net.dahliasolutions.varcomp.models.Note;

import java.sql.*;
import java.util.ArrayList;

public class NoteConnector {

    public static Note getNote(Integer noteID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Note note = new Note();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLNOTES WHERE note_id=?");
            preparedStatement.setInt(1, noteID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Note not found.");
            } else {
                while (resultSet.next()) {
                    Integer recNoteID = resultSet.getInt("note_id");
                    String recNoteTitle = resultSet.getString("note_title");
                    String recNoteText = resultSet.getString("note_text");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    String recEmployeeId = resultSet.getString("employee_id");

                    note = new Note(recNoteID, recNoteTitle, recNoteText, recMetricID, recEmployeeId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            note = new Note();
        }

        return note.getNote();
    }

    public static ArrayList<Note> getNotes() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<Note> noteList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLNOTES");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No notes found.");
            } else {
                while (resultSet.next()) {
                    Integer recNoteID = resultSet.getInt("note_id");
                    String recNoteTitle = resultSet.getString("note_title");
                    String recNoteText = resultSet.getString("note_text");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    String recEmployeeId = resultSet.getString("employee_id");

                    noteList.add(new Note(recNoteID, recNoteTitle, recNoteText, recMetricID, recEmployeeId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            noteList.add(new Note());
        }

        return noteList;
    }

    public static ArrayList<Note> getNotes(String noteTitle, int metricID, String employeeID) {
        Connection connection;
        PreparedStatement preparedStatement;
        String preparedString;
        ResultSet resultSet;
        ArrayList<Note> noteList = new ArrayList<>();

        // Set WHERE Clause
        String wc = "0=0";
        if (metricID != 0) {
            wc = wc+" AND METRIC_ID="+metricID;
        }
        if (!employeeID.isEmpty() && !employeeID.equalsIgnoreCase("ALL")) {
            wc = wc+" AND EMPLOYEE_ID = '"+employeeID+"'";
        }
        if (!noteTitle.isEmpty()) {
            wc = wc+" AND NOTE_TITLE ILIKE '%"+noteTitle+"%'";
        }

        preparedString = "SELECT * FROM TBLNOTES WHERE "+wc;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement(preparedString);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No notes found.");
            } else {
                while (resultSet.next()) {
                    Integer recNoteID = resultSet.getInt("note_id");
                    String recNoteTitle = resultSet.getString("note_title");
                    String recNoteText = resultSet.getString("note_text");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    String recEmployeeId = resultSet.getString("employee_id");

                    noteList.add(new Note(recNoteID, recNoteTitle, recNoteText, recMetricID, recEmployeeId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            noteList.add(new Note());
        }

        return noteList;
    }

    public static Integer countMetricNotes(Integer metricID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int counter = 0;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLNOTES WHERE metric_id=?");
            preparedStatement.setInt(1, metricID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Note not found.");
            } else {
                while (resultSet.next()) {
                    counter =+1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return counter;
    }

    public static Integer countEmployeeNotes(String employeeID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int counter = 0;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLNOTES WHERE EMPLOYEE_ID=?");
            preparedStatement.setString(1, employeeID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Note not found.");
            } else {
                while (resultSet.next()) {
                    counter =+1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return counter;
    }

    public static Boolean insertNote(Note note) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        boolean insertSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            // check for existing ID
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLNOTES WHERE note_id=?");
            preparedStatement.setInt(1, note.getNote_id());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Note with id already exists");
                return false;
            }
            // insert new employee
            preparedStatement = connection.prepareStatement("INSERT INTO TBLNOTES " +
                    "SET note_title=?, note_text=?, metric_id=?, employee_id=?");
            preparedStatement.setString(1, note.getNote_title());
            preparedStatement.setString(2, note.getNote_text());
            preparedStatement.setInt(3, note.getMetric_id());
            preparedStatement.setString(4, note.getEmployee_id());
            preparedStatement.executeUpdate();

            insertSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insertSuccess;
    }

    public static Boolean updateNote(Note note) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLNOTES " +
                    "SET note_title=?, note_text=?, metric_id=?, employee_id=? WHERE note_id=?");
            preparedStatement.setString(1, note.getNote_title());
            preparedStatement.setString(2, note.getNote_text());
            preparedStatement.setInt(3, note.getMetric_id());
            preparedStatement.setString(4, note.getEmployee_id());
            preparedStatement.setInt(5, note.getNote_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteNote(Integer noteID) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLNOTES WHERE note_id=?");
            preparedStatement.setInt(1, noteID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

}
