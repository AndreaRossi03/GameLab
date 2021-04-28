package dev.gamelab.giochi.tris;

import dev.gamelab.GameLab;
import dev.gamelab.giochi.Immagini;
import dev.gamelab.giochi.Stato;
import dev.gamelab.profili.Profilo;
import dev.gamelab.profili.Salvataggio;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

public class TrisController {

    @FXML
    private Canvas canvas;

    private Tris tris;
    private Stato stato;
    private GraphicsContext context;

    private double moltiplicatoreX;
    private double moltiplicatoreY;

    @FXML
    private void initialize() {
        stato = Stato.INIZIANDO;
        context = canvas.getGraphicsContext2D();
        tris = new Tris();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        disegnaGriglia();

        moltiplicatoreY = canvas.getHeight() / 3;
        moltiplicatoreX = canvas.getWidth() / 3;
    }

    @FXML
    private void canvasCliccato(MouseEvent event) {
        if (stato == Stato.INIZIANDO) {
            stato = Stato.GIOCANDO;
        }

        if (stato != Stato.GIOCANDO) {
            return;
        }

        int colonna = (int) (event.getX() / moltiplicatoreX);
        int riga = (int) (event.getY() / moltiplicatoreY);
        MouseButton bottone = event.getButton();

        Tris.Cella cella = tris.getCelle()[colonna][riga];

        if (bottone == MouseButton.PRIMARY) {
            if (cella == Tris.Cella.VUOTO) {
                disegnaElemento(Tris.Cella.CROCE, colonna, riga);
                tris.setCella(Tris.Cella.CROCE, colonna, riga);


                int valore = MinimaxAI.valoreMappa(tris.getCelle());

                if (valore == 0) {
                    Pair<Integer, Integer> posizioneMigliore = MinimaxAI.trovaPosizioneMigliore(tris.getCelle());
                    disegnaElemento(Tris.Cella.CERCHIO, posizioneMigliore.getKey(), posizioneMigliore.getValue());

                    try {
                        tris.setCella(Tris.Cella.CERCHIO, posizioneMigliore.getKey(), posizioneMigliore.getValue());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        stato = Stato.PARI;
                    }

                    valore = MinimaxAI.valoreMappa(tris.getCelle());
                }

                if (valore == 10) {
                    stato = Stato.VINTO;
                } else if (valore == -10) {
                    stato = Stato.PERSO;
                }
            }
        }

        giocoFinito();
    }

    @FXML
    private void riprova() {
        initialize();
    }

    private void giocoFinito() {
        if (stato == Stato.GIOCANDO || stato == Stato.INIZIANDO) {
            return;
        }

        context.setFill(Color.TRANSPARENT);
        context.setEffect(new GaussianBlur());
        disegnaMappa();

        context.setEffect(null);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 60));

        Profilo profilo = GameLab.getInstance().getProfiloSessione();

        if (!profilo.getSalvataggi().containsKey("tris")) {
            profilo.aggiungiSalvataggio("tris", new Salvataggio());
        }

        Salvataggio salvataggio = profilo.getSalvataggio("tris");

        if (stato == Stato.PARI) {
            context.setFill(Color.LIGHTGRAY);
            context.fillText("HAI PAREGGIATO!", canvas.getWidth() / 2, canvas.getHeight() / 2);
            salvataggio.aumentaValore("partite_pari");
        }

        if (stato == Stato.VINTO) {
            context.setFill(Color.LIGHTGREEN);
            context.fillText("HAI VINTO!", canvas.getWidth() / 2, canvas.getHeight() / 2);
            salvataggio.aumentaValore("partite_vinte");
        }

        if (stato == Stato.PERSO) {
            context.setFill(Color.RED);
            context.fillText("HAI PERSO!", canvas.getWidth() / 2, canvas.getHeight() / 2);
            salvataggio.aumentaValore("partite_perse");
        }
    }

    private void disegnaMappa() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                disegnaElemento(tris.getCelle()[i][j], i, j);
            }
        }
    }

    private void disegnaGriglia() {
        context.setLineWidth(3);
        context.setStroke(Color.WHITE);
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
}
