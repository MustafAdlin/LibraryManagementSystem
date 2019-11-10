package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class loginController implements Initializable {


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private Label messageLabel;

    Prefences prefence;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefence = Prefences.getPrefences();
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
    @FXML
    void goToDashboard(ActionEvent event) {
        String user = username.getText();
        String pass = DigestUtils.shaHex(password.getText());

        if (user.equals(prefence.getUsername()) && pass.equals(prefence.getPassword())) {
            ((Stage) username.getScene().getWindow()).close();
            loadDashboard();
        } else {
            messageLabel.setText("Check your Username and Password!");
        }
    }
    void loadDashboard() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/dashboard.fxml"));
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
