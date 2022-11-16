package net.dahliasolutions.varcomp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.dahliasolutions.varcomp.connectors.*;
import net.dahliasolutions.varcomp.models.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;


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
    private Label lblEmployeeTitle;
    @FXML
    private Label lblUsersTitle;
    @FXML
    private TextField txtCompanyName;
    @FXML
    private CheckBox chkShowCompanyLogo;
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
    private Button btnLoadCompanyLogo;
    @FXML
    private Button btnLoadCompanyIcon;
    @FXML
    private ImageView imgCompanyLogo;
    @FXML
    private ImageView imgCompanyIcon;
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
    private CheckBox chkFormClassKPI_auto;
    @FXML
    private Button btnFormClassKPI_cancel;
    @FXML
    private Button btnFormClassKPI_save;
    @FXML
    private Label iconCompanySaveSuccess;
    @FXML
    private TableView<KPIClass> tblKPIClasses;
    @FXML
    private TableColumn<KPIClass, Integer> tbcClassID;
    @FXML
    private TableColumn<KPIClass, String> tbcClassName;
    @FXML
    private TableColumn<KPIClass, String> tbcClassDescription;
    @FXML
    private TableColumn<KPIClass, CheckBox> tbcClassAutoFill;
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
    private ComboBox cmbFormMastKPI_class;
    @FXML
    private ComboBox cmbFormMastKPI_calc;
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
    private ComboBox cmbUserType;
    @FXML
    private Button btnUserCancel;
    @FXML
    private Button btnUserSave;

    @FXML
    private Label lblEmployeeTitlePK;
    @FXML
    private Button bntAddPositionKPI;
    @FXML
    private Button bntRemovePositionKPI;
    @FXML
    private Button btnFormPK_cancel;
    @FXML
    private Button btnFormPK_save;

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
    private ComboBox cmbFormPK_kpi;
    @FXML
    private TextField txtFormPK_weight;

    FileChooser fileChooserLogo = new FileChooser();
    FileChooser fileChooserIcon = new FileChooser();

    ObservableList<KPIClass> kpiClassList;
    ObservableList<KPIMaster> kpiMasterList;
    ObservableList<Position> positionList;
    ObservableList<PositionKPI> positionKPIList;
    ObservableList<User> userList;
    ObservableList<CalculationOptions> calcList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


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
//        chkShowCompanyLogo.setSelected(VarComp.getCurrentCompany().getCompany_logo_show());
        txtSharesIssued.setText(VarComp.getCurrentCompany().getShares_total().toString());
        txtSharesPerIssue.setText(VarComp.getCurrentCompany().getShares_issued_amount().toString());
        txtYearsIssued.setText(VarComp.getCurrentCompany().getShares_issued_years().toString());
        txtSharesOutstanding.setText(VarComp.getCurrentCompany().getShares_outstanding().toString());
        txtFundingPercentage.setText(VarComp.getCurrentCompany().getFunding_percentage().toString());
        btnSaveCompany.setOnAction(actionEvent -> updateCompany(actionEvent));
//        btnLoadCompanyLogo.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                File file = fileChooserLogo.showOpenDialog(new Stage());
//                try {
//                    imgCompanyLogo.setImage(new Image(file.getAbsolutePath()));
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//                URI destination = URI.create(String.valueOf(VarComp.class.getResource("companyLogo.png")));
//                File destFile = new File(destination);
//                try {
//                    Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        btnLoadCompanyIcon.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                File file = fileChooserIcon.showOpenDialog(new Stage());
//                try {
//                    imgCompanyIcon.setImage(new Image(file.getAbsolutePath()));
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });

