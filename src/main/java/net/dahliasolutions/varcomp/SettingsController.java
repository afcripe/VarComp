package net.dahliasolutions.varcomp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import net.dahliasolutions.varcomp.connectors.*;
import net.dahliasolutions.varcomp.models.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class SettingsController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private Button btnSetCompany;
    @FXML
    private HBox boxIndicatorCompany;
    @FXML
    private Button btnSetKPI;
    @FXML
    private HBox boxIndicatorKPI;
    @FXML
    private Button btnSetEmployee;
    @FXML
    private HBox boxIndicatorEmployee;
    @FXML
    private Button btnSetUser;
    @FXML
    private HBox boxIndicatorUser;
    @FXML
    private Pane paneCompany;
    @FXML
    private Pane paneKPI;
    @FXML
    private Pane paneEmployee;
    @FXML
    private Pane paneUsers;
    @FXML
    private Button btnLoadCompanyLogo;
    @FXML
    private ImageView imgCompanyLogo;
    @FXML
    private Button btnLoadCompanyIcon;
    @FXML
    private ImageView imgCompanyIcon;
    @FXML
    private CheckBox chkShowCompanyLogo;
    @FXML
    private TextField txtCompanyName;
    @FXML
    private TextField txtSharesIssued;
    @FXML
    private TextField txtSharesPerIssue;
    @FXML
    private TextField txtSharesOutstanding;
    @FXML
    private TextField txtFundingPercentage;
    @FXML
    private TextField txtYearsIssued;
    @FXML
    private Button btnSaveCompany;
    @FXML
    private Button bntNewKPIClass;
    @FXML
    private Button bntDeleteKPIClass;
    @FXML
    private TextField txtFormClassKPI_id;
    @FXML
    private TextField txtFormClassKPI_name;
    @FXML
    private TextField txtFormClassKPI_description;
    @FXML
    private Button btnFormClassKPI_cancel;
    @FXML
    private Button btnFormClassKPI_save;
    @FXML
    private TableView<KPIClass> tblKPIClasses;
    @FXML
    private TableColumn<KPIClass, Integer> tbcClassID;
    @FXML
    private TableColumn<KPIClass, String> tbcClassName;
    @FXML
    private TableColumn<KPIClass, String> tbcClassDescription;
    @FXML
    private TableView<KPIMaster> tblMasterKPIs;
    @FXML
    private TableColumn<KPIMaster, Integer> tbcKPIid;
    @FXML
    private TableColumn<KPIMaster, String> tbcKPICode;
    @FXML
    private TableColumn<KPIMaster, String> tbcKPIDescription;
    @FXML
    private TableColumn<KPIMaster, String> tbcKPIClass;
    @FXML
    private Pane paneFormKPIClass;

    @FXML
    private Label lblEvalNatural;
    @FXML
    private Label lblEvalReverse;

    @FXML
    private TableView<Position> tblPositions;
    @FXML
    private TableColumn<Position, Integer> tbcPositionID;
    @FXML
    private TableColumn<Position, String> tbcPositionName;
    @FXML
    private TableColumn<Position, String> tbcPositionDescription;
    @FXML
    private TableColumn<Position, Integer> tbcPositionShares;

    @FXML
    private TableView<User> tblUsers;
    @FXML
    private TableColumn<User, Integer> tbcUserID;
    @FXML
    private TableColumn<User, String> tbcUserName;

    @FXML
    private Button bntNewUsers;
    @FXML
    private Button bntDeleteUsers;

    @FXML
    private Button btnNewMastKPI;
    @FXML
    private Button btnDeleteMastKPI;


    @FXML
    private Button bntNewPosition;
    @FXML
    private Button bntDeletePosition;

    @FXML
    private Pane paneMasterKPI;

    @FXML
    private TextField txtFormMastKPI_id;
    @FXML
    private TextField txtFormMastKPI_code;
    @FXML
    private TextField txtFormMastKPI_description;
    @FXML
    private ComboBox<String> cmbFormMastKPI_class;
    @FXML
    private ComboBox<String> cmbFormMastKPI_calc;
    @FXML
    private CheckBox chkEvalReverseOrder;
    @FXML
    private TextField txtFormMasterKPI_extraordinary;
    @FXML
    private TextField txtFormMasterKPI_great;
    @FXML
    private TextField txtFormMasterKPI_well;
    @FXML
    private TextField txtFormMasterKPI_needs_improvement;
    @FXML
    private TextField txtFormMasterKPI_not_acceptable;
    @FXML
    private TextField txtFormMasterKPI_poor;
    @FXML
    private TextField txtFormMasterKPI_extraordinary_grade;
    @FXML
    private TextField txtFormMasterKPI_great_grade;
    @FXML
    private TextField txtFormMasterKPI_well_grade;
    @FXML
    private TextField txtFormMasterKPI_needs_improvement_grade;
    @FXML
    private TextField txtFormMasterKPI_not_acceptable_grade;
    @FXML
    private TextField txtFormMasterKPI_poor_grade;
    @FXML
    private TextField txtFormMasterKPI_f1;
    @FXML
    private TextField txtFormMasterKPI_f2;
    @FXML
    private TextField txtFormMasterKPI_f3;
    @FXML
    private TextField txtFormMasterKPI_f4;
    @FXML
    private Button btnFormMasterKPI_cancel;
    @FXML
    private Button btnFormMasterKPI_save;

    @FXML
    private Pane paneUserForm;
    @FXML
    private TextField txtUserID;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ComboBox<String> cmbUserType;
    @FXML
    private Button btnUserCancel;
    @FXML
    private Button btnUserSave;

    @FXML
    private Button bntAddPositionKPI;
    @FXML
    private Button bntRemovePositionKPI;
    @FXML
    private Button btnFormPK_cancel;
    @FXML
    private Button btnFormPK_save;
    @FXML
    private Label lblPositionTotalWeight;

    @FXML
    private HBox boxPositionKPIs;
    @FXML
    private TableView<PositionKPI> tblPositionKPI;
    @FXML
    private TableColumn<PositionKPI, String> tbcPKMasterKPI;
    @FXML
    private TableColumn<PositionKPI, BigDecimal> tbcPKWeight;

    @FXML
    private Pane paneFormPosition;
    @FXML
    private TextField txtFormPosition_id;
    @FXML
    private TextField txtFormPosition_name;
    @FXML
    private TextField txtFormPosition_description;
    @FXML
    private TextField txtFormPosition_shares;
    @FXML
    private Button btnFormPosition_cancel;
    @FXML
    private Button btnFormPosition_save;

    @FXML
    private Pane paneFormPositionKPI;

    @FXML
    private TextField txtFormPKItem_id;
    @FXML
    private TextField txtFormPK_position;
    @FXML
    private ComboBox<String> cmbFormPK_kpi;
    @FXML
    private TextField txtFormPK_weight;

    final FileChooser fcLogo = new FileChooser();

    ObservableList<KPIClass> kpiClassList;
    ObservableList<KPIMaster> kpiMasterList;
    ObservableList<Position> positionList;
    ObservableList<PositionKPI> positionKPIList;
    ObservableList<User> userList;
    ObservableList<CalculationOptions> calcList;

    List<String> filesListInDir = new ArrayList<String>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTitle.setText("Settings");

        kpiClassList = FXCollections.observableArrayList(KPIClassConnector.getKPIClasses());
        kpiMasterList = FXCollections.observableArrayList(KPIMasterConnector.getKPIMasters());
        positionList = FXCollections.observableArrayList(PositionsConnector.getPositions());
        positionKPIList = FXCollections.observableArrayList(PositionKPIConnector.getPositionKPI());
        userList = FXCollections.observableArrayList(UserConnector.getUsers());
        calcList = FXCollections.observableArrayList(CalculationOptionsConnection.getCalculationOptions());

        btnSetCompany.setOnAction(actionEvent -> settingsNave("company"));
        btnSetKPI.setOnAction(actionEvent -> settingsNave("kpi"));
        btnSetEmployee.setOnAction(actionEvent -> settingsNave("employee"));
        btnSetUser.setOnAction(actionEvent -> settingsNave("user"));

