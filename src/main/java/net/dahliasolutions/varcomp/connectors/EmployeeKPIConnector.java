package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.models.EmployeeKPI;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeKPIConnector {

    public static EmployeeKPI getEmployeeKPI(Integer employeeKPIid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EmployeeKPI employeeKPI = new EmployeeKPI();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS WHERE EMPLOYEE_KPI_ID=?");
            preparedStatement.setInt(1, employeeKPIid);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recMasterKPI = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
                    Integer recCompanyKPIid = resultSet.getInt("company_kpi_id");
                    String recF1Name = resultSet.getString("f1_name");
                    String recF2Name = resultSet.getString("f2_name");
                    String recF3Name = resultSet.getString("f3_name");
                    String recF4Name = resultSet.getString("f4_name");
                    BigDecimal recF1Data = resultSet.getBigDecimal("f1_data");
                    BigDecimal recF2Data = resultSet.getBigDecimal("f2_data");
                    BigDecimal recF3Data = resultSet.getBigDecimal("f3_data");
                    BigDecimal recF4Data = resultSet.getBigDecimal("f4_data");
                    String recCalc = resultSet.getString("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    employeeKPI = new EmployeeKPI(recEmployeeKPIid, recKPICode, recMasterKPI, recWeight, recMetricID,
                            recKPIClass, recCompanyKPIid, recF1Name, recF2Name, recF3Name, recF4Name, recF1Data, recF2Data, recF3Data,
                            recF4Data, recCalc, recGrade, recScore);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeKPI = new EmployeeKPI();
        }

        return employeeKPI.getEmployeeKPI();
    }

    public static EmployeeKPI insertEmployeeKPI(EmployeeKPI employeeKPI) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EmployeeKPI newEmployeeKPI = new EmployeeKPI();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLEMPLOYEEKPIS " +
                    "SET KPI_CODE=?, KPI_MASTER_ID=?, WEIGHT=?, METRIC_ID=?, KPI_CLASS=?, COMPANY_KPI_ID=?, " +
                    "F1_NAME=?, F2_NAME=?, F3_NAME=?, F4_NAME=?, F1_DATA=?, F2_DATA=?, F3_DATA=?, F4_DATA=?, " +
                    "CALC_INSTRUCTIONS=?, KPI_GRADE=?, KPI_SCORE=?");
            preparedStatement.setString(1, employeeKPI.getKpi_code());
            preparedStatement.setInt(2, employeeKPI.getKpi_master_id());
            preparedStatement.setBigDecimal(3, employeeKPI.getWeight());
            preparedStatement.setInt(4, employeeKPI.getMetric_id());
            preparedStatement.setInt(5, employeeKPI.getKpi_class());
            preparedStatement.setInt(6, employeeKPI.getCompany_kpi_id());
            preparedStatement.setString(7, employeeKPI.getF1_name());
            preparedStatement.setString(8, employeeKPI.getF2_name());
            preparedStatement.setString(9, employeeKPI.getF3_name());
            preparedStatement.setString(10, employeeKPI.getF4_name());
            preparedStatement.setBigDecimal(11, employeeKPI.getF1_data());
            preparedStatement.setBigDecimal(12, employeeKPI.getF2_data());
            preparedStatement.setBigDecimal(13, employeeKPI.getF3_data());
            preparedStatement.setBigDecimal(14, employeeKPI.getF4_data());
            preparedStatement.setString(15, employeeKPI.getCalc_instructions());
            preparedStatement.setBigDecimal(16, employeeKPI.getKpi_grade());
            preparedStatement.setBigDecimal(17, employeeKPI.getKpi_score());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS ORDER BY EMPLOYEE_KPI_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recMasterKPI = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
                    Integer recCompanyKPIid = resultSet.getInt("company_kpi_id");
                    String recF1Name = resultSet.getString("f1_name");
                    String recF2Name = resultSet.getString("f2_name");
                    String recF3Name = resultSet.getString("f3_name");
                    String recF4Name = resultSet.getString("f4_name");
                    BigDecimal recF1Data = resultSet.getBigDecimal("f1_data");
                    BigDecimal recF2Data = resultSet.getBigDecimal("f2_data");
                    BigDecimal recF3Data = resultSet.getBigDecimal("f3_data");
                    BigDecimal recF4Data = resultSet.getBigDecimal("f4_data");
                    String recCalc = resultSet.getString("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    newEmployeeKPI = new EmployeeKPI(recEmployeeKPIid, recKPICode, recMasterKPI, recWeight, recMetricID,
                            recKPIClass, recCompanyKPIid, recF1Name, recF2Name, recF3Name, recF4Name, recF1Data, recF2Data, recF3Data,
                            recF4Data, recCalc, recGrade, recScore);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newEmployeeKPI = new EmployeeKPI();
        }

        return newEmployeeKPI.getEmployeeKPI();
    }

    public static ArrayList<EmployeeKPI> getEmployeeKPIs() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<EmployeeKPI> employeeKPIList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recMasterKPI = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
                    Integer recCompanyKPIid = resultSet.getInt("company_kpi_id");
                    String recF1Name = resultSet.getString("f1_name");
                    String recF2Name = resultSet.getString("f2_name");
                    String recF3Name = resultSet.getString("f3_name");
                    String recF4Name = resultSet.getString("f4_name");
                    BigDecimal recF1Data = resultSet.getBigDecimal("f1_data");
                    BigDecimal recF2Data = resultSet.getBigDecimal("f2_data");
                    BigDecimal recF3Data = resultSet.getBigDecimal("f3_data");
                    BigDecimal recF4Data = resultSet.getBigDecimal("f4_data");
                    String recCalc = resultSet.getString("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    employeeKPIList.add(new EmployeeKPI(recEmployeeKPIid, recKPICode, recMasterKPI, recWeight, recMetricID,
                            recKPIClass, recCompanyKPIid, recF1Name, recF2Name, recF3Name, recF4Name, recF1Data, recF2Data, recF3Data,
                            recF4Data, recCalc, recGrade, recScore));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeKPIList.add(new EmployeeKPI());
        }

        return employeeKPIList;
    }

    public static ArrayList<EmployeeKPI> getEmployeeKPIs(Integer metricID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<EmployeeKPI> employeeKPIList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS WHERE METRIC_ID=?");
            preparedStatement.setInt(1, metricID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recMasterKPI = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
                    Integer recCompanyKPIid = resultSet.getInt("company_kpi_id");
                    String recF1Name = resultSet.getString("f1_name");
                    String recF2Name = resultSet.getString("f2_name");
                    String recF3Name = resultSet.getString("f3_name");
                    String recF4Name = resultSet.getString("f4_name");
                    BigDecimal recF1Data = resultSet.getBigDecimal("f1_data");
                    BigDecimal recF2Data = resultSet.getBigDecimal("f2_data");
                    BigDecimal recF3Data = resultSet.getBigDecimal("f3_data");
                    BigDecimal recF4Data = resultSet.getBigDecimal("f4_data");
                    String recCalc = resultSet.getString("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    employeeKPIList.add(new EmployeeKPI(recEmployeeKPIid, recKPICode, recMasterKPI, recWeight, recMetricID,
                            recKPIClass, recCompanyKPIid, recF1Name, recF2Name, recF3Name, recF4Name, recF1Data, recF2Data, recF3Data,
                            recF4Data, recCalc, recGrade, recScore));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeKPIList.add(new EmployeeKPI());
        }

        return employeeKPIList;
    }

    public static Boolean updateEmployeeKPI(EmployeeKPI employeeKPI) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLEMPLOYEEKPIS " +
                    "SET KPI_CODE=?, KPI_MASTER_ID=?, WEIGHT=?, METRIC_ID=?, KPI_CLASS=?, COMPANY_KPI_ID=?, " +
                    "F1_NAME=?, F2_NAME=?, F3_NAME=?, F4_NAME=?, F1_DATA=?, F2_DATA=?, F3_DATA=?, F4_DATA=?, " +
                    "CALC_INSTRUCTIONS=?, KPI_GRADE=?, KPI_SCORE=? WHERE COMPANY_KPI_ID=?");
            preparedStatement.setString(1, employeeKPI.getKpi_code());
            preparedStatement.setInt(2, employeeKPI.getKpi_master_id());
            preparedStatement.setBigDecimal(3, employeeKPI.getWeight());
            preparedStatement.setInt(4, employeeKPI.getMetric_id());
            preparedStatement.setInt(5, employeeKPI.getKpi_class());
            preparedStatement.setInt(6, employeeKPI.getCompany_kpi_id());
            preparedStatement.setString(7, employeeKPI.getF1_name());
            preparedStatement.setString(8, employeeKPI.getF2_name());
            preparedStatement.setString(9, employeeKPI.getF3_name());
            preparedStatement.setString(10, employeeKPI.getF4_name());
            preparedStatement.setBigDecimal(11, employeeKPI.getF1_data());
            preparedStatement.setBigDecimal(12, employeeKPI.getF2_data());
            preparedStatement.setBigDecimal(13, employeeKPI.getF3_data());
            preparedStatement.setBigDecimal(14, employeeKPI.getF4_data());
            preparedStatement.setString(15, employeeKPI.getCalc_instructions());
            preparedStatement.setBigDecimal(16, employeeKPI.getKpi_grade());
            preparedStatement.setBigDecimal(17, employeeKPI.getKpi_score());
            preparedStatement.setInt(18, employeeKPI.getEmployee_kpi_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteEmployeeKPI(Integer employeeKPIid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLEMPLOYEEKPIS WHERE EMPLOYEE_KPI_ID=?");
            preparedStatement.setInt(1, employeeKPIid);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }
}