package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.models.Employee;
import net.dahliasolutions.varcomp.models.PositionKPI;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeConnector {

    public static Employee getEmployee(Integer employeeID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Employee employee = new Employee();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEES WHERE employee_id=?");
            preparedStatement.setInt(1, employeeID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Employee not found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeId = resultSet.getInt("employee_id");
                    Integer recPositionID = resultSet.getInt("position");
                    String recFirstName = resultSet.getString("first_name");
                    String recLastName = resultSet.getString("last_name");
                    Date recStartDate = resultSet.getDate("start_date");
                    Boolean recIsActive = resultSet.getBoolean("is_active");
                    Integer recSharesAssigned = resultSet.getInt("shares_assigned");

                    employee = new Employee(recEmployeeId, recPositionID, recFirstName, recLastName, recStartDate,
                            recIsActive, recSharesAssigned);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employee = new Employee();
        }

        return employee.getEmployee();
    }

    public static Employee insertEmployee(Employee employee) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Employee newEmployee = new Employee();

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("INSERT INTO TBLEMPLOYEES " +
                    "SET EMPLOYEE_ID=?, POSITION=?, FIRST_NAME=?, LAST_NAME=?, START_DATE=?, IS_ACTIVE=?, SHARES_ASSIGNED=?");
            preparedStatement.setInt(1, employee.getEmployee_id());
            preparedStatement.setInt(2, employee.getPosition());
            preparedStatement.setString(3, employee.getFirst_name());
            preparedStatement.setString(4, employee.getLast_name());
            preparedStatement.setDate(5, (Date) employee.getStart_date());
            preparedStatement.setBoolean(6, employee.getIs_active());
            preparedStatement.setInt(7, employee.getShares_assigned());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEES ORDER BY employee_id DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Employee not found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeId = resultSet.getInt("employee_id");
                    Integer recPositionID = resultSet.getInt("position");
                    String recFirstName = resultSet.getString("first_name");
                    String recLastName = resultSet.getString("last_name");
                    Date recStartDate = resultSet.getDate("start_date");
                    Boolean recIsActive = resultSet.getBoolean("is_active");
                    Integer recSharesAssigned = resultSet.getInt("shares_assigned");

                    newEmployee = new Employee(recEmployeeId, recPositionID, recFirstName, recLastName, recStartDate,
                            recIsActive, recSharesAssigned);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newEmployee = new Employee();
        }

        return newEmployee.getEmployee();
    }

    public static ArrayList<Employee> getEmployees() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Employee> EmployeeList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEES");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Employees found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeId = resultSet.getInt("employee_id");
                    Integer recPositionID = resultSet.getInt("position");
                    String recFirstName = resultSet.getString("first_name");
                    String recLastName = resultSet.getString("last_name");
                    Date recStartDate = resultSet.getDate("start_date");
                    Boolean recIsActive = resultSet.getBoolean("is_active");
                    Integer recSharesAssigned = resultSet.getInt("shares_assigned");

                    EmployeeList.add(new Employee(recEmployeeId, recPositionID, recFirstName, recLastName, recStartDate,
                            recIsActive, recSharesAssigned));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            EmployeeList.add(new Employee());
        }

        return EmployeeList;
    }

    public static ArrayList<Employee> getEmployees(Boolean isActive) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Employee> EmployeeList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEES WHERE IS_ACTIVE=true");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Employees found.");
            } else {
                while (resultSet.next()) {
                    Integer recEmployeeId = resultSet.getInt("employee_id");
                    Integer recPositionID = resultSet.getInt("position");
                    String recFirstName = resultSet.getString("first_name");
                    String recLastName = resultSet.getString("last_name");
                    Date recStartDate = resultSet.getDate("start_date");
                    Boolean recIsActive = resultSet.getBoolean("is_active");
                    Integer recSharesAssigned = resultSet.getInt("shares_assigned");

                    EmployeeList.add(new Employee(recEmployeeId, recPositionID, recFirstName, recLastName, recStartDate,
                            recIsActive, recSharesAssigned));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            EmployeeList.add(new Employee());
        }

        return EmployeeList;
    }

    public static Boolean updateEmployee(Employee employee) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean updateSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLEMPLOYEES " +
                    "SET EMPLOYEE_ID=?, POSITION=?, FIRST_NAME=?, LAST_NAME=?, START_DATE=?, IS_ACTIVE=?, SHARES_ASSIGNED=?");
            preparedStatement.setInt(1, employee.getEmployee_id());
            preparedStatement.setInt(2, employee.getPosition());
            preparedStatement.setString(3, employee.getFirst_name());
            preparedStatement.setString(4, employee.getLast_name());
            preparedStatement.setDate(5, (Date) employee.getStart_date());
            preparedStatement.setBoolean(6, employee.getIs_active());
            preparedStatement.setInt(7, employee.getShares_assigned());
            preparedStatement.executeUpdate();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteEmployee(Integer employeeID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection("jdbc:h2:./varcompdb", "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLEMPLOYEES WHERE employee_id=?");
            preparedStatement.setInt(1, employeeID);
            preparedStatement.executeUpdate();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

}
