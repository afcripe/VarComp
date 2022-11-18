package net.dahliasolutions.varcomp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import net.dahliasolutions.varcomp.connectors.CompanyConnector;
import net.dahliasolutions.varcomp.connectors.UserConnector;
import net.dahliasolutions.varcomp.models.Company;
import net.dahliasolutions.varcomp.models.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController extends ViewController implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private Label lblWarning;
    @FXML
    private Button btnLogin;
    @FXML
    private ChoiceBox<String> choiceCompanies;
    @FXML
    private Label lblDBL;
    @FXML
    private Label lblVersion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnLogin.disableProperty().bind(txtUsername.textProperty().isEmpty().or(pwdPassword.textProperty().isEmpty()));
        lblDBL.setText(DBUtils.getDBLocation());
        lblVersion.setText("VarComp v: "+DBUtils.getAppVersion());

        txtUsername.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                btnLogin.fire();
            }
        });
        pwdPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                btnLogin.fire();
            }
        });

        btnLogin.setOnAction(event -> {

            if ( txtUsername.getText().isEmpty() && pwdPassword.getText().isEmpty() ) {
                lblWarning.setText("Please provide a username and password.");
                lblWarning.setVisible(true);
            } else {
                User u = UserConnector.loginUser(txtUsername.getText(), pwdPassword.getText());
                if (!u.getUser_id().equals(0)) {
                    lblWarning.setVisible(false);
                    VarComp.setUser(u);

                    Company c = CompanyConnector.getCompany(1);
                    VarComp.setCurrentCompany(c);
                    EmployeeUtils.updateAllEmployeeShares();

                    VarComp.changeScene("varcomp-view.fxml", "VarComp", false);
                } else {
                    lblWarning.setText("Unsuccessful Login Attempt!");
                    lblWarning.setVisible(true);
                }
            }
        });

        ArrayList<Company> companies = CompanyConnector.getCompanies();
        companies.forEach(company -> choiceCompanies.getItems().add(company.getCompany_name()));
        choiceCompanies.setValue(choiceCompanies.getItems().get(0));

    }

}
