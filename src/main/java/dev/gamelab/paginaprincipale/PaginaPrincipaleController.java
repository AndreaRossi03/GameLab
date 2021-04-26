package dev.gamelab.paginaprincipale;

import com.jfoenix.controls.JFXDrawer;
import dev.gamelab.GameLab;
import dev.gamelab.util.Utilita;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaginaPrincipaleController {

    @FXML
    private Label nomeLabel;
    @FXML
    private JFXDrawer menu;

    @FXML
    private AnchorPane content;

    public static AnchorPane contnt = null;
    private VBox sidebar;
    boolean navFlag;

    private GameLab gameLab = GameLab.getInstance();

    @FXML
    private AnchorPane barra;

    private Stage stage;

    @FXML
    public void initialize() {
        stage = gameLab.getMainStage();
        Utilita.creaStageMobile(stage, barra);
        nomeLabel.setText(gameLab.getProfiloSessione().getNome());

        try {
            sidebar = FXMLLoader.load(getClass().getResource("/scene/Navigazione.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(PaginaPrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        navFlag = false;
        menu.setSidePane(sidebar);
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            menu.setPrefHeight(menu.getHeight() + (newValue.floatValue() - oldValue.floatValue()));
        });
        content.getChildren().clear();
        contnt = this.content;
    }

    @FXML
    private void schermoIntero() {
        stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    private void open_menu() {
        Utilita.suonaClip(Utilita.Clip.BOTTONE);
        menu.toggle();
    }

    @FXML
    private void minimizza() {
        stage.setIconified(!stage.isIconified());
    }

    @FXML
    private void massimizza() {
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    private void chiudi() {
        Platform.exit();
    }
}
