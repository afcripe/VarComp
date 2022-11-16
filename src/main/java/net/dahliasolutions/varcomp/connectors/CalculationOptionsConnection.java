package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.CalculationOptions;
import net.dahliasolutions.varcomp.models.EmployeeScore;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class CalculationOptionsConnection {

    public static CalculationOptions getCalculationOption(Integer calculationID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CalculationOptions calculationOptions = new CalculationOptions();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLCALCULATIONOPTIONS WHERE CALCULATION_ID=?");
            preparedStatement.setInt(1, calculationID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Company not found.");
            } else {
                while (resultSet.next()) {
                    Integer recCalcId = resultSet.getInt("calculation_id");
                    String recCalcName = resultSet.getString("calculation_name");
                    String recCalcDescription = resultSet.getString("calculation_description");
                    String recCalcInstructions = resultSet.getString("calculation_instructions");

                    calculationOptions = new CalculationOptions(recCalcId, recCalcName, recCalcDescription, recCalcInstructions);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            calculationOptions = new CalculationOptions();
        }

        return calculationOptions.getCalculationOptions();
    }

    public static ArrayList<CalculationOptions> getCalculationOptions() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<CalculationOptions> calcOptions = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLCALCULATIONOPTIONS");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No companies found.");
            } else {
                while (resultSet.next()) {
                    Integer recCalcId = resultSet.getInt("calculation_id");
                    String recCalcName = resultSet.getString("calculation_name");
                    String recCalcDescription = resultSet.getString("calculation_description");
                    String recCalcInstructions = resultSet.getString("calculation_instructions");

                    calcOptions.add(new CalculationOptions(recCalcId, recCalcName, recCalcDescription, recCalcInstructions));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            calcOptions.add(new CalculationOptions());
        }

        return calcOptions;
    }

    public static CalculationOptions insertCalculationOption(CalculationOptions calcOption) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CalculationOptions calculationOptions = new CalculationOptions();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLCALCULATIONOPTIONS " +
                    "SET CALCULATION_NAME=?, CALCULATION_DESCRIPTION=?, CALCULATION_INSTRUCTIONS=?");
            preparedStatement.setString(1, calcOption.getCalculation_name());
            preparedStatement.setString(1, calcOption.getCalculation_description());
            preparedStatement.setString(1, calcOption.getCalculation_instructions());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLCALCULATIONOPTIONS ORDER BY CALCULATION_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Calculation ID not found.");
            } else {
                while (resultSet.next()) {
                    Integer recCalcId = resultSet.getInt("calculation_id");
                    String recCalcName = resultSet.getString("calculation_name");
                    String recCalcDescription = resultSet.getString("calculation_description");
                    String recCalcInstructions = resultSet.getString("calculation_instructions");

                    calculationOptions = new CalculationOptions(recCalcId, recCalcName, recCalcDescription, recCalcInstructions);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            calculationOptions = new CalculationOptions();
        }

        return calculationOptions;
    }

    public static Boolean updateCalculationOption(CalculationOptions calcOption) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLCALCULATIONOPTIONS " +
                    "SET CALCULATION_NAME=?, CALCULATION_DESCRIPTION=?, CALCULATION_INSTRUCTIONS=? WHERE CALCULATION_ID=?");
            preparedStatement.setString(1, calcOption.getCalculation_name());
            preparedStatement.setString(2, calcOption.getCalculation_description());
            preparedStatement.setString(3, calcOption.getCalculation_instructions());
            preparedStatement.setInt(4, calcOption.getCalculation_id());
            preparedStatement.executeUpdate();


            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteCalculationOption(Integer calculationID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLCALCULATIONOPTIONS WHERE CALCULATION_ID=?");
            preparedStatement.setInt(1, calculationID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }
}
