package dev.gamelab.giochi.campominato;

import dev.gamelab.GameLab;
import dev.gamelab.giochi.Stato;
import dev.gamelab.profili.Profilo;
import dev.gamelab.profili.Salvataggio;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class CampoMinatoController {

    @FXML
    private Canvas canvas;

    @FXML
    private VBox controlli;

    @FXML
    private Label cronometro;

    @FXML
    private Label bombe;

    @FXML
    private AnchorPane pannello;

    private CampoMinato campoMinato;
    private GraphicsContext context;

    private double moltiplicatoreX;
    private double moltiplicatoreY;
    private int celleScoperte;
    private Stato statoGioco;
    private Stage stage = GameLab.getInstance().getMainStage();

    private StringProperty numeroBombe = new SimpleStringProperty();
    private StringProperty tempo = new SimpleStringProperty();

    private AnimationTimer tempoAnimazione;

    @FXML
    private void initialize() {
        statoGioco = Stato.INIZIANDO;
        campoMinato = new CampoMinato(10, 10, 10);
        campoMinato.creaCampo();
        context = canvas.getGraphicsContext2D();
        celleScoperte = 0;

        moltiplicatoreX = canvas.getWidth() / campoMinato.getColonne();
        moltiplicatoreY = canvas.getHeight() / campoMinato.getRighe();

        numeroBombe.bindBidirectional(bombe.textProperty());
        numeroBombe.set(String.valueOf(campoMinato.getNumeroBombe()));

        tempo.bindBidirectional(cronometro.textProperty());
        tempo.set(String.valueOf(0));

        stage.widthProperty().addListener(((observable, oldValue, newValue) -> {
            pannello.setPrefWidth(pannello.getWidth() + (newValue.doubleValue() - oldValue.doubleValue()));
            disegna();
        }));

        stage.heightProperty().addListener(((observable, oldValue, newValue) -> {
            pannello.setPrefHeight(pannello.getHeight() + (newValue.doubleValue() - oldValue.doubleValue()));
            disegna();
        }));

        tempoAnimazione = new AnimationTimer() {
            private long ultimoCiclo = 0;

            @Override
            public void handle(long now) {
                if (now - ultimoCiclo >= 1_000_000_000L) {
                    tempo.set(String.valueOf(Integer.parseInt(tempo.get()) + 1));
                    ultimoCiclo = now;
                }
            }
        };

        disegnaCampo();
    }

    private void disegna() {
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        disegnaCampo();
        giocoFinito();
    }
    @FXML
    private void riprova() {
        tempoAnimazione.stop();
        initialize();
    }

    private void disegnaCampo() {
        for (int x = 0; x < campoMinato.getColonne(); x++) {
            for (int y = 0; y < campoMinato.getRighe(); y++) {
                disegnaCella(campoMinato.getCelle()[x][y]);
            }
        }
    }

    private void giocoFinito() {
        if (statoGioco == Stato.GIOCANDO || statoGioco == Stato.INIZIANDO) {
            return;
        }

        tempoAnimazione.stop();

        context.setFill(Color.TRANSPARENT);
        context.setEffect(new GaussianBlur());
        disegnaCampo();

        context.setEffect(null);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 60));

        Profilo profilo = GameLab.getInstance().getProfiloSessione();

        if (!profilo.getSalvataggi().containsKey("campo_minato")) {
            profilo.aggiungiSalvataggio("campo_minato", new Salvataggio());
        }

        Salvataggio salvataggio = profilo.getSalvataggio("campo_minato");

        if (statoGioco == Stato.VINTO) {
            context.setFill(Color.LIGHTGREEN);
            context.fillText("HAI VINTO!", canvas.getWidth() / 2, canvas.getHeight() / 2);
            salvataggio.aumentaValore("partite_vinte");
        }

        if (statoGioco == Stato.PERSO) {
            context.setFill(Color.RED);
            context.fillText("HAI PERSO!", canvas.getWidth() / 2, canvas.getHeight() / 2);
            salvataggio.aumentaValore("partite_perse");
        }
    }

    @FXML
    private void canvasCliccato(MouseEvent event) {
        if (statoGioco == Stato.INIZIANDO) {
            tempoAnimazione.start();
            statoGioco = Stato.GIOCANDO;
        }

        if (statoGioco != Stato.GIOCANDO) {
            return;
        }

        int colonna = (int) (event.getX() / moltiplicatoreX);
        int riga = (int) (event.getY() / moltiplicatoreY);

        Cella cella = campoMinato.getCelle()[colonna][riga];
        MouseButton bottone = event.getButton();

        if (bottone == MouseButton.PRIMARY && cella.getStato() != Cella.Stato.BANDIERA) {
            if (cella.getTipo() == Cella.Tipo.BOMBA) {
                for (Cella bomba : campoMinato.getBombe()) {
                    bomba.setStato(Cella.Stato.VISIBILE);
                    disegnaCella(bomba);
                }

                statoGioco = Stato.PERSO;
            } else {
                cascata(campoMinato.getCelle(), colonna, riga);
            }
        }

        if (bottone == MouseButton.SECONDARY) {
            if (cella.getStato() != Cella.Stato.VISIBILE) {
                cella.alternaBandiera(
                    () -> numeroBombe.set(String.valueOf(Integer.parseInt(numeroBombe.get()) + 1)),
                    () -> numeroBombe.set(String.valueOf(Integer.parseInt(numeroBombe.get()) - 1))
                );

                disegnaCella(cella);
            }
        }

        if (celleScoperte == (campoMinato.getRighe() * campoMinato.getColonne()) - campoMinato.getNumeroBombe()) {
            for (Cella bomba : campoMinato.getBombe()) {
                bomba.setStato(Cella.Stato.BANDIERA);
                disegnaCella(bomba);
            }

            statoGioco = Stato.VINTO;
        }

        giocoFinito();
    }

    private void cascata(Cella[][] celle, int x, int y) {
        if (x < 0 || x >= campoMinato.getColonne() || y < 0 || y >= campoMinato.getRighe()) {
            return;
        }
        if (celle[x][y].getStato() != Cella.Stato.NON_VISIBILE) {
            return;
        }

        celle[x][y].setStato(Cella.Stato.VISIBILE);
        disegnaCella(celle[x][y]);
        celleScoperte++;

        if (celle[x][y].getBombeVicine() > 0 ) {
            return;
        }

        cascata(celle, x - 1, y - 1);
        cascata(celle, x - 1, y);
        cascata(celle, x - 1, y + 1);

        cascata(celle, x, y - 1);
        cascata(celle, x, y + 1);

        cascata(celle, x + 1, y - 1);
        cascata(celle, x + 1, y);
        cascata(celle, x + 1, y + 1);
    }

    private void disegnaCella(Cella cella) {
        Image immagine = null;

        switch (cella.getTipo()) {
            case BOMBA:
                immagine = Immagini.BOMBA;
                break;
            case NUMERO:
                immagine = Immagini.NUMERO[cella.getBombeVicine()];
                break;
        }

        switch (cella.getStato()) {
            case BANDIERA:
                immagine = Immagini.BANDIERA;
                break;
            case NON_VISIBILE:
                immagine = Immagini.NON_VISIBILE;
                break;
        }

        if (cella.getTipo() == Cella.Tipo.BOMBA) {
            context.drawImage(Immagini.NUMERO[0], cella.getX() * moltiplicatoreX, cella.getY() * moltiplicatoreY, moltiplicatoreX, moltiplicatoreY);
        }

        context.drawImage(immagine, cella.getX() * moltiplicatoreX, cella.getY() * moltiplicatoreY, moltiplicatoreX, moltiplicatoreY);
    }
}
