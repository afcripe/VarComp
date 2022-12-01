package net.dahliasolutions.varcomp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import net.dahliasolutions.varcomp.connectors.AppCompanyConnector;
import net.dahliasolutions.varcomp.connectors.CompanyConnector;
import net.dahliasolutions.varcomp.connectors.UserConnector;
import net.dahliasolutions.varcomp.models.AppCompany;
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
    @FXML
    private HBox boxNewCompany;
    @FXML
    private TextField txtNewCompany;
    @FXML
    private Button btnNewCompany;

    ObservableList<AppCompany> companies;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        companies = FXCollections.observableArrayList(AppCompanyConnector.getCompanies());

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
                return;
            }
            if (choiceCompanies.getSelectionModel().getSelectedItem().equals("New Company")) {
                txtNewCompany.setText(choiceCompanies.getSelectionModel().getSelectedItem());
                toggleNewCompany(true);
            } else {
                //DBUtils.setDBLocation(AppCompanyConnector.getCompany());
                goLogin();
            }
        });

        btnNewCompany.setOnAction(event -> createCompany());
        fillCompanies();

    }

    private void fillCompanies() {
        companies = FXCollections.observableArrayList(AppCompanyConnector.getCompanies());

        choiceCompanies.getItems().removeAll();
        companies.forEach(company -> choiceCompanies.getItems().add(company.getCompany_name()));
        choiceCompanies.setValue(choiceCompanies.getItems().get(0));
    }

    private void createCompany() {
        String newName = txtNewCompany.getText().toString();
        String newSafeName = newName.replaceAll(" ", "_");
        newSafeName = newSafeName.toLowerCase();

        DBUtils.setDBLocation(newSafeName);
        DBSetup.initializeDB();
        DBSetup.initializeCompanyDefault(newName);

        AppCompanyConnector.insertCompany(new AppCompany(newName, newSafeName));

        if (!txtUsername.toString().equals("admin")) {
            User newUser = new User();
            newUser.setUser_name(txtUsername.getText());
            newUser.setUser_password(pwdPassword.getText());
            newUser.insertUser();
        }
        toggleNewCompany(false);
        fillCompanies();
        goLogin();
    }

    private void toggleNewCompany(boolean b) {
        btnLogin.setVisible(!b);
        btnLogin.setManaged(!b);
        boxNewCompany.setVisible(b);
        boxNewCompany.setManaged(b);
    }

    private void goLogin() {
        User u = UserConnector.loginUser(txtUsername.getText(), pwdPassword.getText());
        if (!u.getUser_id().equals(0)) {
            lblWarning.setVisible(false);
            VarComp.setUser(u);

            VarComp.setCurrentCompany(CompanyConnector.getCompany(1));
            EmployeeUtils.updateAllEmployeeShares();

            VarComp.changeScene("varcomp-view.fxml", "VarComp", false);
        } else {
            lblWarning.setText("Unsuccessful Login Attempt!");
            lblWarning.setVisible(true);
        }
    }

}
