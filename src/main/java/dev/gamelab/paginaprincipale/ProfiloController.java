package dev.gamelab.paginaprincipale;

import com.jfoenix.controls.JFXCheckBox;
import dev.gamelab.GameLab;
import dev.gamelab.profili.Impostazioni;
import dev.gamelab.profili.Profilo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Map;

public class ProfiloController {

    @FXML
    private JFXCheckBox effettiSonori;

    @FXML
    private Label nome;

    @FXML
    private Label email;

    @FXML
    private Label vinteTris;

    @FXML
    private Label vinteCampo;

    @FXML
    private Label perseTris;

    @FXML
    private Label perseCampo;

    @FXML
    private Label pariTris;

    private Profilo profilo = GameLab.getInstance().getProfiloSessione();

    @FXML
    private void initialize() {

        nome.setText(profilo.getNome());
        email.setText(profilo.getEmail());

        vinteTris.setText(getValore("tris", "partite_vinte").toString());
        perseTris.setText(getValore("tris", "partite_perse").toString());
        pariTris.setText(getValore("tris", "partite_pari").toString());

        vinteCampo.setText(getValore("campo_minato", "partite_vinte").toString());
        perseCampo.setText(getValore("campo_minato", "partite_perse").toString());

        effettiSonori.setSelected(profilo.getImpostazioni().isEffettiSonori());

        effettiSonori.selectedProperty().addListener((observable, oldValue, newValue)
                -> profilo.getImpostazioni().setEffettiSonori(newValue));
    }

    private Integer getValore(String gioco, String nome) {
        Map<String, Integer> trisValori;

        try {
            trisValori = profilo.getSalvataggio(gioco).getValori();
        } catch (Exception e) {
            return 0;
        }

        if (trisValori != null) {
            return trisValori.get(nome) == null ? 0 : trisValori.get(nome);
        }

        return 0;
    }
}
