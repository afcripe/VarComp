package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.EmployeeKPI;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeKPIConnector {

    public static EmployeeKPI getEmployeeKPI(Integer employeeKPIid) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        EmployeeKPI employeeKPI = new EmployeeKPI();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS WHERE EMPLOYEE_KPI_ID=?");
            preparedStatement.setInt(1, employeeKPIid);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recScoreID = resultSet.getInt("score_id");
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
                    Integer recCalc = resultSet.getInt("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    employeeKPI = new EmployeeKPI(recEmployeeKPIid, recKPICode, recScoreID, recMasterKPI, recWeight, recMetricID,
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
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        EmployeeKPI newEmployeeKPI = new EmployeeKPI();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLEMPLOYEEKPIS " +
                    "SET KPI_CODE=?, SCORE_ID=?, KPI_MASTER_ID=?, WEIGHT=?, METRIC_ID=?, KPI_CLASS=?, COMPANY_KPI_ID=?, " +
                    "F1_NAME=?, F2_NAME=?, F3_NAME=?, F4_NAME=?, F1_DATA=?, F2_DATA=?, F3_DATA=?, F4_DATA=?, " +
                    "CALC_INSTRUCTIONS=?, KPI_GRADE=?, KPI_SCORE=?");
            preparedStatement.setString(1, employeeKPI.getKpi_code());
            preparedStatement.setInt(2, employeeKPI.getScore_id());
            preparedStatement.setInt(3, employeeKPI.getKpi_master_id());
            preparedStatement.setBigDecimal(4, employeeKPI.getWeight());
            preparedStatement.setInt(5, employeeKPI.getMetric_id());
            preparedStatement.setInt(6, employeeKPI.getKpi_class());
            preparedStatement.setInt(7, employeeKPI.getCompany_kpi_id());
            preparedStatement.setString(8, employeeKPI.getF1_name());
            preparedStatement.setString(9, employeeKPI.getF2_name());
            preparedStatement.setString(10, employeeKPI.getF3_name());
            preparedStatement.setString(11, employeeKPI.getF4_name());
            preparedStatement.setBigDecimal(12, employeeKPI.getF1_data());
            preparedStatement.setBigDecimal(13, employeeKPI.getF2_data());
            preparedStatement.setBigDecimal(14, employeeKPI.getF3_data());
            preparedStatement.setBigDecimal(15, employeeKPI.getF4_data());
            preparedStatement.setInt(16, employeeKPI.getCalc_instructions());
            preparedStatement.setBigDecimal(17, employeeKPI.getKpi_grade());
            preparedStatement.setBigDecimal(18, employeeKPI.getKpi_score());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS ORDER BY EMPLOYEE_KPI_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recScoreID = resultSet.getInt("score_id");
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
                    Integer recCalc = resultSet.getInt("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    newEmployeeKPI = new EmployeeKPI(recEmployeeKPIid, recKPICode, recScoreID, recMasterKPI, recWeight, recMetricID,
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
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<EmployeeKPI> employeeKPIList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recScoreID = resultSet.getInt("score_id");
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
                    Integer recCalc = resultSet.getInt("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    employeeKPIList.add(new EmployeeKPI(recEmployeeKPIid, recKPICode, recScoreID, recMasterKPI, recWeight, recMetricID,
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

    public static ArrayList<EmployeeKPI> getEmployeeKPIsByMetric(Integer metricID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<EmployeeKPI> employeeKPIList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS WHERE METRIC_ID=?");
            preparedStatement.setInt(1, metricID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recScoreID = resultSet.getInt("score_id");
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
                    Integer recCalc = resultSet.getInt("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    employeeKPIList.add(new EmployeeKPI(recEmployeeKPIid, recKPICode, recScoreID, recMasterKPI, recWeight, recMetricID,
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

    public static ArrayList<EmployeeKPI> getEmployeeKPIsByScore(Integer scoreID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<EmployeeKPI> employeeKPIList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEEKPIS WHERE SCORE_ID=?");
            preparedStatement.setInt(1, scoreID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recScoreID = resultSet.getInt("score_id");
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
                    Integer recCalc = resultSet.getInt("calc_instructions");
                    BigDecimal recGrade = resultSet.getBigDecimal("kpi_grade");
                    BigDecimal recScore = resultSet.getBigDecimal("kpi_score");

                    employeeKPIList.add(new EmployeeKPI(recEmployeeKPIid, recKPICode, recScoreID, recMasterKPI, recWeight, recMetricID,
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
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLEMPLOYEEKPIS " +
                    "SET KPI_CODE=?, SCORE_ID=?, KPI_MASTER_ID=?, WEIGHT=?, METRIC_ID=?, KPI_CLASS=?, COMPANY_KPI_ID=?, " +
                    "F1_NAME=?, F2_NAME=?, F3_NAME=?, F4_NAME=?, F1_DATA=?, F2_DATA=?, F3_DATA=?, F4_DATA=?, " +
                    "CALC_INSTRUCTIONS=?, KPI_GRADE=?, KPI_SCORE=? WHERE EMPLOYEE_KPI_ID=?");
            preparedStatement.setString(1, employeeKPI.getKpi_code());
            preparedStatement.setInt(2, employeeKPI.getScore_id());
            preparedStatement.setInt(3, employeeKPI.getKpi_master_id());
            preparedStatement.setBigDecimal(4, employeeKPI.getWeight());
            preparedStatement.setInt(5, employeeKPI.getMetric_id());
            preparedStatement.setInt(6, employeeKPI.getKpi_class());
            preparedStatement.setInt(7, employeeKPI.getCompany_kpi_id());
            preparedStatement.setString(8, employeeKPI.getF1_name());
            preparedStatement.setString(9, employeeKPI.getF2_name());
            preparedStatement.setString(10, employeeKPI.getF3_name());
            preparedStatement.setString(11, employeeKPI.getF4_name());
            preparedStatement.setBigDecimal(12, employeeKPI.getF1_data());
            preparedStatement.setBigDecimal(13, employeeKPI.getF2_data());
            preparedStatement.setBigDecimal(14, employeeKPI.getF3_data());
            preparedStatement.setBigDecimal(15, employeeKPI.getF4_data());
            preparedStatement.setInt(16, employeeKPI.getCalc_instructions());
            preparedStatement.setBigDecimal(17, employeeKPI.getKpi_grade());
            preparedStatement.setBigDecimal(18, employeeKPI.getKpi_score());
            preparedStatement.setInt(19, employeeKPI.getEmployee_kpi_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteEmployeeKPI(Integer employeeKPIid) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
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
