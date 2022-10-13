package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.models.Metric;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class MetricConnector {

    public static Metric getMetric(Integer metricID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Metric metric = new Metric();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLMETRICS WHERE metric_id=?");
            preparedStatement.setInt(1, metricID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Metric not found.");
            } else {
                while (resultSet.next()) {
                    Integer recMetricId = resultSet.getInt("metric_id");
                    Integer recCompanyID = resultSet.getInt("company_id");
                    String recMetricLabel = resultSet.getString("metric_label");
                    BigDecimal recMetricEarnings = resultSet.getBigDecimal("metric_earnings");
                    BigDecimal recMetricFunding = resultSet.getBigDecimal("metric_funding");
                    BigDecimal recMetricEPS = resultSet.getBigDecimal("metric_eps");
                    Integer recMetricShares = resultSet.getInt("metric_shares");
                    BigDecimal recMetricPayout = resultSet.getBigDecimal("metric_payout");
                    Integer recMetricYear = resultSet.getInt("metric_year");
                    Integer recMetricPeriod = resultSet.getInt("metric_period");
                    Boolean recLocked = resultSet.getBoolean("locked");
                    Date recLockDate = resultSet.getDate("lock_date");

                    metric = new Metric(recMetricId, recCompanyID, recMetricLabel, recMetricEarnings, recMetricFunding,
                            recMetricEPS, recMetricShares, recMetricPayout, recMetricYear, recMetricPeriod, recLocked,
                            recLockDate.toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            metric = new Metric();
        }

        return metric.getMetric();
    }

    public static Metric insertMetric(Metric metric) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Metric newMetric = new Metric();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLMETRICS " +
                    "SET COMPANY_ID=?, METRIC_LABEL=?, METRIC_EARNINGS=?, METRIC_FUNDING=?, METRIC_EPS=?, METRIC_SHARES=?, " +
                    "METRIC_PAYOUT=?, METRIC_YEAR=?, METRIC_PERIOD=?, LOCKED=?, LOCK_DATE=?");
            preparedStatement.setInt(1, metric.getCompany_id());
            preparedStatement.setString(2, metric.getMetric_label());
            preparedStatement.setBigDecimal(3, metric.getMetric_earnings());
            preparedStatement.setBigDecimal(4, metric.getMetric_funding());
            preparedStatement.setBigDecimal(5, metric.getMetric_eps());
            preparedStatement.setInt(6, metric.getMetric_shares());
            preparedStatement.setBigDecimal(7, metric.getMetric_payout());
            preparedStatement.setInt(8, metric.getMetric_year());
            preparedStatement.setInt(9, metric.getMetric_period());
            preparedStatement.setBoolean(10, metric.getLocked());
            preparedStatement.setDate(11, Date.valueOf(metric.getLock_date()));
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLMETRICS ORDER BY METRIC_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Metric not found.");
            } else {
                while (resultSet.next()) {
                    Integer recMetricId = resultSet.getInt("metric_id");
                    Integer recCompanyID = resultSet.getInt("company_id");
                    String recMetricLabel = resultSet.getString("metric_label");
                    BigDecimal recMetricEarnings = resultSet.getBigDecimal("metric_earnings");
                    BigDecimal recMetricFunding = resultSet.getBigDecimal("metric_funding");
                    BigDecimal recMetricEPS = resultSet.getBigDecimal("metric_eps");
                    Integer recMetricShares = resultSet.getInt("metric_shares");
                    BigDecimal recMetricPayout = resultSet.getBigDecimal("metric_payout");
                    Integer recMetricYear = resultSet.getInt("metric_year");
                    Integer recMetricPeriod = resultSet.getInt("metric_period");
                    Boolean recLocked = resultSet.getBoolean("locked");
                    Date recLockDate = resultSet.getDate("lock_date");

                    newMetric = new Metric(recMetricId, recCompanyID, recMetricLabel, recMetricEarnings, recMetricFunding,
                            recMetricEPS, recMetricShares, recMetricPayout, recMetricYear, recMetricPeriod, recLocked,
                            recLockDate.toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newMetric = new Metric();
        }

        return newMetric.getMetric();
    }

    public static ArrayList<Metric> getMetrics() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Metric> metricList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLMETRICS");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recMetricId = resultSet.getInt("metric_id");
                    Integer recCompanyID = resultSet.getInt("company_id");
                    String recMetricLabel = resultSet.getString("metric_label");
                    BigDecimal recMetricEarnings = resultSet.getBigDecimal("metric_earnings");
                    BigDecimal recMetricFunding = resultSet.getBigDecimal("metric_funding");
                    BigDecimal recMetricEPS = resultSet.getBigDecimal("metric_eps");
                    Integer recMetricShares = resultSet.getInt("metric_shares");
                    BigDecimal recMetricPayout = resultSet.getBigDecimal("metric_payout");
                    Integer recMetricYear = resultSet.getInt("metric_year");
                    Integer recMetricPeriod = resultSet.getInt("metric_period");
                    Boolean recLocked = resultSet.getBoolean("locked");
                    Date recLockDate = resultSet.getDate("lock_date");

                    metricList.add(new Metric(recMetricId, recCompanyID, recMetricLabel, recMetricEarnings, recMetricFunding,
                            recMetricEPS, recMetricShares, recMetricPayout, recMetricYear, recMetricPeriod, recLocked,
                            recLockDate.toLocalDate()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            metricList.add(new Metric());
        }

        return metricList;
    }

    public static ArrayList<Metric> getMetrics(Boolean locked) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Metric> metricList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLMETRICS WHERE LOCKED=?");
            preparedStatement.setBoolean(1, locked);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recMetricId = resultSet.getInt("metric_id");
                    Integer recCompanyID = resultSet.getInt("company_id");
                    String recMetricLabel = resultSet.getString("metric_label");
                    BigDecimal recMetricEarnings = resultSet.getBigDecimal("metric_earnings");
                    BigDecimal recMetricFunding = resultSet.getBigDecimal("metric_funding");
                    BigDecimal recMetricEPS = resultSet.getBigDecimal("metric_eps");
                    Integer recMetricShares = resultSet.getInt("metric_shares");
                    BigDecimal recMetricPayout = resultSet.getBigDecimal("metric_payout");
                    Integer recMetricYear = resultSet.getInt("metric_year");
                    Integer recMetricPeriod = resultSet.getInt("metric_period");
                    Boolean recLocked = resultSet.getBoolean("locked");
                    Date recLockDate = resultSet.getDate("lock_date");

                    metricList.add(new Metric(recMetricId, recCompanyID, recMetricLabel, recMetricEarnings, recMetricFunding,
                            recMetricEPS, recMetricShares, recMetricPayout, recMetricYear, recMetricPeriod, recLocked,
                            recLockDate.toLocalDate()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            metricList.add(new Metric());
        }

        return metricList;
    }

    public static Boolean updateMetric(Metric metric) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLMETRICS " +
                    "SET COMPANY_ID=?, METRIC_LABEL=?, METRIC_EARNINGS=?, METRIC_FUNDING=?, METRIC_EPS=?, METRIC_SHARES=?, " +
                    "METRIC_PAYOUT=?, METRIC_YEAR=?, METRIC_PERIOD=?, LOCKED=?, LOCK_DATE=? WHERE METRIC_ID=?");
            preparedStatement.setInt(1, metric.getCompany_id());
            preparedStatement.setString(2, metric.getMetric_label());
            preparedStatement.setBigDecimal(3, metric.getMetric_earnings());
            preparedStatement.setBigDecimal(4, metric.getMetric_funding());
            preparedStatement.setBigDecimal(5, metric.getMetric_eps());
            preparedStatement.setInt(6, metric.getMetric_shares());
            preparedStatement.setBigDecimal(7, metric.getMetric_payout());
            preparedStatement.setInt(8, metric.getMetric_year());
            preparedStatement.setInt(9, metric.getMetric_period());
            preparedStatement.setBoolean(10, metric.getLocked());
            preparedStatement.setDate(11, Date.valueOf(metric.getLock_date()));
            preparedStatement.setInt(12, metric.getMetric_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteMetric(Integer metricID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLMETRICS WHERE METRIC_ID=?");
            preparedStatement.setInt(1, metricID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

}
