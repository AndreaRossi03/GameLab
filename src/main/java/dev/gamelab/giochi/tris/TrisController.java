package dev.gamelab.giochi.tris;

import com.jfoenix.controls.JFXButton;
import dev.gamelab.giochi.Stato;
import dev.gamelab.giochi.campominato.Cella;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TrisController {

    @FXML
    private Canvas canvas;

    @FXML
    private JFXButton riprova;

    @FXML
    private JFXButton croce;

    @FXML
    private JFXButton cerchio;

    private Tris tris;
    private Stato stato;
    private GraphicsContext context;

    @FXML
    private void initialize() {
        stato = Stato.INIZIANDO;
        context = canvas.getGraphicsContext2D();
        tris = new Tris();
    }

    private void aggiornaCella(Tris.Cella cella, int x, int y) {
        tris.getCelle()[x][y] = cella;

        double newX = canvas.getWidth() / x;
    }

    @FXML
    private void canvasCliccato(MouseEvent event) {
        if (stato == Stato.INIZIANDO) {
            stato = Stato.GIOCANDO;
        }

        MouseButton bottone = event.getButton();

        switch (bottone) {
            case PRIMARY:
                // METTI CELLA
                break;
            case SECONDARY:
                // RIMUOVI CELLA
                break;
        }
    }
}
