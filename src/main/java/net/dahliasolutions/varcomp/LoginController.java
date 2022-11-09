package net.dahliasolutions.varcomp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.dahliasolutions.varcomp.connectors.CompanyConnector;
import net.dahliasolutions.varcomp.connectors.UserConnector;
import net.dahliasolutions.varcomp.models.Company;
import net.dahliasolutions.varcomp.models.User;

import java.net.URISyntaxException;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnLogin.disableProperty().bind(txtUsername.textProperty().isEmpty().or(pwdPassword.textProperty().isEmpty()));

        txtUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    btnLogin.fire();
                }
            }
        });
        pwdPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    btnLogin.fire();
                }
            }
        });

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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

                        NavigationUtils.changeScene(event, "varcomp-view.fxml", "VarComp", false);
                    } else {
                        lblWarning.setText("Unsuccessful Login Attempt!");
                        lblWarning.setVisible(true);
                    }
                }
            }
        });

        ArrayList<Company> companies = CompanyConnector.getCompanies();
        companies.forEach(company -> choiceCompanies.getItems().add(company.getCompany_name()));
        choiceCompanies.setValue(choiceCompanies.getItems().get(0));

    }

}
