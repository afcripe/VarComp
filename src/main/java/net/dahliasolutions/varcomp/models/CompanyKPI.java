package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.KPIMasterConnector;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CompanyKPI {

    private Integer company_kpi_id;
    private String kpi_code;
    private Integer kpi_master_id;
    private BigDecimal weight;
    private Integer metric_id;
    private Integer kpi_class;
    private String f1_name;
    private String f2_name;
    private String f3_name;
    private String f4_name;
    private BigDecimal f1_data;
    private BigDecimal f2_data;
    private BigDecimal f3_data;
    private BigDecimal f4_data;
    private Integer calc_instructions;
    private BigDecimal kpi_grade;
    private BigDecimal kpi_score;

    public CompanyKPI() {
        setCompany_kpi_id(0);
        setKpi_code("");
        setKpi_master_id(0);
        setWeight(new BigDecimal("0.00"));
        setMetric_id(0);
        setKpi_class(0);
        setF1_name("");
        setF2_name("");
        setF3_name("");
        setF4_name("");
        setF1_data(new BigDecimal("0.00"));
        setF2_data(new BigDecimal("0.00"));
        setF3_data(new BigDecimal("0.00"));
        setF4_data(new BigDecimal("0.00"));
        setCalc_instructions(0);
        setKpi_grade(new BigDecimal("0.00"));
        setKpi_score(new BigDecimal("0.00"));
    }

    public CompanyKPI(Integer companyKPIid, String KPICode, Integer masterID, Integer metricID,
                      Integer kpiClass, String f1Name, String f2Name, String f3Name, String f4Name,
                      Integer calcInstructions) {
        setCompany_kpi_id(companyKPIid);
        setKpi_code(KPICode);
        setKpi_master_id(masterID);
        setWeight(new BigDecimal("0.00"));
        setMetric_id(metricID);
        setKpi_class(kpiClass);
        setF1_name(f1Name);
        setF2_name(f2Name);
        setF3_name(f3Name);
        setF4_name(f4Name);
        setF1_data(new BigDecimal("0.00"));
        setF2_data(new BigDecimal("0.00"));
        setF3_data(new BigDecimal("0.00"));
        setF4_data(new BigDecimal("0.00"));
        setCalc_instructions(calcInstructions);
        setKpi_grade(new BigDecimal("0.00"));
        setKpi_score(new BigDecimal("0.00"));
    }

    public CompanyKPI(Integer companyKPIid, String KPICode, Integer masterID, BigDecimal weight, Integer metricID,
                      Integer kpiClass, String f1Name, String f2Name, String f3Name, String f4Name,
                      BigDecimal f1Data, BigDecimal f2Data, BigDecimal f3Data, BigDecimal f4Data,
                      Integer calcInstructions, BigDecimal kpiGrade, BigDecimal kpiScore) {
        setCompany_kpi_id(companyKPIid);
        setKpi_code(KPICode);
        setKpi_master_id(masterID);
        setWeight(weight);
        setMetric_id(metricID);
        setKpi_class(kpiClass);
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

    public void setCompanyKPI(CompanyKPI companyKPI) {
        this.company_kpi_id = companyKPI.getCompany_kpi_id();
        this.kpi_code = companyKPI.getKpi_code();
        this.kpi_master_id = companyKPI.getKpi_master_id();
        this.weight = companyKPI.getWeight();
        this.metric_id = companyKPI.getMetric_id();
        this.kpi_class = companyKPI.getKpi_class();
        this.f1_name = companyKPI.getF1_name();
        this.f2_name = companyKPI.getF2_name();
        this.f3_name = companyKPI.getF3_name();
        this.f4_name = companyKPI.getF4_name();
        this.f1_data = companyKPI.getF1_data();
        this.f2_data = companyKPI.getF2_data();
        this.f3_data = companyKPI.getF3_data();
        this.f4_data = companyKPI.getF4_data();
        this.calc_instructions = companyKPI.getCalc_instructions();
        this.kpi_grade = companyKPI.getKpi_grade();
        this.kpi_score = companyKPI.getKpi_score();
    }

    public CompanyKPI getCompanyKPI() { return this; }

    public Integer insetCompanyKPI() { return 0; }

    public void updateCompanyKPI() {  }

    public Integer getCompany_kpi_id() { return company_kpi_id; }

    public void setCompany_kpi_id(Integer company_kpi_id) { this.company_kpi_id = company_kpi_id; }

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

    public Integer getCalc_instructions() { return calc_instructions; }

    public void setCalc_instructions(Integer calc_instructions) { this.calc_instructions = calc_instructions; }

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

    public BigDecimal calcScore() {
        BigDecimal thisScore = switch (getCalc_instructions()) {
            case 1 -> getF1_data().multiply(new BigDecimal("0.1")).add(getF2_data().multiply(new BigDecimal("0.3")))
                    .add(getF3_data().multiply(new BigDecimal("0.3"))).add(getF4_data().multiply(new BigDecimal("0.3")));
            case 2 -> getF1_data().divide(getF2_data(), RoundingMode.HALF_UP).multiply(new BigDecimal(365));
            case 3 -> getF1_data().divide(getF2_data(), RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            default -> new BigDecimal("0.00");
        };
        thisScore = thisScore.setScale(2, RoundingMode.HALF_UP);
        setKpi_score(thisScore);
        return thisScore;
    }

    public BigDecimal calcGrade() {
        BigDecimal thisGrade;
        KPIMaster kpiMaster = KPIMasterConnector.getKPIMaster(getKpi_master_id());
        if (kpiMaster.getReverse_scores()) {
            if(getKpi_score().doubleValue() >= kpiMaster.getScore_needs_improvement().doubleValue()) {
                thisGrade = new BigDecimal("1.00");
            } else if (getKpi_score().doubleValue() >= kpiMaster.getScore_poor().doubleValue()) {
                thisGrade = new BigDecimal("0.80");
            } else if (getKpi_score().doubleValue() >= kpiMaster.getScore_well().doubleValue()) {
                thisGrade = new BigDecimal("0.60");
            } else if (getKpi_score().doubleValue() >= kpiMaster.getScore_great().doubleValue()) {
                thisGrade = new BigDecimal("0.40");
            } else if (getKpi_score().doubleValue() >= kpiMaster.getScore_extraordinary().doubleValue()) {
                thisGrade = new BigDecimal("0.20");
            } else {
                thisGrade = new BigDecimal("0.00");
            }
        } else {
            if(getKpi_score().doubleValue() >= kpiMaster.getScore_extraordinary().doubleValue()) {
                thisGrade = new BigDecimal("1.00");
            } else if (getKpi_score().doubleValue() >= kpiMaster.getScore_great().doubleValue()) {
                thisGrade = new BigDecimal("0.80");
            } else if (getKpi_score().doubleValue() >= kpiMaster.getScore_well().doubleValue()) {
                thisGrade = new BigDecimal("0.60");
            } else if (getKpi_score().doubleValue() >= kpiMaster.getScore_poor().doubleValue()) {
                thisGrade = new BigDecimal("0.40");
            } else if (getKpi_score().doubleValue() >= kpiMaster.getScore_needs_improvement().doubleValue()) {
                thisGrade = new BigDecimal("0.20");
            } else {
                thisGrade = new BigDecimal("0.00");
            }
        }

        thisGrade = thisGrade.setScale(2, RoundingMode.HALF_UP);
        setKpi_grade(thisGrade);
        return thisGrade;
    }
}
