package dev.gamelab.paginainiziale;

import dev.gamelab.GameLab;
import dev.gamelab.paginaprincipale.PaginaPrincipaleController;
import dev.gamelab.profili.GestoreProfili;
import dev.gamelab.util.Utilita;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaginaInizialeController {

    @FXML
    private AnchorPane barra;

    @FXML
    private ImageView minimizza;

    @FXML
    private ImageView chiudi;

    @FXML
    private AnchorPane controlli;

    @FXML
    private Hyperlink registrati;

    private VBox accediPannello, registratiPannello;
    private GameLab gameLab;

    @FXML
    private void initialize() {
        this.gameLab = GameLab.getInstance();
        GestoreProfili gestoreProfili = gameLab.getGestoreProfili();

        try {
            accediPannello = FXMLLoader.load(getClass().getResource("/scene/Accedi.fxml"));
            registratiPannello = FXMLLoader.load(getClass().getResource("/scene/Registrati.fxml"));
        } catch (IOException e) {
            Logger.getLogger(PaginaPrincipaleController.class.getName()).log(Level.SEVERE, "Impossibile caricare la pagina iniziale!", e);
        }

        if (gestoreProfili.mappaVuota()) {
            registrati();
        } else {
            controlli.getChildren().setAll(accediPannello);
        }
    }

    @FXML
    private void registrati() {
        Utilita.suonaClip(Utilita.Clip.BOTTONE);
        registrati.setVisible(false);
        controlli.getChildren().setAll(registratiPannello);
    }

    @FXML
    private void minimizza(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(!stage.isIconified());
    }

    @FXML
    private void chiudi() {
        Platform.exit();
    }

    public AnchorPane getBarra() {
        return barra;
    }
}