/* Company Tab */
        txtCompanyName.setText(VarComp.getCurrentCompany().getCompany_name());
        txtSharesIssued.setText(VarComp.getCurrentCompany().getShares_total().toString());
        txtSharesPerIssue.setText(VarComp.getCurrentCompany().getShares_issued_amount().toString());
        txtYearsIssued.setText(VarComp.getCurrentCompany().getShares_issued_years().toString());
        txtSharesOutstanding.setText(VarComp.getCurrentCompany().getShares_outstanding().toString());
        txtFundingPercentage.setText(VarComp.getCurrentCompany().getFunding_percentage().toString());
        chkShowCompanyLogo.setSelected(VarComp.getCurrentCompany().getCompany_logo_show());
        btnSaveCompany.setOnAction(this::updateCompany);
        btnLoadCompanyLogo.setOnAction(event -> browseLogo());
        btnLoadCompanyIcon.setOnAction(event -> browseIcon());

/* KPI Tab */
    /* KPI Class Table and Buttons */
        bntNewKPIClass.setOnAction(event -> setFormClassKPI(new KPIClass()));
        bntDeleteKPIClass.setOnAction(event -> removeKPIClass());

        tbcClassID.setCellValueFactory(new PropertyValueFactory<>("kpi_class_id"));
        tbcClassName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbcClassName.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcClassDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbcClassDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        tblKPIClasses.setItems(kpiClassList);
        tblKPIClasses.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                KPIClass kpiclass = tblKPIClasses.getSelectionModel().getSelectedItem();
                setFormClassKPI(kpiclass);
            }
        });

        btnFormClassKPI_cancel.setOnAction(event -> {
            clearFormClassKPI();
            hidePaneFormClassKPI();
        });
        btnFormClassKPI_save.setOnAction(event -> saveKPIClass());

    /* KPI Master Table and Buttons */
        btnNewMastKPI.setOnAction(event -> setFormMasterKPI(new KPIMaster()));
        btnDeleteMastKPI.setOnAction(event -> removeKPIMaster());

        tbcKPIid.setCellValueFactory(new PropertyValueFactory<>("kpi_master_id"));
        tbcKPICode.setCellValueFactory(new PropertyValueFactory<>("kpi_code"));
        tbcKPIDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbcKPIClass.setCellValueFactory(param -> {
            String cellValue = param.getValue().getKpi_class().toString();
            for (KPIClass kpiClass : kpiClassList) {
                if (kpiClass.getKpi_class_id().equals(param.getValue().getKpi_class())) {
                    cellValue = kpiClass.getKpi_class_id() + ": " + kpiClass.getName();
                }
            }
            return new SimpleObjectProperty<>(cellValue);
        });
        tblMasterKPIs.setItems(kpiMasterList);
        tblMasterKPIs.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                KPIMaster kpiMaster = tblMasterKPIs.getSelectionModel().getSelectedItem();
                setFormMasterKPI(kpiMaster);
            }
        });

        btnFormMasterKPI_cancel.setOnAction(event -> {
            clearFormMasterKPI();
            hidePaneFormMasterKPI();
        });
        btnFormMasterKPI_save.setOnAction(event -> {
            try {
                saveKPIMaster();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        chkEvalReverseOrder.setOnAction(event -> updateEvalCheckbox());
        cmbFormMastKPI_calc.setOnAction(event -> updateCalcToolTip());

/* Employee Tab */
        bntNewPosition.setOnAction(event -> setFormPosition(new Position()));
        bntDeletePosition.setOnAction(event -> removePosition());

        tbcPositionID.setCellValueFactory(new PropertyValueFactory<>("position_id"));
        tbcPositionName.setCellValueFactory(new PropertyValueFactory<>("position_name"));
        tbcPositionDescription.setCellValueFactory(new PropertyValueFactory<>("position_description"));
        tbcPositionShares.setCellValueFactory(new PropertyValueFactory<>("position_shares"));
        tblPositions.setItems(positionList);

        tblPositions.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()){
                showPositionKPIs(tblPositions.getSelectionModel().getSelectedItem().getPosition_id());
                if(event.getClickCount() == 2){
                    Position position = tblPositions.getSelectionModel().getSelectedItem();
                    setFormPosition(position);
                }
            }
        });

        btnFormPosition_cancel.setOnAction(event -> {
            clearPositionForm();
            hidePositionForm();
        });
        btnFormPosition_save.setOnAction(event -> savePositionForm());

        bntAddPositionKPI.setOnAction(event -> setFormPositionKPI(new PositionKPI()));
        bntRemovePositionKPI.setOnAction(event -> removePositionKPI());

        tbcPKMasterKPI.setCellValueFactory(param -> {
            String cellValue = "";
            for (KPIMaster kpi: kpiMasterList){
                if (kpi.getKpi_master_id().equals(param.getValue().getKpi_master_id())){
                    cellValue = kpi.getKpi_master_id() + ": " + kpi.getKpi_code();
                }
            }
            return new SimpleObjectProperty<>(cellValue);
        });
        tbcPKWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        tblPositionKPI.setItems(positionKPIList);

        tblPositionKPI.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                PositionKPI positionKPI = tblPositionKPI.getSelectionModel().getSelectedItem();
                setFormPositionKPI(positionKPI);
            }
        });

        btnFormPK_cancel.setOnAction(event -> {
            clearFormPositionKPI();
            hideFormPositionKPI();
        });
        btnFormPK_save.setOnAction(event -> savePositionKPI());


