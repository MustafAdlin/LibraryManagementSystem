package controllers;

import database.DatabaseConn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listStudentController implements Initializable {

    ObservableList<Student> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, String> idCol;
    @FXML
    private TableColumn<Student, String> nameCol;
    @FXML
    private TableColumn<Student, String> numberCol;
    @FXML
    private TableColumn<Student, String> emailCol;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadData() {
        DatabaseConn databaseConn = DatabaseConn.getInstance();
        String qu = "SELECT * FROM STUDENT";
        ResultSet rs = databaseConn.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String number = rs.getString("number");
                String email = rs.getString("email");

                list.add(new Student(id, name, number, email));
            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().setAll(list);
    }

    public static class Student {

        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty number;
        private final SimpleStringProperty email;

        public Student(String id, String name, String number, String email) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.number = new SimpleStringProperty(number);
            this.email = new SimpleStringProperty(email);
        }

        public String getId() {
            return id.get();
        }
        public String getName() {
            return name.get();
        }
        public String getNumber() {
            return number.get();
        }
        public String getEmail() {
            return email.get();
        }

    }
}
