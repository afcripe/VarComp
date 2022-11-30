package net.dahliasolutions.varcomp;

import java.sql.*;

public class DBUtils {
    private static final String fs = System.getProperty("file.separator");
//    private static final String userHomeDir = System.getProperty("user.home");

    private static final String varCompDB = "jdbc:h2:~"+fs+"varcomp"+fs+"varcompdb";

    public static String getDBLocation() {
        return varCompDB;
    }

    public static String getAppVersion() {
        return "2.0.1";
    }

    public static Boolean updateDBTable(double w, double h) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tbldbsettings SET app_width=?, app_height=? WHERE DB_ID=1 ORDER BY DB_ID DESC LIMIT 1");
            preparedStatement.setDouble(1, w);
            preparedStatement.setDouble(2, h);
            preparedStatement.executeUpdate();

            updateSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return updateSuccess;
    }
    public static double getStageSize(String col) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        double returnSize = 750;

        try {
            connection = DriverManager.getConnection(getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT " + col + " FROM tbldbsettings  WHERE DB_ID=1");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    returnSize = resultSet.getDouble(col);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnSize;
    }
}
