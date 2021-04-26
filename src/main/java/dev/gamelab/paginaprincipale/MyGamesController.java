package dev.gamelab.paginaprincipale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyGamesController {

    @FXML
    private AnchorPane game_display;
    private AnchorPane display;

    @FXML
    private void play_game(ActionEvent event) {
        try {
            display = FXMLLoader.load(getClass().getResource("/scene/CampoMinato.fxml"));
            game_display.getChildren().clear();
            game_display.getChildren().setAll(display);
            game_display.autosize();
        } catch (IOException ex) {
            Logger.getLogger(PaginaPrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
