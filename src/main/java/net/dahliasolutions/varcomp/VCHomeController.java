package net.dahliasolutions.varcomp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class VCHomeController implements Initializable {

    @FXML
    private Label lblTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTitle.setText(VarComp.getCurrentCompany().getCompany_name());
    }
}