/* KPI Tab */
    /* KPI Class Table and Buttons */
        bntNewKPIClass.setOnAction(event -> setFormClassKPI(new KPIClass()));
        bntDeleteKPIClass.setOnAction(event -> removeKPIClass());

        tbcClassID.setCellValueFactory(new PropertyValueFactory<KPIClass, Integer>("kpi_class_id"));
        tbcClassName.setCellValueFactory(new PropertyValueFactory<KPIClass, String>("name"));
        tbcClassName.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcClassDescription.setCellValueFactory(new PropertyValueFactory<KPIClass, String>("description"));
        tbcClassDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        tbcClassAutoFill.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<KPIClass, CheckBox>, ObservableValue<CheckBox>>() {
            @Override
            public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<KPIClass, CheckBox> param) {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(param.getValue().getAuto_fill_employees());
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });
        tblKPIClasses.setItems(kpiClassList);
        tblKPIClasses.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    KPIClass kpiclass = (KPIClass) tblKPIClasses.getSelectionModel().getSelectedItem();
                    setFormClassKPI(kpiclass);
                }
            }
        });

        btnFormClassKPI_cancel.setOnAction(event -> {
            clearFormClassKPI();
            hidePaneFormClassKPI();
        });
        btnFormClassKPI_save.setOnAction(event -> {
            saveKPIClass();
        });

    /* KPI Master Table and Buttons */
        btnNewMastKPI.setOnAction(event -> setFormMasterKPI(new KPIMaster()));
        btnDeleteMastKPI.setOnAction(event -> removeKPIMaster());

        tbcKPIid.setCellValueFactory(new PropertyValueFactory<KPIMaster, Integer>("kpi_master_id"));
        tbcKPICode.setCellValueFactory(new PropertyValueFactory<KPIMaster, String>("kpi_code"));
        tbcKPIDescription.setCellValueFactory(new PropertyValueFactory<KPIMaster, String>("description"));
        tbcKPIClass.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<KPIMaster, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<KPIMaster, String> param) {
                String cellValue = param.getValue().getKpi_class().toString();
                for (KPIClass kpiClass: kpiClassList) {
                    if(kpiClass.getKpi_class_id() == param.getValue().getKpi_class()) {
                        cellValue = kpiClass.getKpi_class_id()+": "+kpiClass.getName();
                    }
                }
                return new SimpleObjectProperty<String>(cellValue);
            }
        });
        tblMasterKPIs.setItems(kpiMasterList);
        tblMasterKPIs.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    KPIMaster kpiMaster = (KPIMaster) tblMasterKPIs.getSelectionModel().getSelectedItem();
                    setFormMasterKPI(kpiMaster);
                }
            }
        });

        btnFormMasterKPI_cancel.setOnAction(event -> {
            clearFormMasterKPI();
            hidePaneFormMasterKPI();
        });
        btnFormMasterKPI_save.setOnAction(event -> {
            saveKPIMaster();
        });

