package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.CalculationOptionsConnection;

public class CalculationOptions {

    private Integer calculation_id;
    private String calculation_name;
    private String calculation_description;
    private String calculation_instructions;

    public CalculationOptions(){
        setCalculation_id(0);
        setCalculation_name("");
        setCalculation_description("");
        setCalculation_instructions("");
    }

    public CalculationOptions(Integer calculationID, String calculationName,
                              String calculationDescription, String calculationInstructions) {
        setCalculation_id(calculationID);
        setCalculation_name(calculationName);
        setCalculation_description(calculationDescription);
        setCalculation_instructions(calculationInstructions);
    }

    public void setCalculationOptions(CalculationOptions calculationOptions) {
        setCalculation_id(calculationOptions.getCalculation_id());
        setCalculation_name(calculationOptions.getCalculation_name());
        setCalculation_description(calculationOptions.getCalculation_description());
        setCalculation_instructions(calculationOptions.getCalculation_instructions());
    }

    public CalculationOptions getCalculationOptions() {
        return this;
    }

    public Integer getCalculation_id() {
        return calculation_id;
    }

    public void setCalculation_id(Integer calculation_id) {
        this.calculation_id = calculation_id;
    }

    public String getCalculation_name() {
        return calculation_name;
    }

    public void setCalculation_name(String calculation_name) {
        this.calculation_name = calculation_name;
    }

    public String getCalculation_description() {
        return calculation_description;
    }

    public void setCalculation_description(String calculation_description) {
        this.calculation_description = calculation_description;
    }

    public String getCalculation_instructions() {
        return calculation_instructions;
    }

    public void setCalculation_instructions(String calculation_instructions) {
        this.calculation_instructions = calculation_instructions;
    }

    public Integer insertCalculationOptions(){
        return CalculationOptionsConnection.insertCalculationOption(this).getCalculation_id();
    }

    public void updateCalculationOptions() {
        CalculationOptionsConnection.updateCalculationOption(this);
    }
}
