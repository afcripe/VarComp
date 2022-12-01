package net.dahliasolutions.varcomp;

import java.io.File;
import java.sql.*;

public class DBUtils {
    private static final String fs = System.getProperty("file.separator");
    private static String installDir = System.getProperty("user.home");

    private static String varCompDB = "jdbc:h2:~"+fs+"varcomp"+fs+"varcompdb";


    public static void init() {
        String homeDir = System.getProperty("user.home");
        String AppData;

        if(System.getProperty("os.name").startsWith("Win")) {
            //Windows Install Dir
            AppData = "AppData"+fs+"Local"+fs+"Programs"+fs+"VarComp";
        } else {
            //Unix Install Dir
            AppData = "varcomp";
        }

        varCompDB = "jdbc:h2:~"+fs+AppData+fs+"varcompdb";
        installDir = homeDir+fs+AppData;
        System.out.println(installDir);
    }

    public static String getDBLocation() {
        return varCompDB;
    }

    public static String getAppVersion() {
        return "2.0.1";
    }
     public static String getInstallDir() {
        return installDir;
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

    public static String getSQLDump() {
        Connection connection = null;
        PreparedStatement preparedStatement;
        File file = new File(getInstallDir()+fs+"varcomp_dump.sql");

        try {
            // remove old dumps
            if (file.exists()) file.delete();

            // dump script
            connection = DriverManager.getConnection(getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SCRIPT TO ?");
            preparedStatement.setString(1, file.getPath());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getPath();
    }

    public static void insertSQLDump(File file) {
        Connection connection = null;
        PreparedStatement preparedStatement;

        try {
            connection = DriverManager.getConnection(getDBLocation(), "sa", "password");
            // Drop current schema
            preparedStatement = connection.prepareStatement("DROP ALL OBJECTS");
            preparedStatement.execute();
            // run script from file
            preparedStatement = connection.prepareStatement("RUNSCRIPT FROM ?");
            preparedStatement.setString(1, file.getPath());
            preparedStatement.execute();
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
    }
}
