package net.dahliasolutions.varcomp;

import java.io.File;
import java.sql.*;

public class DBUtils {
    private static final String appVersion = "2.10.3";
    private static final int appDBVersion = 2;
    private static final int companyDBVersion = 4;
    private static final String fs = System.getProperty("file.separator");
    private static String installDir = System.getProperty("user.home");
    private static String companyDir = System.getProperty("user.home");
    private static String safeName = "varcomp";
    private static String AppData = "varcomp";
    private static String varCompDB = "jdbc:h2:~"+fs+"varcomp"+fs+"varcompdb";
    private static String appDB = "jdbc:h2:~"+fs+"varcomp"+fs+"appdb";
    private static final String helpDocs = "http://vcp.presidiumapp.com/desktopDocs/#";


    public static void init() {
        if(System.getProperty("os.name").startsWith("Win")) {
            //Windows Install Dir
            AppData = "AppData"+fs+"Local"+fs+"Programs"+fs+"VarComp";
        }

        setAppDBLocation();
        setInstallDir();
        setCompanyDir("");
    }

    public static String getAppDBLocation() {
        return appDB;
    }

    public static void setAppDBLocation() {
        appDB = "jdbc:h2:~"+fs+AppData+fs+"appdb";
    }

    public static String getDBLocation() {
        return varCompDB;
    }

    public static void setDBLocation(String name) {
        if (name.isEmpty()) {
            varCompDB = "jdbc:h2:~" + fs + AppData + fs + name;
        } else {
            varCompDB = "jdbc:h2:~" + fs + AppData + fs + name + fs + name;
        }
        safeName = name;
        setCompanyDir(name);
    }

    public static String getSafeName() { return safeName; }

    public static String getInstallDir() {
        return installDir;
    }

    public static void setInstallDir() {
        installDir = System.getProperty("user.home")+fs+AppData;
    }

    public static String getCompanyDir() {
        return companyDir;
    }

    public static void setCompanyDir(String name) {
        if (name.isEmpty()) {
            companyDir = System.getProperty("user.home") + fs + AppData;
        } else {
            companyDir = System.getProperty("user.home") + fs + AppData + fs + name;
        }
    }

    public static String getFS() {
        return fs;
    }

    public static String getAppVersion() {
        return appVersion;
    }
    public static int getAppDBVersion() {
        return appDBVersion;
    }
    public static int getCompanyDBVersion() { return companyDBVersion; }
    public static String getHelpDocs() { return helpDocs; }
    public static Boolean updateDBTable(double w, double h) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(getAppDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tbldbsettings SET app_width=?, app_height=?");
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
            connection = DriverManager.getConnection(getAppDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT " + col + " FROM tbldbsettings");
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
        File file = new File(getCompanyDir()+fs+getSafeName()+"_dump.sql");

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