/* User Tab */
        bntNewUsers.setOnAction(event -> setFormUser(new User()));
        bntDeleteUsers.setOnAction(event -> removeUser());

        tbcUserID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        tbcUserName.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        tblUsers.setItems(userList);

        tblUsers.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                User user = tblUsers.getSelectionModel().getSelectedItem();
                setFormUser(user);
            }
        });

        cmbUserType.getItems().add("admin");
        cmbUserType.getItems().add("user");

        btnUserCancel.setOnAction(actionEvent -> {
            clearUserForm();
            hideUserForm();
        });
        btnUserSave.setOnAction(actionEvent -> saveUserForm());


    /* Table Methods after init */
        setOnTableEdits();
        settingsNave("company");
    }

    private void setOnTableEdits() {
        tbcClassName.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
            e.getTableView().getItems().get(e.getTablePosition().getRow()).updateKPIClass();
        });
        tbcClassDescription.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDescription(e.getNewValue());
            e.getTableView().getItems().get(e.getTablePosition().getRow()).updateKPIClass();
        });
    }

    private void settingsNave(String location) {
        paneFormKPIClass.setVisible(false);
        paneFormKPIClass.setManaged(false);
        paneMasterKPI.setVisible(false);
        paneMasterKPI.setManaged(false);
        paneFormPosition.setVisible(false);
        paneFormPosition.setManaged(false);
        paneFormPositionKPI.setVisible(false);
        paneFormPositionKPI.setManaged(false);
        paneUserForm.setVisible(false);
        paneUserForm.setManaged(false);

        paneCompany.setVisible(false);
        paneCompany.setManaged(false);
        paneKPI.setVisible(false);
        paneKPI.setManaged(false);
        paneEmployee.setVisible(false);
        paneEmployee.setManaged(false);
        paneUsers.setVisible(false);
        paneUsers.setManaged(false);

        boxIndicatorCompany.setVisible(false);
        boxIndicatorKPI.setVisible(false);
        boxIndicatorEmployee.setVisible(false);
        boxIndicatorUser.setVisible(false);

        switch (location) {
            case "company" -> {
                paneCompany.setVisible(true);
                paneCompany.setManaged(true);
                boxIndicatorCompany.setVisible(true);
                loadCompanyLogo();
                loadCompanyIcon();
            }
            case "kpi" -> {
                paneKPI.setVisible(true);
                paneKPI.setManaged(true);
                boxIndicatorKPI.setVisible(true);
            }
            case "employee" -> {
                paneEmployee.setVisible(true);
                paneEmployee.setManaged(true);
                boxPositionKPIs.setVisible(false);
                tblPositionKPI.setVisible(false);
                boxIndicatorEmployee.setVisible(true);
            }
            case "user" -> {
                paneUsers.setVisible(true);
                paneUsers.setManaged(true);
                boxIndicatorUser.setVisible(true);
            }
        }
    }

    private void updateCompany(ActionEvent actionEvent) {
        VarComp.getCurrentCompany().setCompany_name(txtCompanyName.getText().trim());
        VarComp.getAppCompany().setCompany_name(txtCompanyName.getText().trim());
        try{
            int intSharesIssued = Integer.parseInt(txtSharesIssued.getText());
            VarComp.getCurrentCompany().setShares_total(intSharesIssued);
        } catch (NumberFormatException e) {
            System.out.println("Shares issued is not a number!");
        }
        try{
            int intSharesIssuedAmount = Integer.parseInt(txtSharesPerIssue.getText());
            VarComp.getCurrentCompany().setShares_issued_amount(intSharesIssuedAmount);
        } catch (NumberFormatException e) {
            System.out.println("Shares issued is not a number!");
        }
        try{
            int intSharesIssuedYear = Integer.parseInt(txtYearsIssued.getText());
            VarComp.getCurrentCompany().setShares_issued_years(intSharesIssuedYear);
        } catch (NumberFormatException e) {
            System.out.println("Years shares issued is not a number!");
        }
        try{
            int intSharesOutstanding = Integer.parseInt(txtSharesOutstanding.getText());
            VarComp.getCurrentCompany().setShares_outstanding(intSharesOutstanding);
        } catch (NumberFormatException e) {
            System.out.println("Shares outstanding is not a number!");
        }
        try{
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            String pattern = "#0.0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);
            BigDecimal decFundingPercentage = (BigDecimal) decimalFormat.parse(txtFundingPercentage.getText());
            VarComp.getCurrentCompany().setFunding_percentage(decFundingPercentage);
        } catch (ParseException e) {
            System.out.println("Funding percentage is not a number!");
        }
        VarComp.getCurrentCompany().setCompany_logo_show(chkShowCompanyLogo.isSelected());

        VarComp.getCurrentCompany().updateCompany();
    }

    private void loadCompanyLogo() {
        String logoPath = DBUtils.getCompanyDir()+DBUtils.getFS()+"companyLogo.png";

        File logoFile = new File(logoPath);
        if(logoFile.exists()) {
            try {
                InputStream stream = new FileInputStream(logoFile);
                imgCompanyLogo.setImage(new Image(stream));
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadCompanyIcon() {
        String logoPath = DBUtils.getCompanyDir()+DBUtils.getFS()+"companyIcon.png";

        File logoFile = new File(logoPath);
        if(logoFile.exists()) {
            try {
                InputStream stream = new FileInputStream(logoFile);
                imgCompanyIcon.setImage(new Image(stream));
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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

        imgCompanyLogo.setImage(newLogo);
    }

    private void browseIcon() {
        Image newIcon;
        String logoPath = DBUtils.getCompanyDir()+DBUtils.getFS()+"companyIcon.png";

        fcLogo.setTitle("Select Icon Image");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png");
        fcLogo.getExtensionFilters().add(extFilter);
        File file = fcLogo.showOpenDialog(null);

        if (file == null) {
            return;
        }

        File iconFile = new File(logoPath);
        try {
            InputStream stream = new FileInputStream(file);
            newIcon = new Image(stream);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if(!iconFile.exists()) iconFile.createNewFile();
        }catch (IOException ioE) {
            System.out.println(ioE);
        }
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(newIcon, null), "PNG", iconFile);
        } catch (Exception e) {
            System.out.println(e);
        }

        imgCompanyIcon.setImage(newIcon);
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

    private void removeKPIClass() {
        int selectedID = tblKPIClasses.getSelectionModel().getSelectedIndex();
        KPIClass selectedItem = tblKPIClasses.getSelectionModel().getSelectedItem();
        Boolean itemDeleted = KPIClassConnector.deleteKPIClass(selectedItem.getKpi_class_id());
        if (itemDeleted) {
            tblKPIClasses.getItems().remove(selectedID);
        } else {
            System.out.println("Item not deleted from database.");
        }
    }

    private void setFormClassKPI(KPIClass kpiClass) {
        fillFormMasterKPI(kpiClass);
        showPaneFormClassKPI();
    }
    private void fillFormMasterKPI(KPIClass kpiClass) {
        txtFormClassKPI_id.setText(kpiClass.getKpi_class_id().toString());
        txtFormClassKPI_name.setText(kpiClass.getName());
        txtFormClassKPI_description.setText(kpiClass.getDescription());
    }
    private void showPaneFormClassKPI() {
        System.out.println(tblKPIClasses.getPrefWidth()+" x "+tblKPIClasses.getPrefHeight());
        paneFormKPIClass.setLayoutX(tblKPIClasses.getLayoutX());
        paneFormKPIClass.setLayoutY(tblKPIClasses.getLayoutY());
        paneFormKPIClass.setPrefWidth(tblKPIClasses.getPrefWidth());
        paneFormKPIClass.setPrefHeight(tblKPIClasses.getPrefHeight());
        System.out.println(paneFormKPIClass.getPrefWidth()+" x "+paneFormKPIClass.getPrefHeight());
        paneFormKPIClass.setVisible(true);
        paneFormKPIClass.setManaged(true);
    }

    private void hidePaneFormClassKPI() {
        paneFormKPIClass.setVisible(false);
        paneFormKPIClass.setManaged(false);
    }

    private void clearFormClassKPI() {
        txtFormClassKPI_id.setText("");
        txtFormClassKPI_name.setText("");
        txtFormClassKPI_description.setText("");
        cmbFormMastKPI_class.setValue("");
    }

    private void saveKPIClass() {
        KPIClass kpiClass = new KPIClass(Integer.parseInt(txtFormClassKPI_id.getText()), txtFormClassKPI_name.getText(),
                txtFormClassKPI_description.getText(), false);

        if ( kpiClass.getKpi_class_id().equals(0)) {
            kpiClass.insertKPIClass();
        } else {
            kpiClass.updateKPIClass();
        }
        kpiClassList = FXCollections.observableArrayList(KPIClassConnector.getKPIClasses());
        tblKPIClasses.getItems().removeAll();
        tblKPIClasses.setItems(kpiClassList);
        clearFormClassKPI();
        hidePaneFormClassKPI();
    }

    private void setFormMasterKPI(KPIMaster kpiMaster) {
        fillFormMasterKPI(kpiMaster);
        showPaneFormMasterKPI();
    }
    private void fillFormMasterKPI(KPIMaster kpiMaster) {
        txtFormMastKPI_id.setText(kpiMaster.getKpi_master_id().toString());
        txtFormMastKPI_code.setText(kpiMaster.getKpi_code());
        txtFormMastKPI_description.setText(kpiMaster.getDescription());
        chkEvalReverseOrder.setSelected(kpiMaster.getReverse_scores());
        lblEvalNatural.setVisible(!chkEvalReverseOrder.isSelected());
        lblEvalNatural.setManaged(!chkEvalReverseOrder.isSelected());
        lblEvalReverse.setVisible(chkEvalReverseOrder.isSelected());
        lblEvalReverse.setManaged(chkEvalReverseOrder.isSelected());
        txtFormMasterKPI_extraordinary.setText(kpiMaster.getScore_extraordinary().toString());
        txtFormMasterKPI_great.setText(kpiMaster.getScore_great().toString());
        txtFormMasterKPI_well.setText(kpiMaster.getScore_well().toString());
        txtFormMasterKPI_needs_improvement.setText(kpiMaster.getScore_needs_improvement().toString());
        txtFormMasterKPI_not_acceptable.setText(kpiMaster.getScore_not_acceptable().toString());
        txtFormMasterKPI_poor.setText(kpiMaster.getScore_poor().toString());
        txtFormMasterKPI_extraordinary_grade.setText(kpiMaster.getGrade_extraordinary().toString());
        txtFormMasterKPI_great_grade.setText(kpiMaster.getGrade_great().toString());
        txtFormMasterKPI_well_grade.setText(kpiMaster.getGrade_well().toString());
        txtFormMasterKPI_needs_improvement_grade.setText(kpiMaster.getGrade_needs_improvement().toString());
        txtFormMasterKPI_poor_grade.setText(kpiMaster.getGrade_poor().toString());
        txtFormMasterKPI_not_acceptable_grade.setText(kpiMaster.getGrade_not_acceptable().toString());
        txtFormMasterKPI_f1.setText(kpiMaster.getF1_name());
        txtFormMasterKPI_f2.setText(kpiMaster.getF2_name());
        txtFormMasterKPI_f3.setText(kpiMaster.getF3_name());
        txtFormMasterKPI_f4.setText(kpiMaster.getF4_name());
        // create combobox items
        cmbFormMastKPI_class.getItems().clear();
        for (KPIClass kpiClass: kpiClassList) {
            cmbFormMastKPI_class.getItems().add(kpiClass.getKpi_class_id()+": "+kpiClass.getName());
            if(kpiClass.getKpi_class_id().equals(kpiMaster.getKpi_class())) {
                cmbFormMastKPI_class.setValue(kpiClass.getKpi_class_id()+": "+kpiClass.getName());
            }
        }

        cmbFormMastKPI_calc.getItems().clear();
        for (CalculationOptions cOption: calcList) {
            cmbFormMastKPI_calc.getItems().add(cOption.getCalculation_id()+": "+cOption.getCalculation_name());
            if(cOption.getCalculation_id().equals(kpiMaster.getCalc_instructions())) {
                cmbFormMastKPI_calc.setValue(cOption.getCalculation_id()+": "+cOption.getCalculation_name());
                cmbFormMastKPI_calc.setTooltip(new Tooltip(cOption.getCalculation_description()));
            }
        }
        updateEvalCheckbox();
    }
    private void updateEvalCheckbox() {
        lblEvalNatural.setVisible(!chkEvalReverseOrder.isSelected());
        lblEvalNatural.setManaged(!chkEvalReverseOrder.isSelected());
        lblEvalReverse.setVisible(chkEvalReverseOrder.isSelected());
        lblEvalReverse.setManaged(chkEvalReverseOrder.isSelected());
    }
    private void updateCalcToolTip() {
        if (!cmbFormMastKPI_calc.getValue().equals("")) {
            String[] splitCalc = cmbFormMastKPI_calc.getSelectionModel().getSelectedItem().split(":");

            for (CalculationOptions cOption : calcList) {
                if (cOption.getCalculation_id().equals(Integer.parseInt(splitCalc[0]))) {
                    cmbFormMastKPI_calc.setTooltip(new Tooltip(cOption.getCalculation_description()));
                }
            }
        }
    }

    private void removeKPIMaster() {
        int selectedID = tblMasterKPIs.getSelectionModel().getSelectedIndex();
        KPIMaster selectedItem = tblMasterKPIs.getSelectionModel().getSelectedItem();
        Boolean itemDeleted = KPIMasterConnector.deleteKPIMaster(selectedItem.getKpi_master_id());
        if (itemDeleted) {
            tblMasterKPIs.getItems().remove(selectedID);
        } else {
            System.out.println("Item not deleted from database.");
        }
    }
    private void showPaneFormMasterKPI() {
        paneMasterKPI.setLayoutX(tblMasterKPIs.getLayoutX());
        paneMasterKPI.setLayoutY(tblMasterKPIs.getLayoutY());
        paneMasterKPI.setPrefWidth(tblMasterKPIs.getPrefWidth());
        paneMasterKPI.setPrefHeight(tblMasterKPIs.getPrefHeight());
        paneMasterKPI.setVisible(true);
        paneMasterKPI.setManaged(true);
    }

    private void hidePaneFormMasterKPI() {
        paneMasterKPI.setVisible(false);
        paneMasterKPI.setManaged(false);
    }
    private void clearFormMasterKPI() {
        txtFormMastKPI_id.setText("");
        txtFormMastKPI_code.setText("");
        txtFormMastKPI_description.setText("");
        cmbFormMastKPI_class.setValue("");
        cmbFormMastKPI_calc.setValue("");
        chkEvalReverseOrder.setSelected(false);
        txtFormMasterKPI_extraordinary.setText("");
        txtFormMasterKPI_great.setText("");
        txtFormMasterKPI_well.setText("");
        txtFormMasterKPI_needs_improvement.setText("");
        txtFormMasterKPI_not_acceptable.setText("");
        txtFormMasterKPI_poor.setText("");
        txtFormMasterKPI_extraordinary_grade.setText("");
        txtFormMasterKPI_great_grade.setText("");
        txtFormMasterKPI_well_grade.setText("");
        txtFormMasterKPI_needs_improvement_grade.setText("");
        txtFormMasterKPI_not_acceptable_grade.setText("");
        txtFormMasterKPI_poor_grade.setText("");
        txtFormMasterKPI_f1.setText("");
        txtFormMasterKPI_f2.setText("");
        txtFormMasterKPI_f3.setText("");
        txtFormMasterKPI_f4.setText("");
    }

    private void saveKPIMaster() throws ParseException {
        String[] split = cmbFormMastKPI_class.getSelectionModel().getSelectedItem().split(":");
        String[] splitCalc = cmbFormMastKPI_calc.getSelectionModel().getSelectedItem().split(":");
        BigDecimal decScoreExt;
        BigDecimal decScoreGrt;
        BigDecimal decScoreWell;
        BigDecimal decScoreNI;
        BigDecimal decScorePoor;
        BigDecimal decScoreNA;
        BigDecimal decGradeExt;
        BigDecimal decGradeGrt;
        BigDecimal decGradeWell;
        BigDecimal decGradeNI;
        BigDecimal decGradePoor;
        BigDecimal decGradeNA;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            decScoreExt = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_extraordinary.getText());
            decScoreExt = decScoreExt.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decScoreExt = new BigDecimal("0.00");
            decScoreExt = decScoreExt.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decScoreGrt = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_great.getText());
            decScoreGrt = decScoreGrt.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decScoreGrt = new BigDecimal("0.00");
            decScoreGrt = decScoreExt.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decScoreWell = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_well.getText());
            decScoreWell = decScoreWell.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decScoreWell = new BigDecimal("0.00");
            decScoreWell = decScoreExt.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decScoreNI = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_needs_improvement.getText());
            decScoreNI = decScoreNI.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decScoreNI = new BigDecimal("0.00");
            decScoreNI = decScoreExt.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decScorePoor = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_not_acceptable.getText());
            decScorePoor = decScorePoor.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decScorePoor = new BigDecimal("0.00");
            decScorePoor = decScoreExt.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decScoreNA = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_poor.getText());
            decScoreNA = decScoreNA.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decScoreNA = new BigDecimal("0.00");
            decScoreNA = decScoreExt.setScale(2, RoundingMode.HALF_UP);
        }

        try {
            decGradeExt = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_extraordinary_grade.getText());
            decGradeExt = decGradeExt.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decGradeExt = new BigDecimal("0.00");
            decGradeExt = decGradeExt.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decGradeGrt = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_great_grade.getText());
            decScoreGrt = decGradeGrt.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decGradeGrt = new BigDecimal("0.00");
            decGradeGrt = decGradeGrt.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decGradeWell = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_well_grade.getText());
            decGradeWell = decGradeWell.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decGradeWell = new BigDecimal("0.00");
            decGradeWell = decGradeWell.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decGradeNI = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_needs_improvement_grade.getText());
            decGradeNI = decGradeNI.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decGradeNI = new BigDecimal("0.00");
            decGradeNI = decGradeNI.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decGradePoor = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_poor_grade.getText());
            decGradePoor = decGradePoor.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decGradePoor = new BigDecimal("0.00");
            decGradePoor = decGradePoor.setScale(2, RoundingMode.HALF_UP);
        }
        try {
            decGradeNA = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_not_acceptable_grade.getText());
            decGradeNA = decGradeNA.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            decGradeNA = new BigDecimal("0.00");
            decGradeNA = decGradeNA.setScale(2, RoundingMode.HALF_UP);
        }

        KPIMaster kpiMaster = new KPIMaster(Integer.parseInt(txtFormMastKPI_id.getText()), txtFormMastKPI_code.getText(),
                txtFormMastKPI_description.getText(), Integer.parseInt(split[0]), Integer.parseInt(splitCalc[0]), decScoreExt,
                decScoreGrt, decScoreWell, decScoreNI, decScorePoor, decScoreNA, txtFormMasterKPI_f1.getText(),
                txtFormMasterKPI_f2.getText(), txtFormMasterKPI_f3.getText(), txtFormMasterKPI_f4.getText(), chkEvalReverseOrder.isSelected(),
                decGradeExt, decGradeGrt, decGradeWell, decGradeNI, decGradePoor, decGradeNA);

        if ( kpiMaster.getKpi_master_id().equals(0)) {
            kpiMaster.insertKPIMaster();
        } else {
            kpiMaster.updateKPIMaster();
        }
        kpiMasterList = FXCollections.observableArrayList(KPIMasterConnector.getKPIMasters());
        tblMasterKPIs.getItems().removeAll();
        tblMasterKPIs.setItems(kpiMasterList);
        clearFormMasterKPI();
        hidePaneFormMasterKPI();
    }

    private void setFormUser(User user) {
        fillFormUser(user);
        showFormUser();
    }
    private void fillFormUser(User user) {
        txtUserID.setText(user.getUser_id().toString());
        txtUsername.setText(user.getUser_name());
        txtPassword.setText(user.getUser_password());
        cmbUserType.setValue(user.getUser_type());
    }

    private void showFormUser() {
        paneUserForm.setVisible(true);
        paneUserForm.setManaged(true);
    }

    private void clearUserForm() {
        txtUserID.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbUserType.setValue("");
    }
    private void hideUserForm() {
        paneUserForm.setVisible(false);
        paneUserForm.setManaged(false);
    }
    private void saveUserForm() {
        User user = new User(Integer.parseInt(txtUserID.getText()),
                txtUsername.getText(), txtPassword.getText(),
                cmbUserType.getSelectionModel().getSelectedItem());
        if (user.getUser_id() == 0) {
            user.insertUser();
        } else {
            user.updateUser();
        }
        userList = FXCollections.observableArrayList(UserConnector.getUsers());
        tblUsers.getItems().removeAll();
        tblUsers.setItems(userList);
        clearUserForm();
        hideUserForm();
    }
    private void removeUser() {
        int selectedID = tblUsers.getSelectionModel().getSelectedIndex();
        User selectedItem = tblUsers.getSelectionModel().getSelectedItem();
        Boolean itemDeleted = UserConnector.deleteUser(selectedItem.getUser_id());
        if (itemDeleted) {
            tblUsers.getItems().remove(selectedID);
        } else {
            System.out.println("Item not deleted from database.");
        }
    }


    private void setFormPosition(Position position) {
        fillFormPosition(position);
        showFormPosition();
    }
    private void fillFormPosition(Position position) {
        txtFormPosition_id.setText(Integer.toString(position.getPosition_id()));
        txtFormPosition_name.setText(position.getPosition_name());
        txtFormPosition_description.setText(position.getPosition_description());
        txtFormPosition_shares.setText(Integer.toString(position.getPosition_shares()));
    }

    private void showFormPosition() {
        paneFormPosition.setLayoutX(tblPositions.getLayoutX());
        paneFormPosition.setLayoutY(tblPositions.getLayoutY());
        paneFormPosition.setPrefWidth(tblPositions.getPrefWidth());
        paneFormPosition.setPrefHeight(tblPositions.getPrefHeight());
        paneFormPosition.setVisible(true);
        paneFormPosition.setManaged(true);
    }

    private void clearPositionForm() {
        txtFormPosition_id.setText("");
        txtFormPosition_name.setText("");
        txtFormPosition_description.setText("");
        txtFormPosition_shares.setText("");
    }
    private void hidePositionForm() {
        paneFormPosition.setVisible(false);
        paneFormPosition.setManaged(false);
    }
    private void savePositionForm() {
        Position position = new Position(Integer.parseInt(txtFormPosition_id.getText()),
                txtFormPosition_name.getText(), txtFormPosition_description.getText(),
                Integer.parseInt(txtFormPosition_shares.getText()));
        if (position.getPosition_id() == 0) {
            position.insertPosition();
        } else {
            position.updatePosition();
        }
        positionList = FXCollections.observableArrayList(PositionsConnector.getPositions());
        tblPositions.getItems().removeAll();
        tblPositions.setItems(positionList);
        clearPositionForm();
        hidePositionForm();
    }
    private void removePosition() {
        hidePositionKPIs();
        int selectedID = tblPositions.getSelectionModel().getSelectedIndex();
        Position selectedItem = tblPositions.getSelectionModel().getSelectedItem();
        Boolean itemDeleted = PositionsConnector.deletePosition(selectedItem.getPosition_id());
        if (itemDeleted) {
            tblPositions.getItems().remove(selectedID);
        } else {
            System.out.println("Item not deleted from database.");
        }
    }

    private void showPositionKPIs(Integer positionID) {
        positionKPIList = FXCollections.observableArrayList(PositionKPIConnector.getPositionKPIsPosition(positionID));
        tblPositionKPI.getItems().removeAll();
        tblPositionKPI.setItems(positionKPIList);
        boxPositionKPIs.setVisible(true);
        tblPositionKPI.setVisible(true);
        bntAddPositionKPI.setVisible(true);
        bntRemovePositionKPI.setVisible(true);
        verifyPositionWeight();
    }
    private void hidePositionKPIs() {
        boxPositionKPIs.setVisible(false);
        tblPositionKPI.setVisible(false);
        bntAddPositionKPI.setVisible(false);
        bntRemovePositionKPI.setVisible(false);
    }

    private void setFormPositionKPI(PositionKPI positionKPI) {
        fillFormPositionKPI(positionKPI);
        showFormPositionKPI();
    }
    private void fillFormPositionKPI(PositionKPI positionKPI) {
        Position p = tblPositions.getSelectionModel().getSelectedItem();
        String pName = p.getPosition_id() + ": " + p.getPosition_name();

        txtFormPKItem_id.setText(positionKPI.getItem_id().toString());
        txtFormPK_position.setText(pName);
        txtFormPK_weight.setText(positionKPI.getWeight().toString());

        // create combobox items for KPIs
        cmbFormPK_kpi.getItems().clear();
        for (KPIMaster kpiMaster: kpiMasterList) {
            cmbFormPK_kpi.getItems().add(kpiMaster.getKpi_master_id() + ": " + kpiMaster.getKpi_code());
            if(kpiMaster.getKpi_master_id().equals(positionKPI.getKpi_master_id())) {
                cmbFormPK_kpi.setValue(kpiMaster.getKpi_master_id() + ": " + kpiMaster.getKpi_code());
            }
        }
    }

    private void savePositionKPI() {
        String[] split = cmbFormPK_kpi.getSelectionModel().getSelectedItem().split(":");
        String[] splitName = txtFormPK_position.getText().split(":");
        BigDecimal decWeight;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        try {
            decWeight = (BigDecimal) decimalFormat.parse(txtFormPK_weight.getText());
            decWeight = decWeight.setScale(4, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            System.out.println("Weight is not a number!");
            decWeight = new BigDecimal("100.00");
        }

        Integer classID = KPIMasterConnector.getKPIMaster(Integer.parseInt(split[0])).getKpi_class();

        PositionKPI positionKPI = new PositionKPI(Integer.parseInt(txtFormPKItem_id.getText()),
                Integer.parseInt(splitName[0]), Integer.parseInt(split[0]), classID, decWeight);

        if ( positionKPI.getItem_id().equals(0)) {
            positionKPI.insertPositionKPI();
        } else {
            positionKPI.updatePositionKPI();
        }

        try {
            positionKPIList = FXCollections.observableArrayList(
                    PositionKPIConnector.getPositionKPIsPosition(
                            tblPositions.getSelectionModel().getSelectedItem().getPosition_id()));

        } catch (Exception e) {
            positionKPIList = FXCollections.observableArrayList();
        }

        tblPositionKPI.getItems().removeAll();
        tblPositionKPI.setItems(positionKPIList);
        clearFormPositionKPI();
        hideFormPositionKPI();
        verifyPositionWeight();
    }

    private void verifyPositionWeight() {
        BigDecimal totalWeight = new BigDecimal("0.00");

        for ( PositionKPI pKPI : positionKPIList ){
            totalWeight = totalWeight.add(pKPI.getWeight());
        }
        totalWeight = totalWeight.multiply(new BigDecimal("100.00"));
        totalWeight = totalWeight.setScale(1, RoundingMode.HALF_UP);
        lblPositionTotalWeight.setText(totalWeight.toString()+" %");
    }

    private void removePositionKPI() {
        int selectedID = tblPositionKPI.getSelectionModel().getSelectedIndex();
        PositionKPI selectedItem = tblPositionKPI.getSelectionModel().getSelectedItem();
        Boolean itemDeleted = PositionKPIConnector.deletePositionKPI(selectedItem.getItem_id());
        if (itemDeleted) {
            tblPositionKPI.getItems().remove(selectedID);
        } else {
            System.out.println("Item not deleted from database.");
        }
    }

    private void clearFormPositionKPI() {
        txtFormPKItem_id.setText("");
        txtFormPK_position.setText("");
        txtFormPK_weight.setText("");
        cmbFormPK_kpi.getItems().clear();
    }

    private void showFormPositionKPI() {
        paneFormPositionKPI.setVisible(true);
        paneFormPositionKPI.setManaged(true);
    }

    private void hideFormPositionKPI() {
        paneFormPositionKPI.setVisible(false);
        paneFormPositionKPI.setManaged(false);
    }

}
