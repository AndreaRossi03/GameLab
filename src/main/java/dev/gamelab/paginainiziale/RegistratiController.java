package dev.gamelab.paginainiziale;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dev.gamelab.GameLab;
import dev.gamelab.profili.GestoreProfili;
import dev.gamelab.profili.Profilo;
import dev.gamelab.util.Utilita;
import javafx.fxml.FXML;

import java.io.IOException;

public class RegistratiController {

    @FXML
    private JFXTextField nome;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;

    @FXML
    private void registrati() throws IOException {
        GameLab gameLab = GameLab.getInstance();
        GestoreProfili gestoreProfili = gameLab.getGestoreProfili();

        if (nome.getText().length() < 4) {
            nome.setText("");
            nome.setPromptText("Inserisci un nome valido");
            nome.setStyle("-fx-prompt-text-fill: red;");
            Utilita.suonaClip(Utilita.Clip.ERRORE);
            return;
        }

        if (gestoreProfili.getProfilo(email.getText()) != null) {
            email.setText("");
            email.setPromptText("Questa email è già utilizzata!");
            email.setStyle("-fx-prompt-text-fill: red;");
            Utilita.suonaClip(Utilita.Clip.ERRORE);
            return;
        }

        if (!email.getText().matches(Utilita.emailRegex)) {
            email.setText("");
            email.setPromptText("Inserisci un email valida");
            email.setStyle("-fx-prompt-text-fill: red;");
            Utilita.suonaClip(Utilita.Clip.ERRORE);
            return;
        }

        if (!password.getText().matches(Utilita.passwordRegex)) {
            password.setText("");
            password.setPromptText("Inserisci una password valida");
            password.setStyle("-fx-prompt-text-fill: red;");
            Utilita.suonaClip(Utilita.Clip.ERRORE);
            return;
        }

        Utilita.suonaClip(Utilita.Clip.BOTTONE);
        Profilo profilo = new Profilo(nome.getText(), email.getText(), password.getText());
        gestoreProfili.aggiungiProfilo(profilo);
        gameLab.creaSessione(profilo);
        gameLab.cambiaScena("/scene/PaginaPrincipale.fxml");
    }
}
