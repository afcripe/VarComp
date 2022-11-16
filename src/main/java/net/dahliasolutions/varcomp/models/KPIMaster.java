package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.KPIMasterConnector;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class KPIMaster {

    private Integer kpi_master_id;
    private String kpi_code;
    private String description;
    private Integer kpi_class;
    private Integer calc_instructions;
    private BigDecimal score_extraordinary;
    private BigDecimal score_great;
    private BigDecimal score_well;
    private BigDecimal score_needs_improvement;
    private BigDecimal score_poor;
    private BigDecimal score_not_acceptable;
    private String f1_name;
    private String f2_name;
    private String f3_name;
    private String f4_name;

    private Boolean reverse_scores;

    public KPIMaster() {
        setKpi_master_id(0);
        setKpi_code("");
        setDescription("");
        setKpi_class(1);
        setCalc_instructions(0);
        setScore_extraordinary(BigDecimal.valueOf(0.90));
        setScore_great(BigDecimal.valueOf(0.80));
        setScore_well(BigDecimal.valueOf(0.70));
        setScore_needs_improvement(BigDecimal.valueOf(0.60));
        setScore_poor(BigDecimal.valueOf(0.70));
        setScore_not_acceptable(BigDecimal.valueOf(0.50));
        setF1_name("");
        setF2_name("");
        setF3_name("");
        setF4_name("");
        setReverse_scores(false);
    }

    public KPIMaster(Integer id, String code, String description, Integer kpiClass, Integer calcInstructions,
                          BigDecimal scoreExtraordinary, BigDecimal scoreGreat, BigDecimal scoreWell,
                          BigDecimal scoreNeedsImprovement, BigDecimal scorePoor, BigDecimal scoreNotAcceptable,
                          String f1Name, String f2Name, String f3Name, String f4Name, Boolean reverseScores) {
        setKpi_master_id(id);
        setKpi_code(code);
        setDescription(description);
        setKpi_class(kpiClass);
        setCalc_instructions(calcInstructions);
        setScore_extraordinary(scoreExtraordinary);
        setScore_great(scoreGreat);
        setScore_well(scoreWell);
        setScore_needs_improvement(scoreNeedsImprovement);
        setScore_poor(scorePoor);
        setScore_not_acceptable(scoreNotAcceptable);
        setF1_name(f1Name);
        setF2_name(f2Name);
        setF3_name(f3Name);
        setF4_name(f4Name);
        setReverse_scores(reverseScores);
    }

    public void setKPIMaster(KPIMaster kpiMaster) {
        this.kpi_master_id = kpiMaster.kpi_master_id;
        this.kpi_code = kpiMaster.kpi_code;
        this.description = kpiMaster.description;
        this.kpi_class = kpiMaster.kpi_class;
        this.calc_instructions = kpiMaster.calc_instructions;
        this.score_extraordinary = kpiMaster.score_extraordinary;
        this.score_great = kpiMaster.score_great;
        this.score_well = kpiMaster.score_well;
        this.score_needs_improvement = kpiMaster.score_needs_improvement;
        this.score_poor = kpiMaster.score_poor;
        this.score_not_acceptable = kpiMaster.score_not_acceptable;
        this.f1_name = kpiMaster.f1_name;
        this.f2_name = kpiMaster.f2_name;
        this.f3_name = kpiMaster.f3_name;
        this.f4_name = kpiMaster.f4_name;
        this.reverse_scores = kpiMaster.reverse_scores;
    }

    public KPIMaster getKPIMaster() {
        return this;
    }

    public Integer getKpi_master_id() {
        return kpi_master_id;
    }

    public void setKpi_master_id(Integer kpi_master_id) {
        this.kpi_master_id = kpi_master_id;
    }

    public String getKpi_code() {
        return kpi_code;
    }

    public void setKpi_code(String kpi_code) {
        this.kpi_code = kpi_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getKpi_class() {
        return kpi_class;
    }

    public void setKpi_class(Integer kpi_class) {
        this.kpi_class = kpi_class;
    }

    public Integer getCalc_instructions() {
        return calc_instructions;
    }

    public void setCalc_instructions(Integer calc_instructions) {
        this.calc_instructions = calc_instructions;
    }

    public BigDecimal getScore_extraordinary() {
        return score_extraordinary;
    }

    public void setScore_extraordinary(BigDecimal score_extraordinary) {
        this.score_extraordinary = score_extraordinary;
        this.score_extraordinary = this.score_extraordinary.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getScore_great() {
        return score_great;
    }

    public void setScore_great(BigDecimal score_great) {
        this.score_great = score_great;
        this.score_great = this.score_great.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getScore_well() {
        return score_well;
    }

    public void setScore_well(BigDecimal score_well) {
        this.score_well = score_well;
        this.score_well = this.score_well.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getScore_needs_improvement() {
        return score_needs_improvement;
    }

    public void setScore_needs_improvement(BigDecimal score_needs_improvement) {
        this.score_needs_improvement = score_needs_improvement;
        this.score_needs_improvement = this.score_needs_improvement.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getScore_poor() {
        return score_poor;
    }

    public void setScore_poor(BigDecimal score_poor) {
        this.score_poor = score_poor;
        this.score_poor = this.score_poor.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getScore_not_acceptable() {
        return score_not_acceptable;
    }

    public void setScore_not_acceptable(BigDecimal score_not_acceptable) {
        this.score_not_acceptable = score_not_acceptable;
        this.score_not_acceptable = this.score_not_acceptable.setScale(2, RoundingMode.HALF_UP);
    }

    public String getF1_name() {
        return f1_name;
    }

    public void setF1_name(String f1_name) {
        this.f1_name = f1_name;
    }

    public String getF2_name() {
        return f2_name;
    }

    public void setF2_name(String f2_name) {
        this.f2_name = f2_name;
    }

    public String getF3_name() {
        return f3_name;
    }

    public void setF3_name(String f3_name) {
        this.f3_name = f3_name;
    }

    public String getF4_name() {
        return f4_name;
    }

    public void setF4_name(String f4_name) {
        this.f4_name = f4_name;
    }

    public Boolean getReverse_scores() { return reverse_scores; }

    public void setReverse_scores(Boolean reverse_scores) { this.reverse_scores = reverse_scores; }

    public Integer insertKPIMaster(){
        return KPIMasterConnector.insertKPIMaster(this).getKpi_master_id();
    }

    public void updateKPIMaster() {
        KPIMasterConnector.updateKPIMaster(this);
    }
}
