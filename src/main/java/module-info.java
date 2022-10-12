module net.dahliasolutions.varcomp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;

    opens net.dahliasolutions.varcomp to javafx.fxml;
    exports net.dahliasolutions.varcomp;
    exports net.dahliasolutions.varcomp.models;
}