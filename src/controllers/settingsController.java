package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }

    @FXML
    void saveNewUser(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        Prefences prefences = Prefences.getPrefences();
        prefences.setUsername(user);
        prefences.setPassword(pass);

        Prefences.PreferenceToFile(prefences);
    }
    private void initDefaultValues() {
        Prefences prefences = Prefences.getPrefences();
        username.setText(String.valueOf(prefences.getUsername()));
        password.setText(String.valueOf(prefences.getPassword()));
    }

}
