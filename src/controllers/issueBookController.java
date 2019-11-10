package controllers;

import com.jfoenix.effects.JFXDepthManager;
import database.DatabaseConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class issueBookController implements Initializable {

    @FXML
    private HBox bookInfo;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookAvail;
    @FXML
    private TextField bookIdInput;
    @FXML
    private HBox studentInfo;
    @FXML
    private Text studentName;
    @FXML
    private Text studentNumber;
    @FXML
    private TextField studentIdInput;

    DatabaseConn databaseConn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        JFXDepthManager.setDepth(bookInfo, 1);
        JFXDepthManager.setDepth(studentInfo, 1);

        databaseConn = DatabaseConn.getInstance();
    }

    @FXML
    void showBookInfo(ActionEvent event) {
        String id = bookIdInput.getText();
        String qu = "SELECT * FROM BOOK WHERE id = '" + id + "'";
        ResultSet rs = databaseConn.execQuery(qu);
        Boolean flag = false;

            try {
                while (rs.next()) {
                    String bName = rs.getString("title");
                    String bAuthor = rs.getString("author");
                    Boolean bAvail = rs.getBoolean("isAvail");

                    bookName.setText(bName);
                    bookAuthor.setText(bAuthor);
                    String avail = (bAvail)?"Available" : "Not Available";
                    bookAvail.setText(avail);
                    flag = true;
                }
                if (!flag) {
                    bookName.setText("There is no Book in this ID!");
                    bookAuthor.setText("---");
                    bookAvail.setText("Unavailable");
                }
            } catch (SQLException ex) {
                Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    @FXML
    void showStudentInfo(ActionEvent event) {
        String id = studentIdInput.getText();
        String qu = "SELECT * FROM STUDENT WHERE id = '" + id + "'";
        ResultSet rs = databaseConn.execQuery(qu);
        Boolean flag = false;

        try {
            while (rs.next()) {
                String sName =rs.getString("name");
                String sNumber = rs.getString("number");

                studentName.setText(sName);
                studentNumber.setText(sNumber);

                flag = true;
            }
            if (!flag) {
                studentName.setText("There is no Student in this ID!");
                studentNumber.setText("---");
            }
        } catch (SQLException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void doIssueOperation(ActionEvent event) {
        String studentID = studentIdInput.getText();
        String bookID = bookIdInput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to issue the book " + bookName.getText() + "\n to " + studentName.getText() + "?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String str = "INSERT INTO ISSUE(bookID, studentID) VALUES ("
                        + "'" + bookID + "',"
                        + "'" + studentID + "')";
            String str2 = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookID + "'";
            System.out.println(str + " and " + str2);

            if (databaseConn.execAction(str) && databaseConn.execAction(str2)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("");
                alert1.setHeaderText(null);
                alert1.setContentText("Successful");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error!");
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
