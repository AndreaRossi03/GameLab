package dev.gamelab.util;

import dev.gamelab.GameLab;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public final class Utilita {

    public static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    // Minimo 8 caratteri, una lettera maiuscola e una minuscola, un numero e un carattere speciale
    public static final String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    public enum Clip {
        BOTTONE(creaClip("button_click.mp3")),
        ERRORE(creaClip("error_click.mp3"));

        AudioClip clip;

        Clip(AudioClip clip) {
            this.clip = clip;
        }

        private static AudioClip creaClip(String nome) {
            return new AudioClip(Utilita.class.getResource("/audio/" + nome).toString());
        }

        protected void attiva() {
            clip.play();
        }
    }

    public static void suonaClip(Clip clip) {
        if (GameLab.getInstance().getProfiloSessione() == null) {
            clip.attiva();
            return;
        }

        if (GameLab.getInstance().getProfiloSessione().getImpostazioni().isEffettiSonori()) {
            clip.attiva();
        }
    }

    private static double x, y;

    public static void creaStageMobile(Stage stage, Parent pannello) {
        pannello.setOnMousePressed((MouseEvent event) -> {
            if (!stage.isMaximized() && !stage.isFullScreen()) {
                x = event.getSceneX();
                y = event.getSceneY();
            }
        });
        pannello.setOnMouseDragged((MouseEvent event) -> {
            if (!stage.isMaximized() && !stage.isFullScreen()) {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
                stage.setOpacity(0.7f);
            }
        });
        pannello.setOnDragDone((e) -> {
            if (!stage.isMaximized() && !stage.isFullScreen()) {
                stage.setOpacity(1.0f);
            }
        });
        pannello.setOnMouseReleased((e) -> {
            if (!stage.isMaximized() && !stage.isFullScreen()) {
                stage.setOpacity(1.0f);
            }
        });
    }
}
