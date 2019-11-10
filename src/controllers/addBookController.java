package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addBookController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton cancelButton;

    DatabaseConn databaseConn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseConn = DatabaseConn.getInstance();
        checkData();
    }

    @FXML
    void addBook(ActionEvent event) {

        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();

        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }
        String qu = "INSERT INTO BOOK VALUES "
                    + "("
                    + "'" + bookID + "',"
                    + "'" + bookName + "',"
                    + "'" + bookAuthor + "',"
                    + "'" + bookPublisher + "',"
                    + "" + "true" + ""
                    + ")";
        System.out.println(qu);
        if (databaseConn.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        } else //Error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    private void checkData() {
        String qu = "SELECT * FROM BOOK";
        ResultSet rs = databaseConn.execQuery(qu);
        try {
            while(rs.next()){
                String titlex = rs.getString("title");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inflateUI (listBookController.Book book) {
        title.setText(book.getTitle());
        id.setText(book.getId());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
    }
}
