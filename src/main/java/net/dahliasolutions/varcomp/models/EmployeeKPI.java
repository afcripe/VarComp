package net.dahliasolutions.varcomp.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmployeeKPI {

    private Integer employee_kpi_id;
    private String kpi_code;
    private Integer kpi_master_id;
    private BigDecimal weight;
    private Integer metric_id;
    private Integer kpi_class;
    private Integer company_kpi_id;
    private String f1_name;
    private String f2_name;
    private String f3_name;
    private String f4_name;
    private BigDecimal f1_data;
    private BigDecimal f2_data;
    private BigDecimal f3_data;
    private BigDecimal f4_data;
    private String calc_instructions;
    private BigDecimal kpi_grade;
    private BigDecimal kpi_score;

    public EmployeeKPI() {
        setEmployee_kpi_id(0);
        setKpi_code("");
        setKpi_master_id(0);
        setWeight(new BigDecimal(0.00));
        setMetric_id(0);
        setKpi_class(0);
        setCompany_kpi_id(0);
        setF1_name("");
        setF2_name("");
        setF3_name("");
        setF4_name("");
        setF1_data(new BigDecimal(0.00));
        setF2_data(new BigDecimal(0.00));
        setF3_data(new BigDecimal(0.00));
        setF4_data(new BigDecimal(0.00));
        setCalc_instructions("");
        setKpi_grade(new BigDecimal(0.00));
        setKpi_score(new BigDecimal(0.00));
    }

    public EmployeeKPI(Integer employeeKPIid, String KPICode, Integer masterID, BigDecimal weight, Integer metricID,
                       Integer kpiClass, Integer companyKPIid, String f1Name, String f2Name, String f3Name, String f4Name,
                       BigDecimal f1Data, BigDecimal f2Data, BigDecimal f3Data, BigDecimal f4Data,
                       String calcInstructions, BigDecimal kpiGrade, BigDecimal kpiScore) {
        setEmployee_kpi_id(employeeKPIid);
        setKpi_code(KPICode);
        setKpi_master_id(masterID);
        setWeight(weight);
        setMetric_id(metricID);
        setKpi_class(kpiClass);
        setCompany_kpi_id(companyKPIid);
        setF1_name(f1Name);
        setF2_name(f2Name);
        setF3_name(f3Name);
        setF4_name(f4Name);
        setF1_data(f1Data);
        setF2_data(f2Data);
        setF3_data(f3Data);
        setF4_data(f4Data);
        setCalc_instructions(calcInstructions);
        setKpi_grade(kpiGrade);
        setKpi_score(kpiScore);
    }

    public void setEmployeeKPI(EmployeeKPI employeeKPI) {
        this.employee_kpi_id = employeeKPI.getEmployee_kpi_id();
        this.kpi_code = employeeKPI.getKpi_code();
        this.kpi_master_id = employeeKPI.getKpi_master_id();
        this.weight = employeeKPI.getWeight();
        this.metric_id = employeeKPI.getMetric_id();
        this.kpi_class = employeeKPI.getKpi_class();
        this.company_kpi_id = employeeKPI.getCompany_kpi_id();
        this.f1_name = employeeKPI.getF1_name();
        this.f2_name = employeeKPI.getF2_name();
        this.f3_name = employeeKPI.getF3_name();
        this.f4_name = employeeKPI.getF4_name();
        this.f1_data = employeeKPI.getF1_data();
        this.f2_data = employeeKPI.getF2_data();
        this.f3_data = employeeKPI.getF3_data();
        this.f4_data = employeeKPI.getF4_data();
        this.calc_instructions = employeeKPI.getCalc_instructions();
        this.kpi_grade = employeeKPI.getKpi_grade();
        this.kpi_score = employeeKPI.getKpi_score();
    }

    public EmployeeKPI getEmployeeKPI() { return this; }

    public Integer insetEmployeeKPI() { return 0; }

    public void updateEmployeeKPI() {  }

    public Integer getEmployee_kpi_id() { return employee_kpi_id; }

    public void setEmployee_kpi_id(Integer employee_kpi_id) { this.employee_kpi_id = employee_kpi_id; }

    public String getKpi_code() { return kpi_code; }

    public void setKpi_code(String kpi_code) { this.kpi_code = kpi_code; }

    public Integer getKpi_master_id() { return kpi_master_id; }

    public void setKpi_master_id(Integer kpi_master_id) { this.kpi_master_id = kpi_master_id; }

    public BigDecimal getWeight() { return weight; }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
        this.weight = this.weight.setScale(2, RoundingMode.HALF_UP);
    }

    public Integer getMetric_id() { return metric_id; }

    public void setMetric_id(Integer metric_id) { this.metric_id = metric_id; }

    public Integer getKpi_class() { return kpi_class; }

    public void setKpi_class(Integer kpi_class) { this.kpi_class = kpi_class; }

    public Integer getCompany_kpi_id() { return company_kpi_id; }

    public void setCompany_kpi_id(Integer company_kpi_id) { this.company_kpi_id = company_kpi_id; }

    public String getF1_name() { return f1_name; }

    public void setF1_name(String f1_name) { this.f1_name = f1_name; }

    public String getF2_name() { return f2_name; }

    public void setF2_name(String f2_name) { this.f2_name = f2_name; }

    public String getF3_name() { return f3_name; }

    public void setF3_name(String f3_name) { this.f3_name = f3_name; }

    public String getF4_name() { return f4_name; }

    public void setF4_name(String f4_name) { this.f4_name = f4_name; }

    public BigDecimal getF1_data() { return f1_data; }

    public void setF1_data(BigDecimal f1_data) {
        this.f1_data = f1_data;
        this.f1_data = this.f1_data.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getF2_data() { return f2_data; }

    public void setF2_data(BigDecimal f2_data) {
        this.f2_data = f2_data;
        this.f2_data = this.f2_data.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getF3_data() { return f3_data; }

    public void setF3_data(BigDecimal f3_data) {
        this.f3_data = f3_data;
        this.f3_data = this.f3_data.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getF4_data() { return f4_data; }

    public void setF4_data(BigDecimal f4_data) {
        this.f4_data = f4_data;
        this.f4_data = this.f4_data.setScale(2, RoundingMode.HALF_UP);
    }

    public String getCalc_instructions() { return calc_instructions; }

    public void setCalc_instructions(String calc_instructions) { this.calc_instructions = calc_instructions; }

    public BigDecimal getKpi_grade() { return kpi_grade; }

    public void setKpi_grade(BigDecimal kpi_grade) {
        this.kpi_grade = kpi_grade;
        this.kpi_grade = this.kpi_grade.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getKpi_score() { return kpi_score; }

    public void setKpi_score(BigDecimal kpi_score) {
        this.kpi_score = kpi_score;
        this.kpi_score = this.kpi_score.setScale(2, RoundingMode.HALF_UP);
    }
}