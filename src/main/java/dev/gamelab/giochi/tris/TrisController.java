package dev.gamelab.giochi.tris;

import com.jfoenix.controls.JFXButton;
import dev.gamelab.giochi.Immagini;
import dev.gamelab.giochi.Stato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
    private Turno turno;
    private GraphicsContext context;

    private double moltiplicatoreX;
    private double moltiplicatoreY;
    
    private enum Turno {
        CERCHIO,
        CROCE
    }

    @FXML
    private void initialize() {
        stato = Stato.INIZIANDO;
        context = canvas.getGraphicsContext2D();
        tris = new Tris();
        disegnaGriglia();
        
        cerchio.onActionListener((event) -> {
            if (turno == null) {
                turno = Turno.CERCHIO;
            }
        });
        
        
        croce.onActionListener((event) -> {
            if (turno == null) {
                turno = Turno.CROCE;
            }
        });
        
        moltiplicatoreY = canvas.getHeight() / 3;
        moltiplicatoreX = canvas.getWidth() / 3;
    }

    @FXML
    private void canvasCliccato(MouseEvent event) {
        if (stato == Stato.INIZIANDO) {
            stato = Stato.GIOCANDO;
        }

        int colonna = (int) (event.getX() / moltiplicatoreX);
        int riga = (int) (event.getY() / moltiplicatoreY);
        MouseButton bottone = event.getButton();

        Cella cella = tris.getCelle()[colonna][riga];

        switch (bottone) {
            case PRIMARY:
                if (celle[colonna][riga] == Tris.Cella.VUOTO) {
                    cella = turno == CERCHIO ? Tris.Cella.CERCHIO : Tris.Cella.CROCE
                    disegnaElemento(cella, colonna, riga);
                    turno = cella == Tris.Cella.CERCHIO ? Turno.CERCHIO : Turno.CROCE;                
                }
                break;
            case SECONDARY:
                if (cella != Tris.Cella.VUOTO) {
                    disegnaElemento(Tris.Cella.VUOTO, colonna, riga);
                }
                break;
        }
    }

    public void disegnaGriglia() {
        context.setLineWidth(3);
        context.setStroke(Color.BLACK);
        context.strokeLine(canvas.getWidth() / 3, 0, canvas.getWidth() / 3, canvas.getHeight());
        context.strokeLine(canvas.getWidth() * 2 / 3, 0, canvas.getWidth() * 2 / 3, canvas.getHeight());
        context.strokeLine(0, canvas.getHeight() / 3, canvas.getWidth(), canvas.getHeight() / 3);
        context.strokeLine(0, canvas.getHeight() * 2 / 3, canvas.getWidth(), canvas.getHeight() * 2 / 3);
    }

    public void disegnaElemento(Tris.Cella tipo, int x, int y) {
        Image immagine = null;

        switch (tipo) {
            case CROCE:
                immagine = Immagini.CROCE;
                break;
            case CERCHIO:
                immagine = Immagini.CERCHIO;
                break;
        }

        if (immagine != null) {
            context.drawImage(immagine, x * moltiplicatoreX, y * moltiplicatoreY, moltiplicatoreX, moltiplicatoreY);
        } else {
            context.clearRect(x * moltiplicatoreX + 10, y * moltiplicatoreY + 10, moltiplicatoreX - 20, moltiplicatoreY - 20);
        }
    }

    public void riprova(ActionEvent event) {
    }
}
