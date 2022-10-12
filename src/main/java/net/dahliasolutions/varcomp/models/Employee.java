package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.EmployeeConnector;

import java.time.LocalDate;
import java.util.Date;

public class Employee {
    private Integer employee_id;
    private Integer position;
    private String first_name;
    private String last_name;
    private LocalDate start_date;
    private Boolean is_active;
    private Integer shares_assigned;

    public Employee() {
        setEmployee_id(0);
        setPosition(0);
        setFirst_name("");
        setLast_name("");
        setStart_date(LocalDate.now());
        setIs_active(true);
        setShares_assigned(0);
    }

    public Employee(Integer employeeID, Integer positionID, String firstName, String lastName, LocalDate startDate,
                    Boolean isActive, Integer sharesAssigned) {
        setEmployee_id(employeeID);
        setPosition(positionID);
        setFirst_name(firstName);
        setLast_name(lastName);
        setStart_date(startDate);
        setIs_active(isActive);
        setShares_assigned(sharesAssigned);
    }

    public void setEmployee(Employee employee) {
        this.employee_id = employee.getEmployee_id();
        this.position = employee.getPosition();
        this.first_name = employee.getFirst_name();
        this.last_name = employee.getLast_name();
        this.start_date = employee.getStart_date();
        this.is_active = employee.getIs_active();
        this.shares_assigned = employee.getShares_assigned();
    }

    public Employee getEmployee() { return this; }

    public Integer insertEmployee() { return EmployeeConnector.insertEmployee(this).getEmployee_id(); }

    public void updateEmployee() { EmployeeConnector.updateEmployee(this); }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Integer getShares_assigned() {
        return shares_assigned;
    }

    public void setShares_assigned(Integer shares_assigned) {
        this.shares_assigned = shares_assigned;
    }
}
