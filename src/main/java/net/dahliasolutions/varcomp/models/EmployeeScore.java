package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.EmployeeConnector;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmployeeScore {

    private Integer score_id;
    private String employee_id;
    private Integer metric_id;
    private Integer shares;
    private BigDecimal grade;
    private BigDecimal bonus;

    private String employee_name;

    public EmployeeScore() {
        setScore_id(0);
        setEmployee_id("");
        setMetric_id(0);
        setShares(0);
        setGrade(new BigDecimal("0.00"));
        setBonus(new BigDecimal("0.00"));
        setEmployee_name("");
    }

    public EmployeeScore(Integer scoreID, String employeeID, Integer metricID,
                         Integer shares, BigDecimal grade, BigDecimal bonus) {
        setScore_id(scoreID);
        setEmployee_id(employeeID);
        setMetric_id(metricID);
        setShares(shares);
        setGrade(grade);
        setBonus(bonus);
        setEmployee_name(EmployeeConnector.getEmployeeName(getEmployee_id()));
    }

    public EmployeeScore(Integer scoreID, String employeeID, Integer metricID,
                         Integer shares, BigDecimal grade, BigDecimal bonus, String employeeName) {
        setScore_id(scoreID);
        setEmployee_id(employeeID);
        setMetric_id(metricID);
        setShares(shares);
        setGrade(grade);
        setBonus(bonus);
        setEmployee_name(employeeName);
    }

    public void setEmployeeScore(EmployeeScore employeeScore) {
        this.score_id = employeeScore.getScore_id();
        this.employee_id = employeeScore.getEmployee_id();
        this.metric_id = employeeScore.getMetric_id();
        this.shares = employeeScore.getShares();
        this.grade = employeeScore.getGrade();
        this.bonus = employeeScore.getBonus();
        this.employee_name = employeeScore.getEmployee_name();
    }

    public EmployeeScore getEmployeeScore() { return this; }

    public Integer insertEmployeeScore() { return 0; }

    public void updateEmployeeScore() {  }

    public Integer getScore_id() { return score_id; }

    public void setScore_id(Integer score_id) { this.score_id = score_id; }

    public String getEmployee_id() { return employee_id; }

    public void setEmployee_id(String employee_id) { this.employee_id = employee_id; }

    public Integer getMetric_id() { return metric_id; }

    public void setMetric_id(Integer metric_id) { this.metric_id = metric_id; }

    public Integer getShares() { return shares; }

    public void setShares(Integer shares) { this.shares = shares; }

    public BigDecimal getGrade() { return grade; }

    public void setGrade(BigDecimal score) {
        this.grade = score;
        this.grade = this.grade.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getBonus() { return bonus; }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
        this.bonus = this.bonus.setScale(2, RoundingMode.HALF_UP);
    }

    public void setEmployee_name(String employee_name) { this.employee_name = employee_name; }

    public String getEmployee_name() { return employee_name; }

    public String refreshEmployeeName() {
        setEmployee_name(EmployeeConnector.getEmployeeName(getEmployee_id()));
        return getEmployee_name();
    }
}
