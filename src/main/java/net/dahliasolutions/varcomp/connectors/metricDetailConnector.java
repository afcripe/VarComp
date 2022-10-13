package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.models.Metric;
import net.dahliasolutions.varcomp.models.MetricDetail;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class metricDetailConnector {

    public static MetricDetail getMetricDetail(Integer detailID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        MetricDetail metricDetail = new MetricDetail();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLMETRICDETAILS WHERE METRIC_DETAIL_ID=?");
            preparedStatement.setInt(1, detailID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Metric Detail not found.");
            } else {
                while (resultSet.next()) {
                    Integer recDetailID = resultSet.getInt("metric_detail_id");
                    Integer recMetricId = resultSet.getInt("metric_id");
                    Integer recPeriod = resultSet.getInt("detail_period");
                    BigDecimal recBudget = resultSet.getBigDecimal("detail_budget");
                    BigDecimal recActual = resultSet.getBigDecimal("detail_actual");
                    BigDecimal recEarnings = resultSet.getBigDecimal("detail_earnings");

                    metricDetail = new MetricDetail(recDetailID, recMetricId, recPeriod, recBudget, recActual, recEarnings);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            metricDetail = new MetricDetail();
        }

        return metricDetail.getMetricDetail();
    }

    public static MetricDetail insertMetric(MetricDetail metricDetail) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        MetricDetail newMetricDetail = new MetricDetail();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLMETRICDETAILS " +
                    "SET METRIC_ID=?, DETAIL_PERIOD=?, DETAIL_BUDGET=?, DETAIL_ACTUAL=?, DETAIL_EARNINGS=?");
            preparedStatement.setInt(1, metricDetail.getMetric_id());
            preparedStatement.setInt(2, metricDetail.getDetail_period());
            preparedStatement.setBigDecimal(3, metricDetail.getDetail_budget());
            preparedStatement.setBigDecimal(4, metricDetail.getDetail_actual());
            preparedStatement.setBigDecimal(5, metricDetail.getDetail_earnings());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLMETRICDETAILS ORDER BY METRIC_DETAIL_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Metric Detail not found.");
            } else {
                while (resultSet.next()) {
                    Integer recDetailID = resultSet.getInt("metric_detail_id");
                    Integer recMetricId = resultSet.getInt("metric_id");
                    Integer recPeriod = resultSet.getInt("detail_period");
                    BigDecimal recBudget = resultSet.getBigDecimal("detail_budget");
                    BigDecimal recActual = resultSet.getBigDecimal("detail_actual");
                    BigDecimal recEarnings = resultSet.getBigDecimal("detail_earnings");

                    metricDetail = new MetricDetail(recDetailID, recMetricId, recPeriod, recBudget, recActual, recEarnings);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newMetricDetail = new MetricDetail();
        }

        return newMetricDetail.getMetricDetail();
    }

    public static ArrayList<MetricDetail> getMetricDetails() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<MetricDetail> metricDetailList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLMETRICDETAILS");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recDetailID = resultSet.getInt("metric_detail_id");
                    Integer recMetricId = resultSet.getInt("metric_id");
                    Integer recPeriod = resultSet.getInt("detail_period");
                    BigDecimal recBudget = resultSet.getBigDecimal("detail_budget");
                    BigDecimal recActual = resultSet.getBigDecimal("detail_actual");
                    BigDecimal recEarnings = resultSet.getBigDecimal("detail_earnings");

                    metricDetailList.add(new MetricDetail(recDetailID, recMetricId, recPeriod, recBudget, recActual, recEarnings));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            metricDetailList.add(new MetricDetail());
        }

        return metricDetailList;
    }

    public static ArrayList<MetricDetail> getMetricDetails(Integer metricID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<MetricDetail> metricDetailList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLMETRICS WHERE METRIC_ID=?");
            preparedStatement.setInt(1, metricID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recDetailID = resultSet.getInt("metric_detail_id");
                    Integer recMetricId = resultSet.getInt("metric_id");
                    Integer recPeriod = resultSet.getInt("detail_period");
                    BigDecimal recBudget = resultSet.getBigDecimal("detail_budget");
                    BigDecimal recActual = resultSet.getBigDecimal("detail_actual");
                    BigDecimal recEarnings = resultSet.getBigDecimal("detail_earnings");

                    metricDetailList.add(new MetricDetail(recDetailID, recMetricId, recPeriod, recBudget, recActual, recEarnings));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            metricDetailList.add(new MetricDetail());
        }

        return metricDetailList;
    }

    public static Boolean updateMetricDetail(MetricDetail metricDetail) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLMETRICDETAILS " +
                    "SET METRIC_ID=?, DETAIL_PERIOD=?, DETAIL_BUDGET=?, DETAIL_ACTUAL=?, DETAIL_EARNINGS=? WHERE METRIC_DETAIL_ID=?");
            preparedStatement.setInt(1, metricDetail.getMetric_id());
            preparedStatement.setInt(2, metricDetail.getDetail_period());
            preparedStatement.setBigDecimal(3, metricDetail.getDetail_budget());
            preparedStatement.setBigDecimal(4, metricDetail.getDetail_actual());
            preparedStatement.setBigDecimal(5, metricDetail.getDetail_earnings());
            preparedStatement.setInt(6, metricDetail.getMetric_detail_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteMetricDetail(Integer detailID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLMETRICDETAILS WHERE METRIC_DETAIL_ID=?");
            preparedStatement.setInt(1, detailID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

}
