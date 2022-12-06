package net.dahliasolutions.varcomp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpViewController implements Initializable {

    @FXML
    private WebView webViewer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine webEngine = webViewer.getEngine();
        webEngine.load(DBUtils.getHelpDocs());
    }
}
