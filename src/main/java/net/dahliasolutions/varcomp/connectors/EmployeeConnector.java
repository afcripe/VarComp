package net.dahliasolutions.varcomp.connectors;

import net.dahliasolutions.varcomp.DBUtils;
import net.dahliasolutions.varcomp.EmployeeUtils;
import net.dahliasolutions.varcomp.models.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeConnector {

    public static Employee getEmployee(String employeeID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Employee employee = new Employee();

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEES WHERE employee_id=?");
            preparedStatement.setString(1, employeeID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Employee not found.");
            } else {
                while (resultSet.next()) {
                    String recEmployeeId = resultSet.getString("employee_id");
                    Integer recPositionID = resultSet.getInt("position");
                    String recFirstName = resultSet.getString("first_name");
                    String recLastName = resultSet.getString("last_name");
                    Date recStartDate = resultSet.getDate("start_date");
                    Boolean recIsActive = resultSet.getBoolean("is_active");
                    Integer recStartingShares = resultSet.getInt("starting_shares");
                    Integer recSharesAssigned = resultSet.getInt("shares_assigned");

                    employee = new Employee(recEmployeeId, recPositionID, recFirstName, recLastName, recStartDate.toLocalDate(),
                            recIsActive, recStartingShares, recSharesAssigned);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employee = new Employee();
        }

        return employee.getEmployee();
    }

    public static String getEmployeeName(String employeeID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String employee = "";

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT first_name, last_name FROM TBLEMPLOYEES WHERE employee_id=?");
            preparedStatement.setString(1, employeeID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Employee not found.");
            } else {
                while (resultSet.next()) {
                    String recFirstName = resultSet.getString("first_name");
                    String recLastName = resultSet.getString("last_name");

                    employee = recFirstName + " " + recLastName;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employee = "";
        }

        return employee;
    }

    public static Boolean insertEmployee(Employee employee) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Boolean insertSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            // check for existing ID
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEES WHERE employee_id=?");
            preparedStatement.setString(1, employee.getEmployee_id());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Employee with id already exists");
                return false;
            }
            // insert new employee
            preparedStatement = connection.prepareStatement("INSERT INTO TBLEMPLOYEES " +
                    "SET EMPLOYEE_ID=?, POSITION=?, FIRST_NAME=?, LAST_NAME=?, START_DATE=?, " +
                    "IS_ACTIVE=?, STARTING_SHARES=?, SHARES_ASSIGNED=?");
            preparedStatement.setString(1, employee.getEmployee_id());
            preparedStatement.setInt(2, employee.getPosition());
            preparedStatement.setString(3, employee.getFirst_name());
            preparedStatement.setString(4, employee.getLast_name());
            preparedStatement.setDate(5, Date.valueOf(employee.getStart_date()));
            preparedStatement.setBoolean(6, employee.getIs_active());
            preparedStatement.setInt(7, employee.getStarting_shares());
            preparedStatement.setInt(8, employee.getShares_assigned());
            preparedStatement.executeUpdate();

            // Update Company Shares
            EmployeeUtils.setCompanySharesAssigned();

            insertSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insertSuccess;
    }

    public static ArrayList<Employee> getEmployees() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Employee> employeeList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEES");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Employees found.");
            } else {
                while (resultSet.next()) {
                    String recEmployeeId = resultSet.getString("employee_id");
                    Integer recPositionID = resultSet.getInt("position");
                    String recFirstName = resultSet.getString("first_name");
                    String recLastName = resultSet.getString("last_name");
                    Date recStartDate = resultSet.getDate("start_date");
                    Boolean recIsActive = resultSet.getBoolean("is_active");
                    Integer recStartingShares = resultSet.getInt("starting_shares");
                    Integer recSharesAssigned = resultSet.getInt("shares_assigned");

                    employeeList.add(new Employee(recEmployeeId, recPositionID, recFirstName, recLastName, recStartDate.toLocalDate(),
                            recIsActive, recStartingShares, recSharesAssigned));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            employeeList.add(new Employee());
        }

        return employeeList;
    }

    public static ArrayList<Employee> getEmployees(Boolean isActive) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Employee> EmployeeList = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM TBLEMPLOYEES WHERE IS_ACTIVE=?");
            preparedStatement.setBoolean(1, isActive);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Employees found.");
            } else {
                while (resultSet.next()) {
                    String recEmployeeId = resultSet.getString("employee_id");
                    Integer recPositionID = resultSet.getInt("position");
                    String recFirstName = resultSet.getString("first_name");
                    String recLastName = resultSet.getString("last_name");
                    Date recStartDate = resultSet.getDate("start_date");
                    Boolean recIsActive = resultSet.getBoolean("is_active");
                    Integer recStartingShares = resultSet.getInt("starting_shares");
                    Integer recSharesAssigned = resultSet.getInt("shares_assigned");

                    EmployeeList.add(new Employee(recEmployeeId, recPositionID, recFirstName, recLastName, recStartDate.toLocalDate(),
                            recIsActive, recStartingShares, recSharesAssigned));
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
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("UPDATE TBLEMPLOYEES " +
                    "SET POSITION=?, FIRST_NAME=?, LAST_NAME=?, START_DATE=?, IS_ACTIVE=?, " +
                    "STARTING_SHARES=?, SHARES_ASSIGNED=? WHERE EMPLOYEE_ID=?");
            preparedStatement.setInt(1, employee.getPosition());
            preparedStatement.setString(2, employee.getFirst_name());
            preparedStatement.setString(3, employee.getLast_name());
            preparedStatement.setDate(4, Date.valueOf(employee.getStart_date()));
            preparedStatement.setBoolean(5, employee.getIs_active());
            preparedStatement.setInt(6, employee.getStarting_shares());
            preparedStatement.setInt(7, employee.getShares_assigned());
            preparedStatement.setString(8, employee.getEmployee_id());
            preparedStatement.executeUpdate();

            // Update Company Shares
            EmployeeUtils.setCompanySharesAssigned();

            updateSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }

    public static Boolean deleteEmployee(String employeeID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean deleteSuccess = false;

        try {
            connection = DriverManager.getConnection(DBUtils.getDBLocation(), "sa", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM TBLEMPLOYEES WHERE employee_id=?");
            preparedStatement.setString(1, employeeID);
            preparedStatement.executeUpdate();

            // Update Company Shares
            EmployeeUtils.setCompanySharesAssigned();

            deleteSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteSuccess;
    }

}
