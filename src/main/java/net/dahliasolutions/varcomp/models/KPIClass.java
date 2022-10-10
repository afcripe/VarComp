package net.dahliasolutions.varcomp.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import net.dahliasolutions.varcomp.connectors.KPIClassConnector;

public class KPIClass {

    private final SimpleIntegerProperty kpi_class_id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleBooleanProperty auto_fill_employees;

    public KPIClass(){
        this.kpi_class_id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.auto_fill_employees = new SimpleBooleanProperty(false);
    }

    public KPIClass(Integer classID, String className, String classDescription, Boolean autoFillEmployee){
        this.kpi_class_id = new SimpleIntegerProperty(classID);
        this.name = new SimpleStringProperty(className);
        this.description = new SimpleStringProperty(classDescription);
        this.auto_fill_employees = new SimpleBooleanProperty(autoFillEmployee);
    }

    public void setKPIClass(KPIClass kpiClass) {
        setKpi_class_id(kpiClass.getKpi_class_id());
        setName(kpiClass.getName());
        setDescription(kpiClass.getDescription());
        setAuto_fill_employees(kpiClass.getAuto_fill_employees());
    }

    public KPIClass getKPIClass() {return this;}

    public Integer getKpi_class_id() {
        return kpi_class_id.get();
    }

    public void setKpi_class_id(Integer kpiClassId) {
        kpi_class_id.set(kpiClassId);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String className) {
        name.set(className);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String classDescription) {
        description.set(classDescription);
    }

    public Boolean getAuto_fill_employees() {
        return auto_fill_employees.get();
    }

    public void setAuto_fill_employees(Boolean autoFillEmployees) {
        auto_fill_employees.set(autoFillEmployees);
    }

    public Integer insertKPIClass() {
        return KPIClassConnector.insertKPIClass(this).getKpi_class_id();
    }
    public void updateKPIClass() {
        KPIClassConnector.updateKPIClass(this);
    }
}
