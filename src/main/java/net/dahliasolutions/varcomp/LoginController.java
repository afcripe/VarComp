package net.dahliasolutions.varcomp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import net.dahliasolutions.varcomp.connectors.AppCompanyConnector;
import net.dahliasolutions.varcomp.connectors.CompanyConnector;
import net.dahliasolutions.varcomp.connectors.UserConnector;
import net.dahliasolutions.varcomp.models.AppCompany;
import net.dahliasolutions.varcomp.models.Company;
import net.dahliasolutions.varcomp.models.KPIClass;
import net.dahliasolutions.varcomp.models.User;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicTableUI;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends ViewController implements Initializable {

    @FXML
    private VBox boxLogin;
    @FXML
    private VBox boxCompanySettings;
    @FXML
    private ImageView imgApplicationLogo;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private Label lblWarning;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSettings;
    @FXML
    private ComboBox<String> choiceCompanies;
    @FXML
    private Label lblStatus;
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

    @FXML
    private Button btnSettingsClose;
    @FXML
    private Button btnLoadCompanyLogo;
    @FXML
    private Button btnClearData;
    @FXML
    private Button btnDeleteCompany;
    @FXML
    private VBox boxConfirmDelete;
    @FXML
    private Button btnDeleteCompanyNo;
    @FXML
    private Button btnDeleteCompanyYes;

    ObservableList<AppCompany> companies;

    final FileChooser fcLogo = new FileChooser();
    boolean isNewCompany = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblStatus.setText("");
        companies = FXCollections.observableArrayList(AppCompanyConnector.getCompanies());

        btnLogin.disableProperty().bind(txtUsername.textProperty().isEmpty().or(pwdPassword.textProperty().isEmpty()));
        btnSettings.disableProperty().bind(txtUsername.textProperty().isEmpty().or(pwdPassword.textProperty().isEmpty()));
        lblDBL.setText(DBUtils.getAppDBLocation());
        lblVersion.setText("VarComp v: "+DBUtils.getAppVersion());

        txtUsername.setOnKeyPressed(keyEvent -> {
            lblStatus.setText("");
            lblWarning.setVisible(false);
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                btnLogin.fire();
            }
        });
        pwdPassword.setOnKeyPressed(keyEvent -> {
            lblStatus.setText("");
            lblWarning.setVisible(false);
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                btnLogin.fire();
            }
        });
        choiceCompanies.setOnKeyPressed(keyEvent -> {
            lblStatus.setText("");
            lblWarning.setVisible(false);
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                btnLogin.fire();
            }
        });
        choiceCompanies.setOnAction(event -> selectCompany());

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

        imgApplicationLogo.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                browseApplicationLogo();
            }
        });

        btnSettings.setOnAction(event -> openCompSettings());
        btnSettingsClose.setOnAction(event -> closeCompSettings());
        btnLoadCompanyLogo.setOnAction(event -> browseLogo());
        btnDeleteCompany.setOnAction(event -> showConfirmDelete());
        btnDeleteCompanyNo.setOnAction(event -> hideConfirmDelete());

        boxLogin.setManaged(true);
        boxLogin.setVisible(true);
        boxCompanySettings.setManaged(false);
        boxCompanySettings.setVisible(false);
        fillCompanies();

    }

    private void fillCompanies() {
        companies = FXCollections.observableArrayList(AppCompanyConnector.getCompanies());
        companies.add(new AppCompany());

        choiceCompanies.getItems().removeAll();
        companies.forEach(company -> choiceCompanies.getItems().add(company.getCompany_name()));
        choiceCompanies.setValue(choiceCompanies.getItems().get(0));
        selectCompany();
        loadCompanyLogo();
    }

    private void loadApplicationLogo() {
        String logoPath = DBUtils.getInstallDir()+DBUtils.getFS()+"applicationLogo.png";

        File logoFile = new File(logoPath);
        if(logoFile.exists()) {
            try {
                InputStream stream = new FileInputStream(logoFile);
                imgApplicationLogo.setImage(new Image(stream));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void browseApplicationLogo() {
        Image newLogo;
        String logoPath = DBUtils.getInstallDir()+DBUtils.getFS()+"applicationLogo.png";

        fcLogo.setTitle("Select Logo Image");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png");
        fcLogo.getExtensionFilters().add(extFilter);
        File file = fcLogo.showOpenDialog(null);

        if (file == null) {
            return;
        }
        File logoFile = new File(logoPath);
        try {
            InputStream stream = new FileInputStream(file);
            newLogo = new Image(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if(!logoFile.exists()) logoFile.createNewFile();
        }catch (IOException ioE) {
            System.out.println(ioE);
        }
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(newLogo, null), "PNG", logoFile);
        } catch (Exception e) {
            System.out.println(e);
        }

        imgApplicationLogo.setImage(newLogo);
    }

    private void createCompany() {
        lblStatus.setText("Creating New Company");
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

        lblStatus.setText("Setting Up the Database");
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
        // update the company Choises and select the new company
        toggleNewCompany(false);
        fillCompanies();
        choiceCompanies.setValue(newName);
        selectCompany();

        // Login
        goLogin(newName);
        lblStatus.setText("");
    }

    private void closeCompSettings() {
        boxLogin.setVisible(true);
        boxLogin.setManaged(true);
        boxCompanySettings.setVisible(false);
        boxCompanySettings.setManaged(false);
    }

    private void openCompSettings() {
        // verify user
        User u = UserConnector.loginUser(txtUsername.getText(), pwdPassword.getText());
        if (u.getUser_id().equals(0)) {
            lblWarning.setText("Incorrect username or password!");
            lblWarning.setVisible(true);
            return;
        } else if (u.getUser_type().equals("admin")) {
            lblWarning.setText("User must be an admin!");
            lblWarning.setVisible(true);
            return;
        } else {
            lblWarning.setText("");
            lblWarning.setVisible(false);
        }
        boxLogin.setVisible(false);
        boxLogin.setManaged(false);
        boxCompanySettings.setVisible(true);
        boxCompanySettings.setManaged(true);
        boxConfirmDelete.setVisible(false);
    }

    private void browseLogo() {
        Image newLogo;
        String logoPath = DBUtils.getCompanyDir()+DBUtils.getFS()+"companyLogo.png";

        fcLogo.setTitle("Select Logo Image");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png");
        fcLogo.getExtensionFilters().add(extFilter);
        File file = fcLogo.showOpenDialog(null);

        File logoFile = new File(logoPath);
        try {
            InputStream stream = new FileInputStream(file);
            newLogo = new Image(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if(!logoFile.exists()) logoFile.createNewFile();
        }catch (IOException ioE) {
            System.out.println(ioE);
        }
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(newLogo, null), "PNG", logoFile);
        } catch (Exception e) {
            System.out.println(e);
        }

        imgApplicationLogo.setImage(newLogo);
    }

    private void loadCompanyLogo() {
        String logoPath = DBUtils.getCompanyDir()+DBUtils.getFS()+"companyLogo.png";

        File logoFile = new File(logoPath);
        if(logoFile.exists()) {
            try {
                InputStream stream = new FileInputStream(logoFile);
                imgApplicationLogo.setImage(new Image(stream));
            } catch (FileNotFoundException e) {
                loadApplicationLogo();
            }
        }
    }

    private void toggleNewCompany(boolean b) {
        btnLogin.setVisible(!b);
        btnLogin.setManaged(!b);
        btnSettings.setVisible(!b);
        btnSettings.setManaged(!b);
        boxNewCompany.setVisible(b);
        boxNewCompany.setManaged(b);
        if(b) {
            txtNewCompany.requestFocus();
        } else {
            txtUsername.requestFocus();
        }
    }

    private void selectCompany() {
        String selectedCompany = choiceCompanies.getSelectionModel().getSelectedItem();

        if (selectedCompany.equals("New Company")) {
            isNewCompany = true;
            btnSettings.setVisible(false);
            loadApplicationLogo();
        } else {
            // get company from AppCompanies
            lblStatus.setText("Retrieving Company Data");
            AppCompany appCompany = AppCompanyConnector.getCompanyByName(selectedCompany);
            VarComp.setAppCompany(appCompany);

            // set DBUtils for current company
            DBUtils.setDBLocation(appCompany.getDir_Name());
            lblStatus.setText("");
            isNewCompany = false;
            btnSettings.setVisible(true);
            loadCompanyLogo();
        }
    }

    private void goLogin(String companyName) {
        //update company if needed
        lblStatus.setText("Checking for Database Updates");
        DBSetup.initializeDB();

        // verify user
        User u = UserConnector.loginUser(txtUsername.getText(), pwdPassword.getText());
        lblStatus.setText("Verifying User Info");
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
        lblStatus.setText("");
    }

    private void showConfirmDelete() {
        boxConfirmDelete.setVisible(true);
    }
    private void hideConfirmDelete() {
        boxConfirmDelete.setVisible(false);
    }

}
