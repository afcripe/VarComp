package net.dahliasolutions.varcomp;

import java.sql.*;

public class DBSetup {
    private static Connection connection;

    public static void initializeDB() {
        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        dropAllObjects();
        initializeUsers();
    }

    public static void dropAllObjects() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("DROP ALL OBJECTS");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeUsers();
        }
    }

    public static void initializeUsers() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblusers " +
                    "(user_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "user_name VARCHAR(25), user_password VARCHAR(25), user_type VARCHAR(25))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeUsersDefault();
        }
    }

    public static void initializeUsersDefault() {
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

    public static void initializeCompany() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblcompany " +
                    "(company_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "company_name VARCHAR(45), shares_total INT, shares_outstanding INT, " +
                    "funding_percentage NUMERIC(5,4), shares_issued_years INT, " +
                    "shares_issued_amount INT, company_logo_show BOOLEAN)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeCompanyDefault();
        }
    }

    public static void initializeCompanyDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM tblcompany WHERE company_id=1");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData = connection.prepareStatement("INSERT INTO tblcompany " +
                        "set company_name='My Company', shares_total=0, shares_outstanding=0, " +
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

    public static void initializeDBSettings() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tbldbsettings " +
                    "(db_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "db_version INT, db_version_label VARCHAR(25), " +
                    "app_version INT, app_version_label VARCHAR(25), " +
                    "app_width DOUBLE, app_height DOUBLE)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeDBSettingsDefault();
        }
    }

    public static void initializeDBSettingsDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM tbldbsettings");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData = connection.prepareStatement("INSERT INTO tbldbsettings " +
                        "set db_version=2100, db_version_label='2.1.0', app_version=2100, " +
                        "app_version_label='2.1.0', app_width=600, app_height=400");
                setDefaultData.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeEmployees();
        }
    }

    public static void initializeEmployees() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblemployees " +
                    "(employee_id VARCHAR(5) PRIMARY KEY, " +
                    "position VARCHAR(25), first_name VARCHAR(45), last_name VARCHAR(45), " +
                    "start_date DATE, is_active VARCHAR(45), starting_shares INT, shares_assigned INT)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializePositions();
        }
    }

    public static void initializePositions() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblpositions " +
                    "(position_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "position VARCHAR(25), description VARCHAR(45), shares INT)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeKPIMaster();
        }
    }

    public static void initializeKPIMaster() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblkpimaster " +
                    "(kpi_master_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "kpi_code VARCHAR(25), description VARCHAR(255), kpi_class BIGINT, calc_instructions BIGINT, " +
                    "score_extraordinary NUMERIC(10,2), score_great NUMERIC(10,2), score_well NUMERIC(10,2), " +
                    "score_needs_improvement NUMERIC(10,2), score_poor NUMERIC(10,2), " +
                    "score_not_acceptable NUMERIC(10,2), f1_name VARCHAR(45), f2_name VARCHAR(45), " +
                    "f3_name VARCHAR(45), f4_name VARCHAR(45), reverse_scores BOOLEAN)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeClasses();
        }
    }

    public static void initializeClasses() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblkpiclasses " +
                    "(kpi_class_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(25), description VARCHAR(255), auto_fill_employees BOOLEAN)");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeKPIClassesDefault();
        }
    }

    public static void initializeKPIClassesDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData1;
        PreparedStatement setDefaultData2;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM tblkpiclasses");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData1 = connection.prepareStatement("INSERT INTO tblkpiclasses " +
                        "set name='Company', description='All assigned employees receives a company score.', " +
                        "auto_fill_employees=true");
                setDefaultData1.execute();

                setDefaultData2 = connection.prepareStatement("INSERT INTO tblkpiclasses " +
                        "set name='Employee', description='Each assigned employee receives independent score.', " +
                        "auto_fill_employees=false");
                setDefaultData2.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializePositionKPI();
        }
    }

    public static void initializePositionKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblpositionkpis " +
                    "(item_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "position_id BIGINT, kpi_master_id BIGINT, kpi_class_id BIGINT, weight NUMERIC(10,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeCompanyKPI();
        }
    }

    public static void initializeCompanyKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblcompanykpis " +
                    "(company_kpi_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, kpi_code VARCHAR(25), " +
                    "kpi_master_id BIGINT, weight NUMERIC(10,2), metric_id BIGINT, kpi_class BIGINT, " +
                    "f1_name VARCHAR(45), f2_name VARCHAR(45), f3_name VARCHAR(45), f4_name VARCHAR(45), " +
                    "f1_data NUMERIC(18,2), f2_data NUMERIC(18,2), f3_data NUMERIC(18,2), f4_data NUMERIC(18,2), " +
                    "calc_instructions BIGINT, kpi_grade NUMERIC(10,2), kpi_score NUMERIC(10,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeEmployeeKPI();
        }
    }

    public static void initializeEmployeeKPI() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblemployeekpis " +
                    "(employee_kpi_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, score_id BIGINT, kpi_code VARCHAR(25), " +
                    "kpi_master_id BIGINT, weight NUMERIC(10,2), metric_id BIGINT, kpi_class BIGINT, company_kpi_id BIGINT, " +
                    "f1_name VARCHAR(45), f2_name VARCHAR(45), f3_name VARCHAR(45), f4_name VARCHAR(45), " +
                    "f1_data NUMERIC(18,2), f2_data NUMERIC(18,2), f3_data NUMERIC(18,2), f4_data NUMERIC(18,2), " +
                    "calc_instructions BIGINT, kpi_grade NUMERIC(10,2), kpi_score NUMERIC(10,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeEmployeeScores();
        }
    }

    public static void initializeEmployeeScores() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblemployeescores " +
                    "(score_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "employee_id VARCHAR(5), metric_id BIGINT, " +
                    "shares INT, grade NUMERIC(10,2), bonus NUMERIC(10,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeMetrics();
        }
    }

    public static void initializeMetrics() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblmetrics " +
                    "(metric_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, company_id BIGINT, " +
                    "metric_label VARCHAR(45), metric_earnings NUMERIC(18,2), metric_funding NUMERIC(18,2), " +
                    "metric_eps NUMERIC(18,2), metric_shares INT, metric_payout NUMERIC(18,2), " +
                    "metric_year INT, metric_period INT, locked BOOLEAN, lock_date DATE )");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeMetricsDetail();
        }
    }

    public static void initializeMetricsDetail() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblmetricdetails " +
                    "(metric_detail_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, metric_id BIGINT, " +
                    "detail_period INT, detail_budget NUMERIC(18,2), detail_actual NUMERIC(18,2), " +
                    "detail_earnings NUMERIC(18,2))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeCalculationOptions();
        }
    }

    public static void initializeCalculationOptions() {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tblcalculationoptions " +
                    "(calculation_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "calculation_name VARCHAR(150), calculation_description VARCHAR(255), " +
                    "calculation_instructions VARCHAR(255))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            initializeCalculationOptionsDefault();
        }
    }

    public static void initializeCalculationOptionsDefault() {
        PreparedStatement getDefaultData;
        PreparedStatement setDefaultData;
        ResultSet resultSet;

        try {
            getDefaultData = connection.prepareStatement("SELECT * FROM tblcalculationoptions");
            resultSet = getDefaultData.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                setDefaultData = connection.prepareStatement("INSERT INTO tblcalculationoptions " +
                        "set calculation_name='NA', calculation_description='First Column is the Grade', " +
                        "calculation_instructions='ColA'");
                setDefaultData.execute();
                setDefaultData = connection.prepareStatement("INSERT INTO tblcalculationoptions " +
                        "set calculation_name='AVG', calculation_description='Average All Columns', " +
                        "calculation_instructions='(ColA+ColB+ColC+ColD)/100'");
                setDefaultData.execute();
                setDefaultData = connection.prepareStatement("INSERT INTO tblcalculationoptions " +
                        "set calculation_name='AGE', calculation_description='Calculate Aged Receivables', " +
                        "calculation_instructions='ColA*0.1+ColB*0.3+ColC*0.3+ColD*0.3'");
                setDefaultData.execute();
                setDefaultData = connection.prepareStatement("INSERT INTO tblcalculationoptions " +
                        "set calculation_name='DSO', calculation_description='Calculate Days of Sales Outstanding', " +
                        "calculation_instructions='ColA/ColB*365'");
                setDefaultData.execute();
                setDefaultData = connection.prepareStatement("INSERT INTO tblcalculationoptions " +
                        "set calculation_name='OLE', calculation_description='Calculate Overall Labor Effectiveness', " +
                        "calculation_instructions='ColA/ColB*100'");
                setDefaultData.execute();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanupConnection();
        }
    }

    public static void cleanupConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}















