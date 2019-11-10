package controllers;

import com.google.gson.Gson;
import javafx.scene.control.Alert;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Prefences {
    public static final String CONFIG_FILE = "config.txt";

    String username;
    String password;

    public Prefences() {
        username = "admin";
        setPassword("admin");
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = DigestUtils.shaHex(password);
    }

    public static void initConfig() {
        Writer writer = null;
        try {
            Prefences prefence = new Prefences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(prefence,writer);
        } catch (IOException ex) {
            Logger.getLogger(Prefences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Prefences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static Prefences getPrefences() {
        Gson gson = new Gson();
        Prefences prefences = new Prefences();
        try {
            prefences = gson.fromJson(new FileReader(CONFIG_FILE), Prefences.class);
        } catch (FileNotFoundException ex) {
            initConfig();
            Logger.getLogger(Prefences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prefences;
    }
    public static void PreferenceToFile(Prefences prefence) {
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(prefence,writer);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Settings changed succesfully");
            alert.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(Prefences.class.getName()).log(Level.SEVERE, null, ex);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Settings couldn't be changed!");
            alert.showAndWait();
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Prefences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
