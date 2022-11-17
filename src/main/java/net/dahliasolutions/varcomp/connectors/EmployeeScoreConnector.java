package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.EmployeeScore;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeScoreConnector {

    public static EmployeeScore getEmployeeScore(Integer scoreID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        EmployeeScore employeeScore = new EmployeeScore();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES WHERE SCORE_ID=?");
            preparedStatement.setInt(1, scoreID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    String recEmployeeID = resultSet.getString("employee_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recShares = resultSet.getInt("shares");
                    BigDecimal recGrade = resultSet.getBigDecimal("grade");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    employeeScore = new EmployeeScore(recSCoreID, recEmployeeID,
                            recMetricID, recShares, recGrade, recBonus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeScore = new EmployeeScore();
        }

        return employeeScore.getEmployeeScore();
    }

    public static EmployeeScore getEmployeeScoreByMetric(Integer metricID, String employeeID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        EmployeeScore employeeScore = new EmployeeScore();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES " +
                    "WHERE METRIC_ID=? AND EMPLOYEE_ID=?");
            preparedStatement.setInt(1, metricID);
            preparedStatement.setString(2, employeeID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    String recEmployeeID = resultSet.getString("employee_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recGrade = resultSet.getInt("grade");
                    BigDecimal recScore = resultSet.getBigDecimal("grade");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    employeeScore = new EmployeeScore(recSCoreID, recEmployeeID,
                            recMetricID, recGrade, recScore, recBonus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeScore = new EmployeeScore();
        }

        return employeeScore.getEmployeeScore();
    }

    public static EmployeeScore insertEmployeeScore(EmployeeScore employeeScore) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        EmployeeScore newEmployeeScore = new EmployeeScore();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLEMPLOYEESCORES " +
                    "SET EMPLOYEE_ID=?, METRIC_ID=?, SHARES=?, " +
                    "GRADE=?, BONUS=?");
            preparedStatement.setString(1, employeeScore.getEmployee_id());
            preparedStatement.setInt(2, employeeScore.getMetric_id());
            preparedStatement.setInt(3, employeeScore.getShares());
            preparedStatement.setBigDecimal(4, employeeScore.getGrade());
            preparedStatement.setBigDecimal(5, employeeScore.getBonus());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES ORDER BY SCORE_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    String recEmployeeID = resultSet.getString("employee_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recShares = resultSet.getInt("shares");
                    BigDecimal recGrade = resultSet.getBigDecimal("grade");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    newEmployeeScore = new EmployeeScore(recSCoreID, recEmployeeID,
                            recMetricID, recShares, recGrade, recBonus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newEmployeeScore = new EmployeeScore();
        }

        return newEmployeeScore.getEmployeeScore();
    }

    public static ArrayList<EmployeeScore> getEmployeeScores() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<EmployeeScore> employeeScoreList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    String recEmployeeID = resultSet.getString("employee_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recShares = resultSet.getInt("shares");
                    BigDecimal recGrade = resultSet.getBigDecimal("grade");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    employeeScoreList.add(new EmployeeScore(recSCoreID, recEmployeeID,
                            recMetricID, recShares, recGrade, recBonus));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeScoreList.add(new EmployeeScore());
        }

        return employeeScoreList;
    }

    public static ArrayList<EmployeeScore> getEmployeeScores(Integer metricID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<EmployeeScore> employeeScoreList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEESCORES WHERE METRIC_ID=?");
            preparedStatement.setInt(1, metricID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recSCoreID = resultSet.getInt("score_id");
                    String recEmployeeID = resultSet.getString("employee_id");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recShares = resultSet.getInt("shares");
                    BigDecimal recGrade = resultSet.getBigDecimal("grade");
                    BigDecimal recBonus = resultSet.getBigDecimal("bonus");

                    employeeScoreList.add(new EmployeeScore(recSCoreID, recEmployeeID,
                            recMetricID, recShares, recGrade, recBonus));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeScoreList.add(new EmployeeScore());
        }

        return employeeScoreList;
    }

    public static Boolean updateEmployeeScore(EmployeeScore employeeScore) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLEMPLOYEESCORES " +
                    "SET EMPLOYEE_ID=?, METRIC_ID=?, SHARES=?, " +
                    "GRADE=?, BONUS=? WHERE SCORE_ID=?");
            preparedStatement.setString(1, employeeScore.getEmployee_id());
            preparedStatement.setInt(2, employeeScore.getMetric_id());
            preparedStatement.setInt(3, employeeScore.getShares());
            preparedStatement.setBigDecimal(4, employeeScore.getGrade());
            preparedStatement.setBigDecimal(5, employeeScore.getBonus());
            preparedStatement.setInt(6, employeeScore.getScore_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteEmployeeScore(Integer scoreID) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
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
