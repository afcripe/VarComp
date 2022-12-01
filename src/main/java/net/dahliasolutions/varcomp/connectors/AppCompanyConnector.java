package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.AppCompany;
import net.dahliasolutions.varcomp.models.Company;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class AppCompanyConnector {

    public static AppCompany getCompany(Integer companyID) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        AppCompany appCompany = new AppCompany();

        try {
            connection = DriverManager.getConnection(DBUtils.getAppDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblappcompanies WHERE company_id=?");
            preparedStatement.setInt(1, companyID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company not found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyId = resultSet.getInt("company_id");
                    String recCompanyName = resultSet.getString("company_name");
                    String recDirName = resultSet.getString("dir_name");

                    appCompany = new AppCompany(recCompanyId, recCompanyName, recDirName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            appCompany = new AppCompany();
        }

        return appCompany.getAppCompany();
    }

    public static AppCompany getCompanyByName(String companyName) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        AppCompany appCompany = new AppCompany();

        try {
            connection = DriverManager.getConnection(DBUtils.getAppDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblappcompanies WHERE company_name=?");
            preparedStatement.setString(1, companyName);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company not found.");
            } else {
                while (resultSet.next()) {
                    Integer recCompanyId = resultSet.getInt("company_id");
                    String recCompanyName = resultSet.getString("company_name");
                    String recDirName = resultSet.getString("dir_name");

                    appCompany = new AppCompany(recCompanyId, recCompanyName, recDirName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            appCompany = new AppCompany();
        }

        return appCompany.getAppCompany();
    }

    public static ArrayList<AppCompany> getCompanies() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ArrayList<AppCompany> companies = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getAppDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblappcompanies");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No companies found.");
                companies.add(new AppCompany());
            } else {
                while (resultSet.next()) {
                    Integer recCompanyId = resultSet.getInt("company_id");
                    String recCompanyName = resultSet.getString("company_name");
                    String recDirName = resultSet.getString("dir_name");

                    companies.add(new AppCompany(recCompanyId, recCompanyName, recDirName));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            companies.add(new AppCompany());
        }

        return companies;
    }

    public static boolean insertCompany(AppCompany company) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getAppDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO tblappcompanies SET COMPANY_NAME= ?, DIR_NAME=?");
            preparedStatement.setString(1, company.getCompany_name());
            preparedStatement.setString(2, company.getDir_Name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Boolean updateCompany(AppCompany company) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getAppDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tblappcompanies SET COMPANY_NAME= ?, DIR_NAME=? WHERE company_id=?");
            preparedStatement.setString(1, company.getCompany_name());
            preparedStatement.setString(2, company.getDir_Name());
            preparedStatement.setInt(3, company.getCompany_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteCompany(Integer companyID) {
        Connection connection;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getAppDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM tblappcompanies WHERE company_id=?");
            preparedStatement.setInt(1, companyID);
            preparedStatement.executeUpdate();

            updateSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }
}
