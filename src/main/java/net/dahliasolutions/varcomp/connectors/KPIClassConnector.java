package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.KPIClass;

import java.sql.*;
import java.util.ArrayList;

public class KPIClassConnector {

    public static KPIClass getKPIClass(Integer classID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        KPIClass kpiClass = new KPIClass();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblkpiclasses WHERE kpi_class_id=?");
            preparedStatement.setInt(1, classID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("KPI class not found.");
            } else {
                while (resultSet.next()) {
                    Integer recClassId = resultSet.getInt("kpi_class_id");
                    String recClassName = resultSet.getString("name");
                    String recClassDescription = resultSet.getString("description");
                    Boolean recClassAutoFill = resultSet.getBoolean("auto_fill_employees");

                    kpiClass = new KPIClass(recClassId, recClassName, recClassDescription, recClassAutoFill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            kpiClass = new KPIClass();
        }

        return kpiClass.getKPIClass();
    }

    public static KPIClass insertKPIClass(KPIClass kpiClass) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        KPIClass newKPIClass = new KPIClass();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO tblkpiclasses " +
                    "SET name=?, description=?, auto_fill_employees=?");
            preparedStatement.setString(1, kpiClass.getName());
            preparedStatement.setString(2, kpiClass.getDescription());
            preparedStatement.setBoolean(3, kpiClass.getAuto_fill_employees());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM tblkpiclasses ORDER BY KPI_CLASS_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("KPI class not found.");
            } else {
                while (resultSet.next()) {
                    Integer recClassId = resultSet.getInt("kpi_class_id");
                    String recClassName = resultSet.getString("name");
                    String recClassDescription = resultSet.getString("description");
                    Boolean recClassAutoFill = resultSet.getBoolean("auto_fill_employees");

                    newKPIClass = new KPIClass(recClassId, recClassName, recClassDescription, recClassAutoFill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newKPIClass = new KPIClass();
        }

        return newKPIClass.getKPIClass();
    }

    public static ArrayList<KPIClass> getKPIClasses() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<KPIClass> kpiClassList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblkpiclasses");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No KPI classes found.");
            } else {
                while (resultSet.next()) {
                    Integer recClassId = resultSet.getInt("kpi_class_id");
                    String recClassName = resultSet.getString("name");
                    String recClassDescription = resultSet.getString("description");
                    Boolean recClassAutoFill = resultSet.getBoolean("auto_fill_employees");

                    kpiClassList.add(new KPIClass(recClassId, recClassName, recClassDescription, recClassAutoFill));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            kpiClassList.add(new KPIClass());
        }

        return kpiClassList;
    }

    public static Boolean updateKPIClass(KPIClass kpiClass) {
        if (kpiClass.getKpi_class_id() < 2) return false;

        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tblkpiclasses " +
                    "SET name=?, description=?, auto_fill_employees=? WHERE kpi_class_id=?");
            preparedStatement.setString(1, kpiClass.getName());
            preparedStatement.setString(2, kpiClass.getDescription());
            preparedStatement.setBoolean(3, kpiClass.getAuto_fill_employees());
            preparedStatement.setInt(4, kpiClass.getKpi_class_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteKPIClass(Integer classID) {
        if (classID < 2) return false;

        Connection connection;
        PreparedStatement preparedStatement;
        boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM tblkpiclasses WHERE kpi_class_id=?");
            preparedStatement.setInt(1, classID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }
}
