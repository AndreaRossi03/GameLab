package dev.gamelab.giochi.campominato;

import javafx.scene.image.Image;

public class Immagini {
    public static final Image[] NUMERO = new Image[9];
    public static final Image BOMBA = caricaImmagine("bomba.png");
    public static final Image BANDIERA = caricaImmagine("bandiera.png");
    public static final Image NON_VISIBILE = caricaImmagine("non_visibile.png");

    static {
        for (int i = 0; i < NUMERO.length; i++) {
            NUMERO[i] = caricaImmagine(i + ".png");
        }
    }

    public static Image caricaImmagine(String nome) {
        return new Image(Immagini.class.getResourceAsStream("/immagini/giochi/campominato/" + nome));
    }
}
