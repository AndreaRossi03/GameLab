package dev.gamelab.paginaprincipale;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SidebarController implements Initializable {

    AnchorPane mygames, impostazioni;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mygames = FXMLLoader.load(getClass().getResource("/scene/ListaGiochi.fxml"));
            impostazioni = FXMLLoader.load(getClass().getResource("/scene/Impostazioni.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(PaginaPrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    VBox sidebarButton;

    @FXML
    private void mouseRilascia(MouseEvent event) {
        sidebarButton = (VBox) event.getSource();
        String id = sidebarButton.getId();

        if ("MG".equals(id) || "GL".equals(id)) {
            sidebarButton.setEffect(null);
        }
    }

    @FXML
    private void mouseSopra(MouseEvent event) {
        sidebarButton = (VBox) event.getSource();

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.web("#212330", 1.0f));
        dropShadow.setOffsetY(8.0);

        String id = sidebarButton.getId();
        if ("MG".equals(id) || "GL".equals(id)) {
            sidebarButton.setEffect(dropShadow);
        }
    }

    @FXML
    private void mouseClicca(MouseEvent event) {
        sidebarButton = (VBox) event.getSource();

        switch (sidebarButton.getId()) {
            case "MG":
                PaginaPrincipaleController.contnt.getChildren().setAll(mygames);
                break;
            case "GL":
                PaginaPrincipaleController.contnt.getChildren().setAll(impostazioni);
                break;
        }

    }
}
