package dev.gamelab.paginainiziale;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dev.gamelab.GameLab;
import dev.gamelab.profili.GestoreProfili;
import dev.gamelab.profili.Profilo;
import dev.gamelab.util.Utilita;
import javafx.fxml.FXML;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

public class AccediController {

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;

    @FXML
    private void accedi() throws IOException {
        GameLab gameLab = GameLab.getInstance();
        GestoreProfili gestoreProfili = gameLab.getGestoreProfili();

        Profilo profilo = gestoreProfili.getProfilo(email.getText());

        if (profilo == null) {
            email.setText("");
            email.setPromptText("Email non trovata");
            email.setStyle("-fx-prompt-text-fill: red;");
            Utilita.suonaClip(Utilita.Clip.ERRORE);
            return;
        }

        if (!profilo.getPassword().equals(DigestUtils.sha1Hex(password.getText()))) {
            password.setText("");
            password.setPromptText("Password errata");
            password.setStyle("-fx-prompt-text-fill: red;");
            Utilita.suonaClip(Utilita.Clip.ERRORE);
            return;
        }

        Utilita.suonaClip(Utilita.Clip.BOTTONE);
        gameLab.creaSessione(profilo);
        gameLab.cambiaScena("/scene/PaginaPrincipale.fxml");
    }
}
