package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.models.CompanyKPI;
import net.dahliasolutions.varcomp.models.EmployeeScore;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeScoreConnector {

    public static EmployeeScore getEmployeeScore(Integer scoreID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EmployeeScore employeeScore = new EmployeeScore();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES WHERE SCORE_ID=?");
            preparedStatement.setInt(1, scoreID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    Integer recEmployeeID = resultSet.getInt("employee_id");
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recShares = resultSet.getInt("shares");
                    BigDecimal recScore = resultSet.getBigDecimal("score");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    employeeScore = new EmployeeScore(recSCoreID, recEmployeeID, recEmployeeKPIid,
                            recMetricID, recShares, recScore, recBonus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeScore = new EmployeeScore();
        }

        return employeeScore.getEmployeeScore();
    }

    public static EmployeeScore insertEmployeeScore(EmployeeScore employeeScore) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EmployeeScore newEmployeeScore = new EmployeeScore();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLEMPLOYEESCORES " +
                    "SET EMPLOYEE_ID=?, EMPLOYEE_KPI_ID=?, METRIC_ID=?, METRIC_ID=?, SHARES=?, " +
                    "SCORE=?, BONUS=?");
            preparedStatement.setInt(1, employeeScore.getEmployee_id());
            preparedStatement.setInt(2, employeeScore.getEmployee_kpi_id());
            preparedStatement.setInt(3, employeeScore.getMetric_id());
            preparedStatement.setInt(4, employeeScore.getShares());
            preparedStatement.setBigDecimal(5, employeeScore.getScore());
            preparedStatement.setBigDecimal(6, employeeScore.getBonus());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES ORDER BY SCORE_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    Integer recEmployeeID = resultSet.getInt("employee_id");
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recShares = resultSet.getInt("shares");
                    BigDecimal recScore = resultSet.getBigDecimal("score");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    newEmployeeScore = new EmployeeScore(recSCoreID, recEmployeeID, recEmployeeKPIid,
                            recMetricID, recShares, recScore, recBonus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newEmployeeScore = new EmployeeScore();
        }

        return newEmployeeScore.getEmployeeScore();
    }

    public static ArrayList<EmployeeScore> getEmployeeScores() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<EmployeeScore> employeeScoreList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    Integer recEmployeeID = resultSet.getInt("employee_id");
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recShares = resultSet.getInt("shares");
                    BigDecimal recScore = resultSet.getBigDecimal("score");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    employeeScoreList.add(new EmployeeScore(recSCoreID, recEmployeeID, recEmployeeKPIid,
                            recMetricID, recShares, recScore, recBonus));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeScoreList.add(new EmployeeScore());
        }

        return employeeScoreList;
    }

    public static ArrayList<EmployeeScore> getEmployeeScores(Integer metricID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<EmployeeScore> employeeScoreList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES WHERE METRIC_ID=?");
            preparedStatement.setInt(1, metricID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    Integer recEmployeeID = resultSet.getInt("employee_id");
                    Integer recEmployeeKPIid = resultSet.getInt("employee_kpi_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recShares = resultSet.getInt("shares");
                    BigDecimal recScore = resultSet.getBigDecimal("score");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    employeeScoreList.add(new EmployeeScore(recSCoreID, recEmployeeID, recEmployeeKPIid,
                            recMetricID, recShares, recScore, recBonus));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeScoreList.add(new EmployeeScore());
        }

        return employeeScoreList;
    }

    public static Boolean updateEmployeeScore(EmployeeScore employeeScore) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLEMPLOYEESCORES " +
                    "SET EMPLOYEE_ID=?, EMPLOYEE_KPI_ID=?, METRIC_ID=?, METRIC_ID=?, SHARES=?, " +
                    "SCORE=?, BONUS=? WHERE SCORE_ID=?");
            preparedStatement.setInt(1, employeeScore.getEmployee_id());
            preparedStatement.setInt(2, employeeScore.getEmployee_kpi_id());
            preparedStatement.setInt(3, employeeScore.getMetric_id());
            preparedStatement.setInt(4, employeeScore.getShares());
            preparedStatement.setBigDecimal(5, employeeScore.getScore());
            preparedStatement.setBigDecimal(6, employeeScore.getBonus());
            preparedStatement.setInt(7, employeeScore.getScore_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteEmployeeScore(Integer scoreID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLEMPLOYEESCORES WHERE SCORE_ID=?");
            preparedStatement.setInt(1, scoreID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

}
