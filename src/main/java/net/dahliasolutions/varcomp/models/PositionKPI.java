package net.dahliasolutions.varcomp.models;


import net.dahliasolutions.varcomp.connectors.PositionKPIConnector;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PositionKPI {
    private Integer item_id;
    private Integer position_id;
    private Integer kpi_master_id;

    private Integer kpi_class_id;
    private BigDecimal weight;

    public PositionKPI() {
        setItem_id(0);
        setPosition_id(0);
        setKpi_master_id(0);
        setKpi_class_id(0);
        setWeight(BigDecimal.valueOf(0.00));
    }

    public PositionKPI(Integer itemID, Integer positionID, Integer kpiMastID, Integer kpiClass, BigDecimal weight) {
        setItem_id(itemID);
        setPosition_id(positionID);
        setKpi_master_id(kpiMastID);
        setKpi_class_id(kpiClass);
        setWeight(weight);
    }

    public void setPositionKPI(PositionKPI positionKPI) {
        this.item_id = positionKPI.getItem_id();
        this.position_id = positionKPI.getPosition_id();
        this.kpi_master_id = positionKPI.getKpi_master_id();
        this.kpi_class_id = positionKPI.getKpi_class_id();
        this.weight = positionKPI.getWeight();
    }

    public PositionKPI getPositionKPI() { return this; }

    public Integer getItem_id() {return item_id;}

    public void setItem_id(Integer item_id) {this.item_id = item_id;}

    public Integer getPosition_id() {return position_id;}

    public void setPosition_id(Integer position_id) {this.position_id = position_id;}

    public Integer getKpi_master_id() {return kpi_master_id;}

    public void setKpi_master_id(Integer kpi_master_id) {this.kpi_master_id = kpi_master_id;}

    public Integer getKpi_class_id() { return kpi_class_id; }

    public void setKpi_class_id(Integer kpi_class_id) { this.kpi_class_id = kpi_class_id; }

    public BigDecimal getWeight() {return weight;}

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
        this.weight = this.weight.setScale(2, RoundingMode.HALF_UP);
    }

    public Integer insertPositionKPI() {return PositionKPIConnector.insertPositionKPI(this).getItem_id();}

    public void updatePositionKPI() { PositionKPIConnector.updatePositionKPI(this);}
}
