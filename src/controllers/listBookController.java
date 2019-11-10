package controllers;

import com.jfoenix.controls.JFXButton;
import database.DatabaseConn;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listBookController implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Boolean> availabilityCol;
    @FXML
    private JFXButton editButton;
    @FXML
    private JFXButton deleteButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    @FXML
    private void delete(ActionEvent event) {
        Book deleteSelected = tableView.getSelectionModel().getSelectedItem();
        if (deleteSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Select Book for Delete!");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to delete " + deleteSelected.getTitle() + "?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            boolean result = DatabaseConn.getInstance().deleteBook(deleteSelected);
            if (result) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Book Deleted");
                alert1.setHeaderText(null);
                alert1.setContentText(deleteSelected.getTitle() + " deleted.");
                list.remove(deleteSelected);
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText(null);
                alert1.setContentText(deleteSelected.getTitle() + " couldn't deleted!");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("");
            alert1.setHeaderText(null);
            alert1.setContentText("Deletion Canceled");
            alert1.showAndWait();
        }
    }

    @FXML
    private void edit(ActionEvent event) {
        Book editSelected = tableView.getSelectionModel().getSelectedItem();
        if (editSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Select Book for Edit!");
            alert.showAndWait();
            return;
        }
    }

    private void initCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }

    private void loadData() {
        DatabaseConn databaseConn = DatabaseConn.getInstance();
        String qu = "SELECT * FROM BOOK";
        ResultSet rs = databaseConn.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");

                list.add(new Book(id, title, author, publisher, avail));
            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.setItems(list);
    }

    public static class Book {

        private final SimpleStringProperty id;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty availability;

        public Book(String id, String title, String author, String publisher, Boolean avail) {
            this.id = new SimpleStringProperty(id);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.availability = new SimpleBooleanProperty(avail);
        }

        public String getId() {
            return id.get();
        }
        public String getTitle() {
            return title.get();
        }
        public String getAuthor() {
            return author.get();
        }
        public String getPublisher() {
            return publisher.get();
        }
        public Boolean getAvailability() {
            return availability.get();
        }
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
