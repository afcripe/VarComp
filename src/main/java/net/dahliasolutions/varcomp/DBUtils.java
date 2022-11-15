package net.dahliasolutions.varcomp;

import java.sql.*;

public class DBUtils {

    private static String varCompDB = "jdbc:h2:~/varcomp/varcompdb";
//    private static String varCompDB = "jdbc:h2:tcp://localhost/Users/afcripe/Desktop/varcompdb";

    public static String getDBLocation() {
        return varCompDB;
//        return "jdbc:h2:tcp://localhost/Users/afcripe/Desktop/varcompdb";
    }

    public static Boolean updateDBTable(double w, double h) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

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
            return updateSuccess;
        }
    }
    public static double getStageSize(String col) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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
            return returnSize;
        }
    }
}
