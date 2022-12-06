module net.dahliasolutions.varcomp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.swing;
    requires javafx.web;

    opens net.dahliasolutions.varcomp to javafx.fxml;
    exports net.dahliasolutions.varcomp;
    exports net.dahliasolutions.varcomp.models;
}