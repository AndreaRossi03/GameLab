package dev.gamelab.paginaprincipale;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaGiochiController {

    @FXML
    private AnchorPane game_display;
    private AnchorPane display;

    public static ObservableList<Node> listaGiochi;

    @FXML
    private void initialize() {
        listaGiochi = game_display.getChildren();
    }

    @FXML
    private void campoMinato() {
        try {
            display = FXMLLoader.load(getClass().getResource("/scene/CampoMinato.fxml"));
            PaginaPrincipaleController.contnt.getChildren().setAll(display);
        } catch (IOException ex) {
            Logger.getLogger(PaginaPrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void tris() {
        try {
            display = FXMLLoader.load(getClass().getResource("/scene/Tris.fxml"));
            PaginaPrincipaleController.contnt.getChildren().setAll(display);
        } catch (IOException ex) {
            Logger.getLogger(PaginaPrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
