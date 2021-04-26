package dev.gamelab.giochi;

import javafx.scene.image.Image;

public class Immagini {
    public static final Image[] NUMERO = new Image[9];
    public static final Image BOMBA = caricaImmagine("campominato/bomba.png");
    public static final Image BANDIERA = caricaImmagine("campominato/bandiera.png");
    public static final Image NON_VISIBILE = caricaImmagine("campominato/non_visibile.png");

    public static final Image CROCE = caricaImmagine("tris/croce.png");
    public static final Image CERCHIO = caricaImmagine("tris/cerchio.png");

    static {
        for (int i = 0; i < NUMERO.length; i++) {
            NUMERO[i] = caricaImmagine("campominato/" + i + ".png");
        }
    }

    public static Image caricaImmagine(String nome) {
        return new Image(Immagini.class.getResourceAsStream("/immagini/giochi/" + nome));
    }
}
