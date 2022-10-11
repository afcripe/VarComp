package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.models.PositionKPI;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class PositionKPIConnector {

    public static PositionKPI getPositionKPI(Integer itemID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PositionKPI positionKPI = new PositionKPI();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLPOSITIONKPIS WHERE item_id=?");
            preparedStatement.setInt(1, itemID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Position KPI not found.");
            } else {
                while (resultSet.next()) {
                    Integer recItemId = resultSet.getInt("item_id");
                    Integer recPositionID = resultSet.getInt("position_id");
                    Integer recKPIMasterID = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");

                    positionKPI = new PositionKPI(recItemId, recPositionID, recKPIMasterID, recWeight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            positionKPI = new PositionKPI();
        }

        return positionKPI.getPositionKPI();
    }

    public static PositionKPI insertPositionKPI(PositionKPI positionKPI) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PositionKPI newPositionKPI = new PositionKPI();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLPOSITIONKPIS " +
                    "SET POSITION_ID=?, KPI_MASTER_ID=?, WEIGHT=?");
            preparedStatement.setInt(1, positionKPI.getPosition_id());
            preparedStatement.setInt(2, positionKPI.getKpi_master_id());
            preparedStatement.setBigDecimal(3, positionKPI.getWeight());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLPOSITIONKPIS ORDER BY ITEM_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("KPI class not found.");
            } else {
                while (resultSet.next()) {
                    Integer recItemId = resultSet.getInt("item_id");
                    Integer recPositionID = resultSet.getInt("position_id");
                    Integer recKPIMasterID = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");

                    newPositionKPI = new PositionKPI(recItemId, recPositionID, recKPIMasterID, recWeight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newPositionKPI = new PositionKPI();
        }

        return newPositionKPI.getPositionKPI();
    }

    public static ArrayList<PositionKPI> getPositionKPI() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<PositionKPI> positionKPISList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLPOSITIONKPIS");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Position KPI found.");
            } else {
                while (resultSet.next()) {
                    Integer recItemId = resultSet.getInt("item_id");
                    Integer recPositionID = resultSet.getInt("position_id");
                    Integer recKPIMasterID = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");

                    positionKPISList.add(new PositionKPI(recItemId, recPositionID, recKPIMasterID, recWeight));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            positionKPISList.add(new PositionKPI());
        }

        return positionKPISList;
    }

    public static Boolean updatePositionKPI(PositionKPI positionKPI) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLPOSITIONKPIS " +
                    "SET POSITION_ID=?, KPI_MASTER_ID=?, WEIGHT=? WHERE ITEM_ID=?");
            preparedStatement.setInt(1, positionKPI.getPosition_id());
            preparedStatement.setInt(2, positionKPI.getKpi_master_id());
            preparedStatement.setBigDecimal(3, positionKPI.getWeight());
            preparedStatement.setInt(4, positionKPI.getItem_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deletePositionKPI(Integer itemID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLPOSITIONKPIS WHERE ITEM_ID=?");
            preparedStatement.setInt(1, itemID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

    public static ArrayList<PositionKPI> getPositionKPIsPosition(Integer positionID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<PositionKPI> positionKPISList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLPOSITIONKPIS WHERE POSITION_ID=?");
            preparedStatement.setInt(1, positionID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Position KPI found.");
            } else {
                while (resultSet.next()) {
                    Integer recItemId = resultSet.getInt("item_id");
                    Integer recPositionID = resultSet.getInt("position_id");
                    Integer recKPIMasterID = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");

                    positionKPISList.add(new PositionKPI(recItemId, recPositionID, recKPIMasterID, recWeight));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            positionKPISList.add(new PositionKPI());
        }

        return positionKPISList;
    }

    public static ArrayList<PositionKPI> getPositionKPIsByKPI(Integer kpiMasterID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<PositionKPI> positionKPISList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLPOSITIONKPIS WHERE KPI_MASTER_ID=?");
            preparedStatement.setInt(1, kpiMasterID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Position KPI found.");
            } else {
                while (resultSet.next()) {
                    Integer recItemId = resultSet.getInt("item_id");
                    Integer recPositionID = resultSet.getInt("position_id");
                    Integer recKPIMasterID = resultSet.getInt("kpi_master_id");
                    BigDecimal recWeight = resultSet.getBigDecimal("weight");

                    positionKPISList.add(new PositionKPI(recItemId, recPositionID, recKPIMasterID, recWeight));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            positionKPISList.add(new PositionKPI());
        }

        return positionKPISList;
    }

}