/* Employee Tab */
        bntNewPosition.setOnAction(event -> setFormPosition(new Position()));
        bntDeletePosition.setOnAction(event -> removePosition());

        tbcPositionID.setCellValueFactory(new PropertyValueFactory<Position, Integer>("position_id"));
        tbcPositionName.setCellValueFactory(new PropertyValueFactory<Position, String>("position_name"));
        tbcPositionDescription.setCellValueFactory(new PropertyValueFactory<Position, String>("position_description"));
        tbcPositionShares.setCellValueFactory(new PropertyValueFactory<Position, Integer>("position_shares"));
        tblPositions.setItems(positionList);

        tblPositions.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown()){
                    showPositionKPIs(tblPositions.getSelectionModel().getSelectedItem().getPosition_id());
                    if(event.getClickCount() == 2){
                        Position position = (Position) tblPositions.getSelectionModel().getSelectedItem();
                        setFormPosition(position);
                    }
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

        tbcPKMasterKPI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PositionKPI, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TableColumn.CellDataFeatures<PositionKPI, String> param) {
                   String cellValue = "";
                   for (KPIMaster kpi: kpiMasterList){
                       if (kpi.getKpi_master_id() == param.getValue().getKpi_master_id()){
                           cellValue = kpi.getKpi_master_id() + ": " + kpi.getKpi_code();
                       }
                   }
                   return new SimpleObjectProperty<String>(cellValue);
               }
           });
        tbcPKWeight.setCellValueFactory(new PropertyValueFactory<PositionKPI, BigDecimal>("weight"));
        tblPositionKPI.setItems(positionKPIList);

        tblPositionKPI.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    PositionKPI positionKPI = (PositionKPI) tblPositionKPI.getSelectionModel().getSelectedItem();
                    setFormPositionKPI(positionKPI);
                }
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

        tbcUserID.setCellValueFactory(new PropertyValueFactory<User, Integer>("user_id"));
        tbcUserName.setCellValueFactory(new PropertyValueFactory<User, String>("user_name"));
        tblUsers.setItems(userList);

        tblUsers.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    User user = (User) tblUsers.getSelectionModel().getSelectedItem();
                    setFormUser(user);
                }
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
        paneMasterKPI.setVisible(false);
        paneFormPosition.setVisible(false);
        paneFormPositionKPI.setVisible(false);
        paneUserForm.setVisible(false);

        paneCompany.setVisible(false);
        paneKPI.setVisible(false);
        paneEmployee.setVisible(false);
        paneUsers.setVisible(false);

        boxIndicatorCompany.setVisible(false);
        boxIndicatorKPI.setVisible(false);
        boxIndicatorEmployee.setVisible(false);
        boxIndicatorUser.setVisible(false);

        switch (location){
            case "company":
                paneCompany.setVisible(true);
                boxIndicatorCompany.setVisible(true);
                break;
            case "kpi":
                paneKPI.setVisible(true);
                boxIndicatorKPI.setVisible(true);
                break;
            case "employee":
                paneEmployee.setVisible(true);
                boxPositionKPIs.setVisible(false);
                tblPositionKPI.setVisible(false);
                boxIndicatorEmployee.setVisible(true);
                break;
            case "user":
                paneUsers.setVisible(true);
                boxIndicatorUser.setVisible(true);
                break;

        }
    }

    private void updateCompany(ActionEvent actionEvent) {
        VarComp.getCurrentCompany().setCompany_name(txtCompanyName.getText().trim());
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
//        VarComp.getCurrentCompany().setCompany_logo_show(chkShowCompanyLogo.isSelected());

        Boolean success = CompanyConnector.updateCompany(VarComp.getCurrentCompany());
        System.out.println(success);
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
        chkFormClassKPI_auto.setSelected(kpiClass.getAuto_fill_employees());
    }
    private void showPaneFormClassKPI() {
        System.out.println(tblKPIClasses.getPrefWidth()+" x "+tblKPIClasses.getPrefHeight());
        paneFormKPIClass.setLayoutX(tblKPIClasses.getLayoutX());
        paneFormKPIClass.setLayoutY(tblKPIClasses.getLayoutY());
        paneFormKPIClass.setPrefWidth(tblKPIClasses.getPrefWidth());
        paneFormKPIClass.setPrefHeight(tblKPIClasses.getPrefHeight());
        System.out.println(paneFormKPIClass.getPrefWidth()+" x "+paneFormKPIClass.getPrefHeight());
        paneFormKPIClass.setVisible(true);
    }

    private void hidePaneFormClassKPI() {
        paneFormKPIClass.setVisible(false);
    }

    private void clearFormClassKPI() {
        txtFormClassKPI_id.setText("");
        txtFormClassKPI_name.setText("");
        txtFormClassKPI_description.setText("");
        cmbFormMastKPI_class.setValue("");
        chkFormClassKPI_auto.setSelected(false);
    }

    private void saveKPIClass() {
        KPIClass kpiClass = new KPIClass(Integer.parseInt(txtFormClassKPI_id.getText()), txtFormClassKPI_name.getText(),
                txtFormClassKPI_description.getText(), chkFormClassKPI_auto.isSelected());

        if ( kpiClass.getKpi_class_id().equals(0)) {
            Integer i = kpiClass.insertKPIClass();
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
        txtFormMasterKPI_extraordinary.setText(kpiMaster.getScore_extraordinary().toString());
        txtFormMasterKPI_great.setText(kpiMaster.getScore_great().toString());
        txtFormMasterKPI_well.setText(kpiMaster.getScore_well().toString());
        txtFormMasterKPI_needs_improvement.setText(kpiMaster.getScore_needs_improvement().toString());
        txtFormMasterKPI_not_acceptable.setText(kpiMaster.getScore_not_acceptable().toString());
        txtFormMasterKPI_poor.setText(kpiMaster.getScore_poor().toString());
        txtFormMasterKPI_f1.setText(kpiMaster.getF1_name());
        txtFormMasterKPI_f2.setText(kpiMaster.getF2_name());
        txtFormMasterKPI_f3.setText(kpiMaster.getF3_name());
        txtFormMasterKPI_f4.setText(kpiMaster.getF4_name());
        // create combobox items
        cmbFormMastKPI_class.getItems().clear();
        String cb = "";
        for (KPIClass kpiClass: kpiClassList) {
            cmbFormMastKPI_class.getItems().add(kpiClass.getKpi_class_id()+": "+kpiClass.getName());
            if(kpiClass.getKpi_class_id() == kpiMaster.getKpi_class()) {
                cb = kpiClass.getKpi_class_id()+": "+kpiClass.getName();
            }
        }
        cmbFormMastKPI_class.setValue(cb);

        cmbFormMastKPI_calc.getItems().clear();
        String cbCalc = "";
        for (CalculationOptions cOption: calcList) {
            cmbFormMastKPI_calc.getItems().add(cOption.getCalculation_id()+": "+cOption.getCalculation_name());
            if(cOption.getCalculation_id() == kpiMaster.getCalc_instructions()) {
                cbCalc = cOption.getCalculation_id()+": "+cOption.getCalculation_name();
            }
        }
        cmbFormMastKPI_calc.setValue(cbCalc);

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
    }

    private void hidePaneFormMasterKPI() {
        paneMasterKPI.setVisible(false);
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
        txtFormMasterKPI_f1.setText("");
        txtFormMasterKPI_f2.setText("");
        txtFormMasterKPI_f3.setText("");
        txtFormMasterKPI_f4.setText("");
    }

    private void saveKPIMaster() {
        String split[] = cmbFormMastKPI_class.getSelectionModel().getSelectedItem().toString().split(":");
        String splitCalc[] = cmbFormMastKPI_calc.getSelectionModel().getSelectedItem().toString().split(":");
        BigDecimal decScoreExt;
        BigDecimal decScoreGrt;
        BigDecimal decScoreWell;
        BigDecimal decScoreNI;
        BigDecimal decScorePoor;
        BigDecimal decScoreNA;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        try {
            decScoreExt = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_extraordinary.getText());
            decScoreExt = decScoreExt.setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            System.out.println("Funding percentage is not a number!");
            decScoreExt = new BigDecimal(100.00);
        }
        try {
            decScoreGrt = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_great.getText());
            decScoreGrt = decScoreGrt.setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            System.out.println("Funding percentage is not a number!");
            decScoreGrt = new BigDecimal(100.00);
        }
        try {
            decScoreWell = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_well.getText());
            decScoreWell = decScoreWell.setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            System.out.println("Funding percentage is not a number!");
            decScoreWell = new BigDecimal(100.00);
        }
        try {
            decScoreNI = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_needs_improvement.getText());
            decScoreNI = decScoreNI.setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            System.out.println("Funding percentage is not a number!");
            decScoreNI = new BigDecimal(100.00);
        }
        try {
            decScorePoor = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_not_acceptable.getText());
            decScorePoor = decScorePoor.setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            System.out.println("Funding percentage is not a number!");
            decScorePoor = new BigDecimal(100.00);
        }
        try {
            decScoreNA = (BigDecimal) decimalFormat.parse(txtFormMasterKPI_poor.getText());
            decScoreNA = decScoreNA.setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            System.out.println("Funding percentage is not a number!");
            decScoreNA = new BigDecimal(100.00);
        }
        KPIMaster kpiMaster = new KPIMaster(Integer.parseInt(txtFormMastKPI_id.getText()), txtFormMastKPI_code.getText(),
                txtFormMastKPI_description.getText(), Integer.parseInt(split[0]), Integer.parseInt(splitCalc[0]), decScoreExt,
                decScoreGrt, decScoreWell, decScoreNI, decScorePoor, decScoreNA, txtFormMasterKPI_f1.getText(),
                txtFormMasterKPI_f2.getText(), txtFormMasterKPI_f3.getText(), txtFormMasterKPI_f4.getText(), chkEvalReverseOrder.isSelected());

        if ( kpiMaster.getKpi_master_id().equals(0)) {
            Integer i = kpiMaster.insertKPIMaster();
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
    }

    private void clearUserForm() {
        txtUserID.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbUserType.setValue("");
    }
    private void hideUserForm() {
        paneUserForm.setVisible(false);
    }
    private void saveUserForm() {
        User user = new User(Integer.parseInt(txtUserID.getText()),
                txtUsername.getText(), txtPassword.getText(),
                cmbUserType.getSelectionModel().getSelectedItem().toString());
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
    }

    private void clearPositionForm() {
        txtFormPosition_id.setText("");
        txtFormPosition_name.setText("");
        txtFormPosition_description.setText("");
        txtFormPosition_shares.setText("");
    }
    private void hidePositionForm() {
        paneFormPosition.setVisible(false);
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
        String select = "";
        for (KPIMaster kpiMaster: kpiMasterList) {
            cmbFormPK_kpi.getItems().add(kpiMaster.getKpi_master_id() + ": " + kpiMaster.getKpi_code());

            if(kpiMaster.getKpi_master_id() == positionKPI.getKpi_master_id()) {
                select = kpiMaster.getKpi_master_id() + ": " + kpiMaster.getKpi_code();
                cmbFormPK_kpi.setValue(select);
            }
        }
    }

    private void savePositionKPI() {
        String split[] = cmbFormPK_kpi.getSelectionModel().getSelectedItem().toString().split(":");
        String splitName[] = txtFormPK_position.getText().split(":");
        BigDecimal decWeight;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        try {
            decWeight = (BigDecimal) decimalFormat.parse(txtFormPK_weight.getText());
            decWeight = decWeight.setScale(2, RoundingMode.HALF_UP);
        } catch (ParseException e) {
            System.out.println("Weight is not a number!");
            decWeight = new BigDecimal(100.00);
        }

        Integer classID = KPIMasterConnector.getKPIMaster(Integer.parseInt(split[0])).getKpi_class();

        PositionKPI positionKPI = new PositionKPI(Integer.parseInt(txtFormPKItem_id.getText()),
                Integer.parseInt(splitName[0]), Integer.parseInt(split[0]), classID, decWeight);

        if ( positionKPI.getItem_id().equals(0)) {
            Integer i = positionKPI.insertPositionKPI();
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
        paneFormPositionKPI.setLayoutX(tblPositionKPI.getLayoutX());
        paneFormPositionKPI.setLayoutY(tblPositionKPI.getLayoutY());
        paneFormPositionKPI.setPrefWidth(tblPositionKPI.getPrefWidth());
        paneFormPositionKPI.setPrefHeight(tblPositionKPI.getPrefHeight());
        paneFormPositionKPI.setVisible(true);
    }

    private void hideFormPositionKPI() {
        paneFormPositionKPI.setVisible(false);
    }

}
