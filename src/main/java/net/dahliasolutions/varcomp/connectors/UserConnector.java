package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.models.User;

import java.sql.*;
import java.util.ArrayList;

public class UserConnector {

    public static User loginUser(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblusers WHERE user_name=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found.");
            } else {
                while (resultSet.next()) {
                    Integer recUserId = resultSet.getInt("user_id");
                    String recUsername = resultSet.getString("user_name");
                    String recPassword = resultSet.getString("user_password");
                    String recType = resultSet.getString("user_type");

                    if (recPassword.equals(password)) {
                        System.out.println("User of type: " + recType + " has logged in.");
                        user = new User(recUserId, recUsername, recPassword, recType);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            user = new User();
        }

        return user.getUser();
    }

    public static User insertUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User newUser = new User();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO tblusers " +
                    "SET user_name=?, user_password=?, user_type=?");
            preparedStatement.setString(1, user.getUser_name());
            preparedStatement.setString(2, user.getUser_password());
            preparedStatement.setString(3, user.getUser_type());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM tblusers ORDER BY USER_ID DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found.");
            } else {
                while (resultSet.next()) {
                    Integer recUserId = resultSet.getInt("user_id");
                    String recUsername = resultSet.getString("user_name");
                    String recPassword = resultSet.getString("user_password");
                    String recType = resultSet.getString("user_type");

                    newUser = new User(recUserId, recUsername, recPassword, recType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newUser = new User();
        }

        return newUser.getUser();
    }

    public static ArrayList<User> getUsers() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<User> userList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM tblusers");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No KPI classes found.");
            } else {
                while (resultSet.next()) {
                    Integer recUserId = resultSet.getInt("user_id");
                    String recUsername = resultSet.getString("user_name");
                    String recPassword = resultSet.getString("user_password");
                    String recType = resultSet.getString("user_type");

                    userList.add(new User(recUserId, recUsername, recPassword, recType));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            userList.add(new User());
        }

        return userList;
    }

    public static Boolean updateUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE tblusers " +
                    "SET user_name=?, user_password=?, user_type=? WHERE user_id=?");
            preparedStatement.setString(1, user.getUser_name());
            preparedStatement.setString(2, user.getUser_password());
            preparedStatement.setString(3, user.getUser_type());
            preparedStatement.setInt(4, user.getUser_id());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteUser(Integer userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        if (userID == 1) {
            System.out.println("Can't delete default user");
            return false;
        }

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM tblusers WHERE user_id=?");
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

}
