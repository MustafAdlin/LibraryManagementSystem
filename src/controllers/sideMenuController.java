package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sideMenuController implements Initializable {

    @FXML
    private JFXButton btnOverview;
    @FXML
    private JFXButton btnIssue;
    @FXML
    private JFXButton btnRenew;
    @FXML
    private JFXButton btnAddBook;
    @FXML
    private JFXButton btnListBook;
    @FXML
    private JFXButton btnAddStu;
    @FXML
    private JFXButton btnListStu;
    @FXML
    private JFXButton btnSettings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    public void openOverview(ActionEvent actionEvent) {
    }

    public void openIssue(ActionEvent actionEvent) {
        switchPane(Constants.ISSUEBOOK);
    }

    public void openRenew(ActionEvent actionEvent) {
        switchPane(Constants.RENEWBOOK);
    }

    public void openAddBook(ActionEvent actionEvent) {
        switchPane(Constants.ADDBOOK);
    }

    public void openListBook(ActionEvent actionEvent) {
        switchPane(Constants.LISTBOOK);
    }

    public void openAddStu(ActionEvent actionEvent) {
        switchPane(Constants.ADDSTUDENT);
    }

    public void openListStu(ActionEvent actionEvent) {
        switchPane(Constants.LISTSTUDENT);
    }

    public void openSettings(ActionEvent actionEvent) {
        switchPane(Constants.SETTINGS);
    }

    private void switchPane(String pane) {
        try {
            dashboardController.temporaryPane.getChildren().clear();
            StackPane pane2 = FXMLLoader.load(getClass().getResource(pane));

            ObservableList<Node> elements = pane2.getChildren();
            dashboardController.temporaryPane.getChildren().setAll(elements);
        } catch (IOException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
