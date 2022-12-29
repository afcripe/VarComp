package net.dahliasolutions.varcomp;


import java.io.File;
import java.sql.*;

public class DBSetup {
    private static Connection connection;

    public static void initializeDB() {
        // check if db exists
        boolean goInit;
        File compDB = new File(DBUtils.getCompanyDir()+DBUtils.getFS()+DBUtils.getSafeName()+".mv.db");
        goInit = !compDB.exists();
        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            cleanupConnection();
            return;
        }
        if (goInit) {
            createData();
        } else {
            getDBVersion();
        }
        // cleanup the connection
        cleanupConnection();
    }

    public static void dropCompanyData(Integer companyID){
        // connect to db
        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            cleanupConnection();
            return;
        }

//        dropUsers();
//        dropDBSettings();
        dropCompany(companyID);
        dropEmployees();
        dropPositions();
        dropKPIMaster();
        dropClasses();
        dropPositionKPI();
        dropCompanyKPI();
        dropEmployeeKPI();
        dropEmployeeScores();
        dropMetrics();
        dropMetricsDetail();
        dropCalculationOptions();

        // reinitialize tables
        createData();

        // cleanup connection
        cleanupConnection();
    }

    private static void getDBVersion() {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement("SELECT db_version FROM tbldbsettings");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    int recDBVersion = resultSet.getInt("db_version");
                    while (recDBVersion < DBUtils.getCompanyDBVersion()) {
                        int uv = recDBVersion;
                        int nv = recDBVersion+1;
                        switch (nv) {
                            case 3 -> uv = updateTOV3();
                            case 4 -> uv = updateTOV4();
                        }
                        recDBVersion = uv;
                    }
                }
            } else {
                System.out.println("Could not get DB Version!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanupConnection();
        }
    }

    private static void createData() {
        initializeUsers();
        initializeUsersDefault();
        initializeCompany();
        initializeCompanyDefault();
        initializeDBSettings();
        initializeDBSettingsDefault();
        initializeEmployees();
        initializePositions();
        initializeKPIMaster();
        initializeClasses();
        initializeKPIClassesDefault();
        initializePositionKPI();
        initializeCompanyKPI();
        initializeEmployeeKPI();
        initializeEmployeeScores();
        initializeMetrics();
        initializeMetricsDetail();
        initializeCalculationOptions();
        initializeCalculationOptionsDefault();
    }

    private static void dropUsers() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLUSERS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void initializeUsers() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLUSERS " +
                    "(user_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "user_name VARCHAR(25), user_password VARCHAR(25), user_type VARCHAR(25))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeUsersDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM tblusers WHERE user_name='admin'");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData = connection.prepareStatement("INSERT INTO tblusers " +
                        "set user_name='admin', user_password='admin', user_type='admin'");
                setDefaultData.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeCompany();
        }
    }

    private static void dropCompany() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLCOMPANY");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropCompany(Integer companyID) {
        PreparedStatement preparedStatement;

        try {
//            preparedStatement = connection.prepareStatement("DROP TABLE TBLCOMPANY");
            preparedStatement = connection.prepareStatement("UPDATE tblcompany SET " +
                    "SHARES_TOTAL=0, SHARES_OUTSTANDING=0, FUNDING_PERCENTAGE=0.0000," +
                    "SHARES_ISSUED_AMOUNT=0, SHARES_ISSUED_YEARS=0 WHERE company_id=?");
            preparedStatement.setInt(1, companyID);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeCompany() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLCOMPANY " +
                    "(company_id INT, company_name VARCHAR(45), shares_total INT, shares_outstanding INT, " +
                    "funding_percentage NUMERIC(5,4), shares_issued_years INT, " +
                    "shares_issued_amount INT, company_logo_show BOOLEAN)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeCompanyDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM TBLCOMPANY WHERE COMPANY_ID=1");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData = connection.prepareStatement("INSERT INTO TBLCOMPANY " +
                        "set company_name='New Company', shares_total=0, shares_outstanding=0, " +
                        "funding_percentage=0.0000, shares_issued_years=0, shares_issued_amount=0, " +
                        "company_logo_show=false");
                setDefaultData.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeDBSettings();
        }
    }

    private static void dropDBSettings() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLDBSETTINGS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeDBSettings() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLDBSETTINGS " +
                    "(db_id INT, db_version INT)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeDBSettingsDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM TBLDBSETTINGS WHERE db_id=1");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData = connection.prepareStatement("INSERT INTO TBLDBSETTINGS set db_id=1,  db_version=2");
                setDefaultData.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropEmployees() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLEMPLOYEES");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeEmployees() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLEMPLOYEES " +
                    "(employee_id VARCHAR(5) PRIMARY KEY, " +
                    "position VARCHAR(25), first_name VARCHAR(45), last_name VARCHAR(45), " +
                    "start_date DATE, is_active VARCHAR(45), starting_shares INT, shares_assigned INT)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropPositions() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLPOSITIONS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializePositions() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLPOSITIONS " +
                    "(position_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "position VARCHAR(25), description VARCHAR(45), shares INT)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropKPIMaster() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLKPIMASTER");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeKPIMaster() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLKPIMASTER " +
                    "(kpi_master_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "kpi_code VARCHAR(25), description VARCHAR(255), kpi_class BIGINT, calc_instructions BIGINT, " +
                    "score_extraordinary NUMERIC(10,2), score_great NUMERIC(10,2), score_well NUMERIC(10,2), " +
                    "score_needs_improvement NUMERIC(10,2), score_poor NUMERIC(10,2), " +
                    "score_not_acceptable NUMERIC(10,2), f1_name VARCHAR(45), f2_name VARCHAR(45), " +
                    "f3_name VARCHAR(45), f4_name VARCHAR(45), reverse_scores BOOLEAN)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropClasses() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLKPICLASSES");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeClasses() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLKPICLASSES " +
                    "(kpi_class_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(25), description VARCHAR(255), auto_fill_employees BOOLEAN)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeKPIClassesDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData1;
        PreparedStatement setDefaultData2;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM TBLKPICLASSES");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData1 = connection.prepareStatement("INSERT INTO TBLKPICLASSES " +
                        "set name='Company', description='All assigned employees receives a company score.', " +
                        "auto_fill_employees=true");
                setDefaultData1.execute();

                setDefaultData2 = connection.prepareStatement("INSERT INTO TBLKPICLASSES " +
                        "set name='Employee', description='Each assigned employee receives independent score.', " +
                        "auto_fill_employees=false");
                setDefaultData2.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropPositionKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLPOSITIONKPIS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializePositionKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLPOSITIONKPIS " +
                    "(item_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "position_id BIGINT, kpi_master_id BIGINT, kpi_class_id BIGINT, weight NUMERIC(10,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropCompanyKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLCOMPANYKPIS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeCompanyKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLCOMPANYKPIS " +
                    "(company_kpi_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, kpi_code VARCHAR(25), " +
                    "kpi_master_id BIGINT, weight NUMERIC(10,2), metric_id BIGINT, kpi_class BIGINT, " +
                    "f1_name VARCHAR(45), f2_name VARCHAR(45), f3_name VARCHAR(45), f4_name VARCHAR(45), " +
                    "f1_data NUMERIC(18,2), f2_data NUMERIC(18,2), f3_data NUMERIC(18,2), f4_data NUMERIC(18,2), " +
                    "calc_instructions BIGINT, kpi_grade NUMERIC(10,2), kpi_score NUMERIC(10,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropEmployeeKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLEMPLOYEEKPIS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeEmployeeKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLEMPLOYEEKPIS " +
                    "(employee_kpi_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, score_id BIGINT, kpi_code VARCHAR(25), " +
                    "kpi_master_id BIGINT, weight NUMERIC(10,2), metric_id BIGINT, kpi_class BIGINT, company_kpi_id BIGINT, " +
                    "f1_name VARCHAR(45), f2_name VARCHAR(45), f3_name VARCHAR(45), f4_name VARCHAR(45), " +
                    "f1_data NUMERIC(18,2), f2_data NUMERIC(18,2), f3_data NUMERIC(18,2), f4_data NUMERIC(18,2), " +
                    "calc_instructions BIGINT, kpi_grade NUMERIC(10,2), kpi_score NUMERIC(10,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropEmployeeScores() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLEMPLOYEESCORES");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeEmployeeScores() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLEMPLOYEESCORES " +
                    "(score_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "employee_id VARCHAR(5), metric_id BIGINT, " +
                    "shares INT, grade NUMERIC(10,2), bonus NUMERIC(10,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropMetrics() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLMETRICS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeMetrics() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLMETRICS " +
                    "(metric_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, company_id BIGINT, " +
                    "metric_label VARCHAR(45), metric_earnings NUMERIC(18,2), metric_funding NUMERIC(18,2), " +
                    "metric_eps NUMERIC(18,2), metric_shares INT, metric_payout NUMERIC(18,2), " +
                    "metric_year INT, metric_period INT, locked BOOLEAN, lock_date DATE )");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropMetricsDetail() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLMETRICDETAILS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeMetricsDetail() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLMETRICDETAILS " +
                    "(metric_detail_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, metric_id BIGINT, " +
                    "detail_period INT, detail_budget NUMERIC(18,2), detail_actual NUMERIC(18,2), " +
                    "detail_earnings NUMERIC(18,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropCalculationOptions() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLCALCULATIONOPTIONS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeCalculationOptions() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLCALCULATIONOPTIONS " +
                    "(calculation_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "calculation_name VARCHAR(150), calculation_description VARCHAR(255), " +
                    "calculation_instructions VARCHAR(255))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeCalculationOptionsDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM TBLCALCULATIONOPTIONS");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData = connection.prepareStatement("INSERT INTO TBLCALCULATIONOPTIONS " +
                        "set calculation_name='NA', calculation_description='First Column is the Grade', " +
                        "calculation_instructions='ColA'");
                setDefaultData.execute();
                setDefaultData = connection.prepareStatement("INSERT INTO TBLCALCULATIONOPTIONS " +
                        "set calculation_name='AVG', calculation_description='Average All Columns That are Given a Title', " +
                        "calculation_instructions='(ColA+ColB+ColC+ColD)/100'");
                setDefaultData.execute();
                setDefaultData = connection.prepareStatement("INSERT INTO TBLCALCULATIONOPTIONS " +
                        "set calculation_name='AGE', calculation_description='Calculate Aged Receivables', " +
                        "calculation_instructions='ColA*0.1+ColB*0.3+ColC*0.3+ColD*0.3'");
                setDefaultData.execute();
                setDefaultData = connection.prepareStatement("INSERT INTO TBLCALCULATIONOPTIONS " +
                        "set calculation_name='DSO', calculation_description='Calculate Days of Sales Outstanding', " +
                        "calculation_instructions='ColA/ColB*365'");
                setDefaultData.execute();
                setDefaultData = connection.prepareStatement("INSERT INTO TBLCALCULATIONOPTIONS " +
                        "set calculation_name='OLE', calculation_description='Calculate Overall Labor Effectiveness', " +
                        "calculation_instructions='ColA/ColB*100'");
                setDefaultData.execute();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropNotes() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP TABLE TBLCALCULATIONOPTIONS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeNotes() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TBLNOTES " +
                    "(note_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "note_title VARCHAR(150), metric_id BIGINT, employee_id VARCHAR(5), " +
                    "note_text CHARACTER LARGE OBJECT)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int updateTOV3() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("ALTER TABLE tblpositionkpis " +
                    "ALTER COLUMN weight NUMERIC(10,4)");
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("UPDATE TBLDBSETTINGS " +
                    "SET DB_VERSION=3 WHERE DB_ID=1");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 3;
    }

    private static int updateTOV4() {
        PreparedStatement preparedStatement;

        initializeNotes();

        try {
            preparedStatement = connection.prepareStatement("UPDATE TBLDBSETTINGS " +
                    "SET DB_VERSION=4 WHERE DB_ID=1");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 4;
    }

    private static void cleanupConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}















