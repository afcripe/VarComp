package net.dahliasolutions.varcomp.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmployeeScore {

    private Integer score_id;
    private Integer employee_id;
    private Integer employee_kpi_id;
    private Integer metric_id;
    private Integer shares;
    private BigDecimal score;
    private BigDecimal bonus;

    public EmployeeScore() {
        setScore_id(0);
        setEmployee_id(0);
        setEmployee_kpi_id(0);
        setMetric_id(0);
        setShares(0);
        setScore(new BigDecimal(0.00));
        setBonus(new BigDecimal(0.00));
    }

    public EmployeeScore(Integer scoreID, Integer employeeID, Integer employeeKPIid, Integer metricID,
                         Integer shares, BigDecimal score, BigDecimal bonus) {
        setScore_id(scoreID);
        setEmployee_id(employeeID);
        setEmployee_kpi_id(employeeKPIid);
        setMetric_id(metricID);
        setShares(shares);
        setScore(score);
        setBonus(bonus);
    }

    public void setEmployeeScore(EmployeeScore employeeScore) {
        this.score_id = employeeScore.getScore_id();
        this.employee_id = employeeScore.getEmployee_id();
        this.employee_kpi_id = employeeScore.getEmployee_kpi_id();
        this.metric_id = employeeScore.getMetric_id();
        this.shares = employeeScore.getShares();
        this.score = employeeScore.getScore();
        this.bonus = employeeScore.getBonus();
    }

    public EmployeeScore getEmployeeScore() { return this; }

    public Integer insertEmployeeScore() { return 0; }

    public void updateEmployeeScore() {  }

    public Integer getScore_id() { return score_id; }

    public void setScore_id(Integer score_id) { this.score_id = score_id; }

    public Integer getEmployee_id() { return employee_id; }

    public void setEmployee_id(Integer employee_id) { this.employee_id = employee_id; }

    public Integer getEmployee_kpi_id() { return employee_kpi_id; }

    public void setEmployee_kpi_id(Integer employee_kpi_id) { this.employee_kpi_id = employee_kpi_id; }

    public Integer getMetric_id() { return metric_id; }

    public void setMetric_id(Integer metric_id) { this.metric_id = metric_id; }

    public Integer getShares() { return shares; }

    public void setShares(Integer shares) { this.shares = shares; }

    public BigDecimal getScore() { return score; }

    public void setScore(BigDecimal score) {
        this.score = score;
        this.score = this.score.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getBonus() { return bonus; }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
        this.bonus = this.bonus.setScale(2, RoundingMode.HALF_UP);
    }
}
