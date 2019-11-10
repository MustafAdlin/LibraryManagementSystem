package controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class dashboardController  implements Initializable {

    @FXML
    private StackPane dashStackPane;
    @FXML
    private JFXToolbar toolbar;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXDrawer drawer;

    public static AnchorPane temporaryPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        temporaryPane = contentPane;
        initDrawer();
    }

    private void initDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sideMenu.fxml"));
            VBox box = loader.load();
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(dashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    double x, y;
    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
}

