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

import java.io.File;
import java.net.URL;
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
    private ComboBox<String> choiceCompanies;
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
        lblDBL.setText(DBUtils.getAppDBLocation());
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
        choiceCompanies.setOnKeyPressed(keyEvent -> {
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
                goLogin(choiceCompanies.getSelectionModel().getSelectedItem());
            }
        });

        txtNewCompany.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                btnNewCompany.fire();
            }
        });
        btnNewCompany.setOnAction(event -> createCompany());
        fillCompanies();

    }

    private void fillCompanies() {
        companies = FXCollections.observableArrayList(AppCompanyConnector.getCompanies());
        companies.add(new AppCompany());

        choiceCompanies.getItems().removeAll();
        companies.forEach(company -> choiceCompanies.getItems().add(company.getCompany_name()));
        choiceCompanies.setValue(choiceCompanies.getItems().get(0));
    }

    private void createCompany() {
        String newName = txtNewCompany.getText().toString();
        String newSafeName = newName.replaceAll(" ", "_");
        newSafeName = newSafeName.toLowerCase();

        // set the company DB location and then create the DB
        DBUtils.setDBLocation(newSafeName);

        File dir = new File(DBUtils.getCompanyDir());
        int i = 0;
        while(dir.exists()) {
            i++;
            DBUtils.setDBLocation(newSafeName+"_"+i);
            dir = new File(DBUtils.getCompanyDir());
        }
        if (i > 0) newName = newName+" "+i;

        DBSetup.initializeDB();

        // insert the company into AppDB
        AppCompany appCompany = new AppCompany(newName, newSafeName);
        int companyID = AppCompanyConnector.insertCompany(appCompany);

        // update companyDB info
        Company company = new Company();
        company.setCompany_id(companyID);
        company.setCompany_name(newName);
        company.updateNewCompany();

        if (!txtUsername.getText().equals("admin")) {
            User newUser = new User();
            newUser.setUser_name(txtUsername.getText());
            newUser.setUser_password(pwdPassword.getText());
            newUser.setUser_type("admin");
            newUser.insertUser();
        }
        toggleNewCompany(false);
        fillCompanies();
        goLogin(newName);
    }

    private void toggleNewCompany(boolean b) {
        btnLogin.setVisible(!b);
        btnLogin.setManaged(!b);
        boxNewCompany.setVisible(b);
        boxNewCompany.setManaged(b);
        if(b) {
            txtNewCompany.requestFocus();
        } else {
            txtUsername.requestFocus();
        }
    }

    private void goLogin(String companyName) {
        // get company from AppCompanies
        AppCompany appCompany = AppCompanyConnector.getCompanyByName(companyName);
        VarComp.setAppCompany(appCompany);

        // set dDBUtils for current company
        DBUtils.setDBLocation(appCompany.getDir_Name());

        // verify user
        User u = UserConnector.loginUser(txtUsername.getText(), pwdPassword.getText());
        if (!u.getUser_id().equals(0)) {
            lblWarning.setVisible(false);
            VarComp.setUser(u);

            //get company info
            VarComp.setCurrentCompany(CompanyConnector.getCompany());

            EmployeeUtils.updateAllEmployeeShares();

            String title = "VarComp - "+VarComp.getCurrentCompany().getCompany_name();
            VarComp.changeScene("varcomp-view.fxml", title, false);
        } else {
            lblWarning.setText("Unsuccessful Login Attempt!");
            lblWarning.setVisible(true);
        }
    }

}
