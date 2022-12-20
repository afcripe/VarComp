package net.dahliasolutions.varcomp;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.dahliasolutions.varcomp.connectors.AppCompanyConnector;
import net.dahliasolutions.varcomp.connectors.CompanyConnector;
import net.dahliasolutions.varcomp.connectors.UserConnector;
import net.dahliasolutions.varcomp.models.AppCompany;
import net.dahliasolutions.varcomp.models.Company;
import net.dahliasolutions.varcomp.models.User;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class LoginController extends ViewController implements Initializable {

    @FXML
    private BorderPane viewMain;
    @FXML
    private VBox boxLogin;
    @FXML
    private VBox boxCompanySettings;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem menuLogout;
    @FXML
    private MenuItem menuExit;
    @FXML
    private MenuItem menuHelp;
    @FXML
    private MenuItem menuAbout;
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
    private Button btnNewCompanyLogin;

    @FXML
    private Label lblSettingsCompany;

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
    @FXML
    private Label lblConfirmDelete;
    @FXML
    private Label lblConfirmClear;
    @FXML
    private CheckBox chkDeleteCompany;
    @FXML
    private Button btnExportData;
    @FXML
    private Button btnImportData;

    ObservableList<AppCompany> companies;

    List<String> filesListInDir = new ArrayList<String>();

    final FileChooser fcLogo = new FileChooser();
    final FileChooser fcExport = new FileChooser();
    final FileChooser fcImport = new FileChooser();
    boolean isNewCompany = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblStatus.setText("");
        companies = FXCollections.observableArrayList(AppCompanyConnector.getCompanies());

        menuLogout.setOnAction(actionEvent -> VarComp.changeScene("login-view.fxml", "Login", false));
        menuExit.setOnAction(actionEvent -> VarComp.closeApp());
        menuHelp.setOnAction(event -> showHelp());
        menuAbout.setOnAction(event -> showAbout());

        if(System.getProperty("os.name").startsWith("Mac")) {
            //Mac OS, move menubar to System Menu
            menuBar.setUseSystemMenuBar(true);
            // Remove top border pane
            viewMain.getTop().setVisible(false);
        }

        btnSettings.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.COG, "12px"));
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
                goLogin();
            }
        });

        txtNewCompany.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                btnNewCompanyLogin.fire();
            }
        });
        btnNewCompany.setOnAction(event -> createCompany(false));
        btnNewCompanyLogin.setOnAction(event -> createCompany(true));

        btnSettings.setOnAction(event -> openCompSettings());
        btnSettingsClose.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.CLOSE, "14px"));
        btnSettingsClose.setText("");
        btnSettingsClose.setOnAction(event -> closeCompSettings());
        btnLoadCompanyLogo.setOnAction(event -> browseLogo());
        btnClearData.setOnAction(event -> showConfirmDelete("data"));
        btnDeleteCompany.setOnAction(event -> showConfirmDelete("company"));
        btnDeleteCompanyNo.setOnAction(event -> hideConfirmDelete());
        btnDeleteCompanyYes.setOnAction(event -> processConfirmDeleteClear());
        btnExportData.setOnAction(event -> {
            try {
                exportDB();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnImportData.setOnAction(event -> importDB());

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
    }

    private void loadApplicationLogo() {
        String logoPath = DBUtils.getInstallDir()+DBUtils.getFS()+"applicationLogo.png";

        File logoFile = new File(logoPath);
        if(logoFile.exists()) {
            try {
                InputStream stream = new FileInputStream(logoFile);
                imgApplicationLogo.setImage(new Image(stream));
                stream.close();
            } catch (IOException e) {
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
            stream.close();
        } catch (IOException e) {
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

    private void createCompany(boolean login) {
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

        lblStatus.setText("");
        if(login) {
            // Login
            goLogin();
        }
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
        } else if (!u.getUser_type().equals("admin")) {
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

        if (file == null) {
            return;
        }

        File logoFile = new File(logoPath);
        try {
            InputStream stream = new FileInputStream(file);
            newLogo = new Image(stream);
            stream.close();
        } catch (IOException e) {
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
                stream.close();
            } catch (FileNotFoundException e) {
                loadApplicationLogo();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            loadApplicationLogo();
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
            lblSettingsCompany.setText(appCompany.getCompany_name());
            loadCompanyLogo();
        }
    }

    private void goLogin() {
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

    private void showConfirmDelete(String deleteType) {
        boxConfirmDelete.setVisible(true);

        lblConfirmDelete.setVisible(false);
        lblConfirmDelete.setManaged(false);
        lblConfirmClear.setVisible(false);
        lblConfirmClear.setManaged(false);

        if ( deleteType.equals("company") ) {
            lblConfirmDelete.setVisible(true);
            lblConfirmDelete.setManaged(true);
            chkDeleteCompany.setSelected(true);
        } else {
            lblConfirmClear.setVisible(true);
            lblConfirmClear.setManaged(true);
            chkDeleteCompany.setSelected(false);
        }
    }
    private void hideConfirmDelete() {
        boxConfirmDelete.setVisible(false);
    }

    private void processConfirmDeleteClear() {
        if (chkDeleteCompany.isSelected() ) {
            deleteCompany();
        } else {
            clearData();
        }
    }

    private void deleteCompany() {
        AppCompany appCompany = VarComp.getAppCompany();
        File compDir = new File(DBUtils.getCompanyDir());

        loadApplicationLogo();

        // remove company from appDatabase
        appCompany.removeAppCompany();
        fillCompanies();

        // delete dir and contents
        deleteDir(compDir);

        // logout
        VarComp.changeScene("login-view.fxml", "Login", false);
    }
    private void clearData() {
        // drop the table data
        DBSetup.dropCompanyData(VarComp.getAppCompany().getCompany_id());

        // logout
        VarComp.changeScene("login-view.fxml", "Login", false);
    }

    private void exportDB() throws IOException {
        String dbPath = DBUtils.getCompanyDir()+DBUtils.getFS()+DBUtils.getSafeName()+"_dump.sql";
        String zipFolder = DBUtils.getCompanyDir()+DBUtils.getFS()+DBUtils.getSafeName()+".zip";

        File logoFile = new File(DBUtils.getCompanyDir()+DBUtils.getFS()+"companyLogo.png");
        File iconFile = new File(DBUtils.getCompanyDir()+DBUtils.getFS()+"companyIcon.png");
        File sqlFile = new File(dbPath);

        String dumpFile;
        fcExport.setTitle("Select Export Location");
        File file = fcExport.showSaveDialog(null);

        // Dump db to SQL
        dumpFile = DBUtils.getSQLDump();

        // create folder to zip
        Path folderToZip = Paths.get(DBUtils.getCompanyDir()+DBUtils.getFS()+DBUtils.getSafeName()+"_zip");
        Files.createDirectory(folderToZip);

        //copy files to new folder
        if(logoFile.exists()) copyFile(logoFile.getPath(), folderToZip+DBUtils.getFS()+"companyLogo.png");
        if(iconFile.exists()) copyFile(iconFile.getPath(), folderToZip+DBUtils.getFS()+"companyIcon.png");
        if(sqlFile.exists()) copyFile(sqlFile.getPath(), folderToZip+DBUtils.getFS()+DBUtils.getSafeName()+"_dump.sql");

        // zip folder
        zipDirectory(new File(folderToZip.toUri()), zipFolder);

        // Fix the extension
        String expFile = file.getPath();
        if(!file.getPath().endsWith(".zip")) expFile = expFile+".zip";
        // try to copy
        if (!dumpFile.isEmpty()) {
            try {
                copyFile(zipFolder, expFile);
            } catch (IOException ioException) {
                System.out.println(ioException);
            }
        }

        /* cleanup company directory */
        if(sqlFile.exists()) sqlFile.delete();
        if(new File(zipFolder).exists()) new File(zipFolder).delete();
        if(folderToZip.toFile().exists()) deleteDir(folderToZip.toFile());
    }

    private void importDB() {
        String dbPath = DBUtils.getCompanyDir()+DBUtils.getFS()+DBUtils.getSafeName()+"_dump.sql";

        fcImport.setTitle("Select Export Location");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("VarComp ZIP File", "*.zip");
        fcImport.getExtensionFilters().add(extFilter);
        File file = fcImport.showOpenDialog(null);

        // unzip tho the company directory
        unZipFiles(file.getPath(), DBUtils.getCompanyDir());

        //restore the database
        DBUtils.insertSQLDump(new File(dbPath));

        //cleanup dump file
        File dbFile = new File(dbPath);
        if(dbFile.exists()) dbFile.delete();

        // update the companyID
        CompanyConnector.updateCompanyID(VarComp.getAppCompany().getCompany_id());

        // update appCompany name
        AppCompanyConnector.updateCompany(VarComp.getAppCompany().getCompany_id(), CompanyConnector.getCompany().getCompany_name());

        // logout
        VarComp.changeScene("login-view.fxml", "Login", false);
    }

    public static void copyFile(String src, String dest) throws IOException {
        File targetFile = new File(dest);
        if(targetFile.exists()) targetFile.delete();
        Files.copy(Paths.get(src), Paths.get(dest));
    }
    private void zipDirectory(File dir, String zipDirName) {
        try {
            populateFilesList(dir);
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir){
                System.out.println("Zipping "+filePath);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void populateFilesList(File dir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(file);
        }
    }
    private void deleteDir(File dir) {
        try {
            populateFilesList(dir);
            // delete each file in directory
            for(String filePath : filesListInDir){
                System.out.println("Deleting "+filePath);
                new File(filePath).delete();
            }
            // now delete the folder
            dir.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unZipFiles(String zipFilePath, String destDir) {
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void showAbout() {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(VarComp.class.getResource("aboutView.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 500, 400));
        stage.setTitle("About VarComp");
        stage.show();
    }

    private void showHelp() {
        Stage stage = new Stage();
        Parent root = null;
        FXMLLoader loader;

        try {
            loader = new FXMLLoader(VarComp.class.getResource("helpView.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle("Help");
        stage.show();
    }

}
