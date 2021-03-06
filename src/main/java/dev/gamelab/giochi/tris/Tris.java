package dev.gamelab.giochi.tris;

public class Tris {

    public enum Cella {
        VUOTO,
        CROCE,
        CERCHIO
    }

    private Cella[][] celle;

    public Tris() {
        this.celle = new Cella[3][3];
        celleVuote();
    }

    public void celleVuote() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                celle[i][j] = Cella.VUOTO;
            }
        }
    }

    public Cella[][] getCelle() {
        return celle;
    }

    public void setCella(Cella cella, int x, int y) {
        celle[x][y] = cella;
    }
}
