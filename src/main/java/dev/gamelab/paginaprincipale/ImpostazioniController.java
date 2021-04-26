package dev.gamelab.paginaprincipale;

import com.jfoenix.controls.JFXCheckBox;
import dev.gamelab.GameLab;
import dev.gamelab.profili.Impostazioni;
import javafx.fxml.FXML;

public class ImpostazioniController {

    @FXML
    private JFXCheckBox effettiSonori;

    private Impostazioni impostazioni;

    @FXML
    private void initialize() {
        this.impostazioni = GameLab.getInstance().getProfiloSessione().getImpostazioni();
        effettiSonori.setSelected(impostazioni.isEffettiSonori());

        effettiSonori.selectedProperty().addListener((observable, oldValue, newValue) -> {
            impostazioni.setEffettiSonori(newValue);
        });
    }
}
