package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.Position;

import java.sql.*;
import java.util.ArrayList;

public class PositionsConnector {

    public static Position getPosition(Integer positionID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Position position = new Position();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblpositions WHERE position_id=?");
            preparedStatement.setInt(1, positionID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Position not found.");
            } else {
                while (resultSet.next()) {
                    Integer recPositionId = resultSet.getInt("position_id");
                    String recPositionName = resultSet.getString("position");
                    String recPositionDescription = resultSet.getString("description");
                    Integer recPositionShares = resultSet.getInt("shares");

                    position = new Position(recPositionId, recPositionName, recPositionDescription, recPositionShares);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            position = new Position();
        }

        return position.getPosition();
    }

    public static Position insertPosition(Position position) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Position newPosition = new Position();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO tblpositions " +
                    "SET position=?, description=?, shares=?");
            preparedStatement.setString(1, position.getPosition_name());
            preparedStatement.setString(2, position.getPosition_description());
            preparedStatement.setInt(3, position.getPosition_shares());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM tblpositions ORDER BY POSITION_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Position not found.");
            } else {
                while (resultSet.next()) {
                    Integer recPositionId = resultSet.getInt("position_id");
                    String recPositionName = resultSet.getString("position");
                    String recPositionDescription = resultSet.getString("description");
                    Integer recPositionShares = resultSet.getInt("shares");

                    newPosition = new Position(recPositionId, recPositionName, recPositionDescription, recPositionShares);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newPosition = new Position();
        }

        return newPosition.getPosition();
    }

    public static ArrayList<Position> getPositions() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Position> positionList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblpositions");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No positions found.");
            } else {
                while (resultSet.next()) {
                    Integer recPositionId = resultSet.getInt("position_id");
                    String recPositionName = resultSet.getString("position");
                    String recPositionDescription = resultSet.getString("description");
                    Integer recPositionShares = resultSet.getInt("shares");

                    positionList.add(new Position(recPositionId, recPositionName, recPositionDescription, recPositionShares));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            positionList.add(new Position());
        }

        return positionList;
    }

    public static Boolean updatePosition(Position position) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tblpositions " +
                    "SET position=?, description=?, shares=? WHERE position_id=?");
            preparedStatement.setString(1, position.getPosition_name());
            preparedStatement.setString(2, position.getPosition_description());
            preparedStatement.setInt(3, position.getPosition_shares());
            preparedStatement.setInt(4, position.getPosition_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deletePosition(Integer positionID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM tblpositions WHERE position_id=?");
            preparedStatement.setInt(1, positionID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }
}
