package net.dahliasolutions.varcomp;

import java.io.File;
import java.sql.*;

public class AppDBSetup {
    private static Connection appConnection;

    public static void initAppDB() {
        // check if db exists
        boolean goInit;
        File appDB = new File(DBUtils.getInstallDir()+DBUtils.getFS()+"appdb.mv.db");
        goInit = !appDB.exists();
        try {
            appConnection = DriverManager.getConnection(DBUtils.getAppDBLocation(), "sa", "password");

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        if (goInit) {
            initializeAppSettings();
        } else {
            getAppDB();
        }
    }

    private static void getAppDB() {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = appConnection.prepareStatement("SELECT db_version FROM tbldbsettings");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    int recDBVersion = resultSet.getInt("db_version");
                if (recDBVersion < DBUtils.getAppDBVersion()){
                    // ToDO - Update Database
                } else {
                    // ToDo - cleanup and move on
                }
            }
            } else {
                System.out.println("Could not get DB Version!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanupAppConnection();
        }
    }

    private static void initializeAppSettings() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = appConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tbldbsettings " +
                    "(db_id INT, db_version INT, app_version VARCHAR(25), " +
                    "app_width DOUBLE, app_height DOUBLE)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeAppSettingsDefault();
        }
    }

    private static void initializeAppSettingsDefault() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = appConnection.prepareStatement("INSERT INTO tbldbsettings " +
                    "set db_id=1,  db_version=?, app_version=?, app_width=750, app_height=750");
            preparedStatement.setInt(1, DBUtils.getAppDBVersion());
            preparedStatement.setString(2, DBUtils.getAppVersion());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initAppCompanies();
        }
    }

    private static void initAppCompanies() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = appConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblappcompanies " +
                    "(company_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "company_name VARCHAR(45), dir_name VARCHAR(255))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanupAppConnection();
        }
    }

    private static void cleanupAppConnection() {
        try {
            appConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}















