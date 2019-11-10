package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import database.DatabaseConn;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class renewBookController implements Initializable {

    @FXML
    private JFXTextField bookID;
    @FXML
    private ListView<String> issuedData;

    Boolean isReadyForSubmit = false;

    DatabaseConn databaseConn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseConn = DatabaseConn.getInstance();
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmit = false;

        String bookId = bookID.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookID = '" + bookId + "'";
        ResultSet rs = databaseConn.execQuery(qu);

        try {
            while (rs.next()) {
                String rBookID = bookId;
                String rStudentID = rs.getString("studentID");
                Timestamp bIssueTime = rs.getTimestamp("issueTime");
                int bRenewCount = rs.getInt("renew_count");

                issueData.add("Issue Date And Time: " + bIssueTime.toGMTString());
                issueData.add("Renew Count: " + bRenewCount);

                issueData.add("Book Info: ");
                qu = "SELECT * FROM BOOK WHERE ID = '" + rBookID + "'";
                ResultSet rl = databaseConn.execQuery(qu);
                while (rl.next()) {
                    issueData.add("Book ID : " + rl.getString("id"));
                    issueData.add("Book Name : " + rl.getString("title"));
                    issueData.add("Book Author : " + rl.getString("author"));
                    issueData.add("Book Publisher : " + rl.getString("publisher"));
                }

                issueData.add("Student Info: ");
                qu = "SELECT * FROM STUDENT WHERE ID = '" + rStudentID + "'";
                rl = databaseConn.execQuery(qu);
                while (rl.next()) {
                    issueData.add("Name : " + rl.getString("name"));
                    issueData.add("Mobile Number : " + rl.getString("number"));
                    issueData.add("Email : " + rl.getString("email"));
                }
                isReadyForSubmit = true;
            }
        }   catch (SQLException ex) {
            Logger.getLogger(renewBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        issuedData.getItems().setAll(issueData);
    }

    @FXML
    void submitBook(ActionEvent event) {
        if (!isReadyForSubmit) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Select Book For Submission");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Submit Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to return the book?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String bookId = bookID.getText();
            String stl = "DELETE FROM ISSUE WHERE BOOKID = '" + bookId + "'";
            String st2 = "UPDATE BOOK SET ISAVAIL = TRUE WHERE ID = '" + bookId + "'";

            if (databaseConn.execAction(stl) && databaseConn.execAction(st2)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Successful");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Has Been Submitted");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Failed");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("");
            alert1.setHeaderText(null);
            alert1.setContentText("Canceled");
            alert1.showAndWait();
        }
    }

    @FXML
    void renewBook(ActionEvent event) {
        if (!isReadyForSubmit) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Select Book For Renew");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Renew Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to renew the book?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String st = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE BOOKID = '" + bookID.getText() + "'";
            System.out.println(st);
            if (databaseConn.execAction(st)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Successful");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Has Been Renewed");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Failed");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("");
            alert1.setHeaderText(null);
            alert1.setContentText("Canceled");
            alert1.showAndWait();
        }
    }
}
