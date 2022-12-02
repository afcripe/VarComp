package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.Company;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class CompanyConnector {

    public static Company getCompany() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Company company = new Company();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblcompany LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company not found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyId = resultSet.getInt("company_id");
                    String recCompanyName = resultSet.getString("company_name");
                    Integer recSharesTotal = resultSet.getInt("shares_total");
                    Integer recSharesOutstanding = resultSet.getInt("shares_outstanding");
                    BigDecimal recFundingPercentage = resultSet.getBigDecimal("funding_percentage");
                    Integer recSharesIssuedYears = resultSet.getInt("shares_issued_years");
                    Integer recSharesIssuedAmount = resultSet.getInt("shares_issued_amount");
                    Boolean recCompanyLogoShow = resultSet.getBoolean("company_logo_show");

                    company = new Company(recCompanyId, recCompanyName, recSharesTotal, recSharesOutstanding,
                            recFundingPercentage, recSharesIssuedYears, recSharesIssuedAmount, recCompanyLogoShow);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            company = new Company();
        }

        return company.getCompany();
    }

    public static Company getCompany(Integer companyID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Company company = new Company();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblcompany WHERE company_id=?");
            preparedStatement.setInt(1, companyID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company not found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyId = resultSet.getInt("company_id");
                    String recCompanyName = resultSet.getString("company_name");
                    Integer recSharesTotal = resultSet.getInt("shares_total");
                    Integer recSharesOutstanding = resultSet.getInt("shares_outstanding");
                    BigDecimal recFundingPercentage = resultSet.getBigDecimal("funding_percentage");
                    Integer recSharesIssuedYears = resultSet.getInt("shares_issued_years");
                    Integer recSharesIssuedAmount = resultSet.getInt("shares_issued_amount");
                    Boolean recCompanyLogoShow = resultSet.getBoolean("company_logo_show");

                    company = new Company(recCompanyId, recCompanyName, recSharesTotal, recSharesOutstanding,
                            recFundingPercentage, recSharesIssuedYears, recSharesIssuedAmount, recCompanyLogoShow);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            company = new Company();
        }

        return company.getCompany();
    }

    public static ArrayList<Company> getCompanies() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<Company> companies = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblcompany");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No companies found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyId = resultSet.getInt("company_id");
                    String recCompanyName = resultSet.getString("company_name");
                    Integer recSharesTotal = resultSet.getInt("shares_total");
                    Integer recSharesOutstanding = resultSet.getInt("shares_outstanding");
                    BigDecimal recFundingPercentage = resultSet.getBigDecimal("funding_percentage");
                    Integer recSharesIssuedYears = resultSet.getInt("shares_issued_years");
                    Integer recSharesIssuedAmount = resultSet.getInt("shares_issued_amount");
                    Boolean recCompanyLogoShow = resultSet.getBoolean("company_logo_show");

                    companies.add(new Company(recCompanyId, recCompanyName, recSharesTotal, recSharesOutstanding,
                            recFundingPercentage, recSharesIssuedYears, recSharesIssuedAmount, recCompanyLogoShow));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            companies.add(new Company());
        }

        return companies;
    }

    public static Boolean updateCompany(Company company) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tblcompany SET COMPANY_NAME= ?," +
                    "SHARES_TOTAL=?, SHARES_OUTSTANDING=?, FUNDING_PERCENTAGE=?," +
                    "SHARES_ISSUED_AMOUNT=?, SHARES_ISSUED_YEARS=?, COMPANY_LOGO_SHOW=? WHERE company_id=?");
            preparedStatement.setString(1, company.getCompany_name());
            preparedStatement.setInt(2, company.getShares_total());
            preparedStatement.setInt(3, company.getShares_outstanding());
            preparedStatement.setBigDecimal(4, company.getFunding_percentage());
            preparedStatement.setInt(5, company.getShares_issued_amount());
            preparedStatement.setInt(6, company.getShares_issued_years());
            preparedStatement.setBoolean(7, company.getCompany_logo_show());
            preparedStatement.setInt(8, company.getCompany_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean updateNewCompany(Company company) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tblcompany SET company_id=?, COMPANY_NAME= ?," +
                    "SHARES_TOTAL=?, SHARES_OUTSTANDING=?, FUNDING_PERCENTAGE=?," +
                    "SHARES_ISSUED_AMOUNT=?, SHARES_ISSUED_YEARS=?, COMPANY_LOGO_SHOW=?");
            preparedStatement.setInt(1, company.getCompany_id());
            preparedStatement.setString(2, company.getCompany_name());
            preparedStatement.setInt(3, company.getShares_total());
            preparedStatement.setInt(4, company.getShares_outstanding());
            preparedStatement.setBigDecimal(5, company.getFunding_percentage());
            preparedStatement.setInt(6, company.getShares_issued_amount());
            preparedStatement.setInt(7, company.getShares_issued_years());
            preparedStatement.setBoolean(8, company.getCompany_logo_show());
            preparedStatement.executeUpdate();

            updateSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean updateCompanyID(Integer companyID) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tblcompany SET company_id=?");
            preparedStatement.setInt(1, companyID);
            preparedStatement.executeUpdate();

            updateSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }
}
