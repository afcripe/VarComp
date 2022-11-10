package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class Employee {
    private String employee_id;
    private Integer position;
    private String first_name;
    private String last_name;
    private LocalDate start_date;
    private Boolean is_active;
    private Integer shares_assigned;

    public Employee() {
        setEmployee_id("");
        setPosition(0);
        setFirst_name("");
        setLast_name("");
        setStart_date(LocalDate.now());
        setIs_active(true);
        setShares_assigned(0);
    }

    public Employee(String employeeID, Integer positionID, String firstName, String lastName, LocalDate startDate,
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

    public Boolean insertEmployee() { return EmployeeConnector.insertEmployee(this); }

    public void updateEmployee() { EmployeeConnector.updateEmployee(this); }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
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

    public void addToMetric(Integer metricID) {
        // get list of KPIs for employee by position
        ArrayList<PositionKPI> positionKPIArrayList = PositionKPIConnector.getPositionKPIsPosition(getPosition());

        //get Company KPIs, Loop and Insert
        ArrayList<CompanyKPI> companyKPIArrayList = CompanyKPIConnector.getCompanyKPIs(metricID);
        for (CompanyKPI companyKPI : companyKPIArrayList) {
            if (!findKPIByPosition(positionKPIArrayList, companyKPI.getKpi_master_id()).isEmpty()) {
                EmployeeKPIConnector.insertEmployeeKPI(new EmployeeKPI(0,
                        companyKPI.getKpi_code(), companyKPI.getKpi_master_id(), getPositionKPIWeight(companyKPI.getKpi_master_id()),
                        metricID, companyKPI.getKpi_class(), companyKPI.getCompany_kpi_id(), companyKPI.getF1_name(),
                        companyKPI.getF2_name(), companyKPI.getF3_name(), companyKPI.getF4_name(), companyKPI.getF1_data(),
                        companyKPI.getF2_data(), companyKPI.getF3_data(), companyKPI.getF4_data(), companyKPI.getCalc_instructions(),
                        companyKPI.getKpi_grade(), companyKPI.getKpi_score()
                ));
            }
        }

        // filter list of KPIs by employee class, Loop and Insert
//        ArrayList<PositionKPI> filteredList = positionKPIArrayList.stream()
//                .filter(t -> t.getKPIClass.equals(kpiID))
//                .collect(Collectors.toList());
    }

    private BigDecimal getPositionKPIWeight(Integer masterID) {
        return PositionKPIConnector.getPositionKPIByMasterAndPosition(masterID, getPosition()).getWeight();
    }

    private ArrayList<PositionKPI> findKPIByPosition(ArrayList<PositionKPI> aList, Integer kpiID) {
        return (ArrayList<PositionKPI>) aList.stream()
                .filter(t -> t.getKpi_master_id().equals(kpiID))
                .collect(Collectors.toList());
    }
}
