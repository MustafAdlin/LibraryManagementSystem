package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.net.URL;
import java.util.ResourceBundle;

    public class addStudentController implements Initializable {

        @FXML
        private JFXTextField id;
        @FXML
        private JFXTextField name;
        @FXML
        private JFXTextField number;
        @FXML
        private JFXTextField email;
        @FXML
        private JFXButton addButton;
        @FXML
        private JFXButton cancelButton;

        DatabaseConn databaseConn;

        @Override
        public void initialize(URL url, ResourceBundle rb) {

            databaseConn = DatabaseConn.getInstance();
        }

        @FXML
        void addStudent(ActionEvent event) {

            String studentID = id.getText();
            String studentName = name.getText();
            String studentNumber = number.getText();
            String studentEmail = email.getText();

            Boolean flag = studentID.isEmpty() || studentName.isEmpty() || studentNumber.isEmpty() || studentEmail.isEmpty();
            if (flag) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields");
                alert.showAndWait();
                return;
            }
            String st = "INSERT INTO STUDENT VALUES ("
                        + "'" + studentID + "',"
                        + "'" + studentName + "',"
                        + "'" + studentNumber + "',"
                        + "'" + studentEmail + "'"
                        + ")";
            System.out.println(st);
            if (databaseConn.execAction(st)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Saved");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Error Occurred");
                alert.showAndWait();
            }
        }

        @FXML
        void cancel(ActionEvent event) {

        }
}
