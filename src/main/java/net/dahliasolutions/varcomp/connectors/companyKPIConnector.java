package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.CompanyKPI;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class companyKPIConnector {

    public static CompanyKPI getCompanyKPI(Integer companyKPIid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CompanyKPI companyKPI = new CompanyKPI();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLCOMPANYKPIS WHERE COMPANY_KPI_ID=?");
            preparedStatement.setInt(1, companyKPIid);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyKPIid = resultSet.getInt("company_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recMasterKPI = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
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

                    companyKPI = new CompanyKPI(recCompanyKPIid, recKPICode, recMasterKPI, recWeight, recMetricID,
                            recKPIClass, recF1Name, recF2Name, recF3Name, recF4Name, recF1Data, recF2Data, recF3Data,
                            recF4Data, recCalc, recGrade, recScore);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            companyKPI = new CompanyKPI();
        }

        return companyKPI.getCompanyKPI();
    }

    public static CompanyKPI insertCompanyKPI(CompanyKPI companyKPI) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CompanyKPI newCompanyKPI = new CompanyKPI();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLCOMPANYKPIS " +
                    "SET KPI_CODE=?, KPI_MASTER_ID=?, WEIGHT=?, METRIC_ID=?, KPI_CLASS=?, F1_NAME=?, F2_NAME=?, " +
                    "F3_NAME=?, F4_NAME=?, F1_DATA=?, F2_DATA=?, F3_DATA=?, F4_DATA=?, CALC_INSTRUCTIONS=?, " +
                    "KPI_GRADE=?, KPI_SCORE=?");
            preparedStatement.setString(1, companyKPI.getKpi_code());
            preparedStatement.setInt(2, companyKPI.getKpi_master_id());
            preparedStatement.setBigDecimal(3, companyKPI.getWeight());
            preparedStatement.setInt(4, companyKPI.getMetric_id());
            preparedStatement.setInt(5, companyKPI.getKpi_class());
            preparedStatement.setString(6, companyKPI.getF1_name());
            preparedStatement.setString(7, companyKPI.getF2_name());
            preparedStatement.setString(8, companyKPI.getF3_name());
            preparedStatement.setString(9, companyKPI.getF4_name());
            preparedStatement.setBigDecimal(10, companyKPI.getF1_data());
            preparedStatement.setBigDecimal(11, companyKPI.getF2_data());
            preparedStatement.setBigDecimal(12, companyKPI.getF3_data());
            preparedStatement.setBigDecimal(13, companyKPI.getF4_data());
            preparedStatement.setString(14, companyKPI.getCalc_instructions());
            preparedStatement.setBigDecimal(15, companyKPI.getKpi_grade());
            preparedStatement.setBigDecimal(16, companyKPI.getKpi_score());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLCOMPANYKPIS ORDER BY COMPANY_KPI_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyKPIid = resultSet.getInt("company_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recMasterKPI = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
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

                    newCompanyKPI = new CompanyKPI(recCompanyKPIid, recKPICode, recMasterKPI, recWeight, recMetricID,
                            recKPIClass, recF1Name, recF2Name, recF3Name, recF4Name, recF1Data, recF2Data, recF3Data,
                            recF4Data, recCalc, recGrade, recScore);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newCompanyKPI = new CompanyKPI();
        }

        return newCompanyKPI.getCompanyKPI();
    }

    public static ArrayList<CompanyKPI> getCompanyKPIs() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<CompanyKPI> companyKPIList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLCOMPANYKPIS");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyKPIid = resultSet.getInt("company_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recMasterKPI = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
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

                    companyKPIList.add(new CompanyKPI(recCompanyKPIid, recKPICode, recMasterKPI, recWeight, recMetricID,
                            recKPIClass, recF1Name, recF2Name, recF3Name, recF4Name, recF1Data, recF2Data, recF3Data,
                            recF4Data, recCalc, recGrade, recScore));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            companyKPIList.add(new CompanyKPI());
        }

        return companyKPIList;
    }

    public static ArrayList<CompanyKPI> getCompanyKPIs(Integer metricID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<CompanyKPI> companyKPIList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLCOMPANYKPIS WHERE METRIC_ID=?");
            preparedStatement.setInt(1, metricID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Metrics found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyKPIid = resultSet.getInt("company_kpi_id");
                    String recKPICode = resultSet.getString("kpi_code");
                    Integer recMasterKPI = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");
                    Integer recMetricID = resultSet.getInt("metric_id");
                    Integer recKPIClass = resultSet.getInt("kpi_class");
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

                    companyKPIList.add(new CompanyKPI(recCompanyKPIid, recKPICode, recMasterKPI, recWeight, recMetricID,
                            recKPIClass, recF1Name, recF2Name, recF3Name, recF4Name, recF1Data, recF2Data, recF3Data,
                            recF4Data, recCalc, recGrade, recScore));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            companyKPIList.add(new CompanyKPI());
        }

        return companyKPIList;
    }

    public static Boolean updateCompanyKPI(CompanyKPI companyKPI) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLCOMPANYKPIS " +
                    "SET KPI_CODE=?, KPI_MASTER_ID=?, WEIGHT=?, METRIC_ID=?, KPI_CLASS=?, F1_NAME=?, F2_NAME=?, " +
                    "F3_NAME=?, F4_NAME=?, F1_DATA=?, F2_DATA=?, F3_DATA=?, F4_DATA=?, CALC_INSTRUCTIONS=?, " +
                    "KPI_GRADE=?, KPI_SCORE=? WHERE COMPANY_KPI_ID=?");
            preparedStatement.setString(1, companyKPI.getKpi_code());
            preparedStatement.setInt(2, companyKPI.getKpi_master_id());
            preparedStatement.setBigDecimal(3, companyKPI.getWeight());
            preparedStatement.setInt(4, companyKPI.getMetric_id());
            preparedStatement.setInt(5, companyKPI.getKpi_class());
            preparedStatement.setString(6, companyKPI.getF1_name());
            preparedStatement.setString(7, companyKPI.getF2_name());
            preparedStatement.setString(8, companyKPI.getF3_name());
            preparedStatement.setString(9, companyKPI.getF4_name());
            preparedStatement.setBigDecimal(10, companyKPI.getF1_data());
            preparedStatement.setBigDecimal(11, companyKPI.getF2_data());
            preparedStatement.setBigDecimal(12, companyKPI.getF3_data());
            preparedStatement.setBigDecimal(13, companyKPI.getF4_data());
            preparedStatement.setString(14, companyKPI.getCalc_instructions());
            preparedStatement.setBigDecimal(15, companyKPI.getKpi_grade());
            preparedStatement.setBigDecimal(16, companyKPI.getKpi_score());
            preparedStatement.setInt(17, companyKPI.getCompany_kpi_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteCompanyKPI(Integer companyKPIid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLCOMPANYKPIS WHERE COMPANY_KPI_ID=?");
            preparedStatement.setInt(1, companyKPIid);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

}
