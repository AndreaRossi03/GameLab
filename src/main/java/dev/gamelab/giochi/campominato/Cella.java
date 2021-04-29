package dev.gamelab.giochi.campominato;

import java.util.Objects;

public class Cella {

    public enum Tipo {
        BOMBA,
        NUMERO
    }

    public enum Stato {
        BANDIERA,
        VISIBILE,
        NON_VISIBILE
    }

    private int x, y, bombeVicine;
    private Stato stato;
    private Tipo tipo;

    public Cella(int x, int y, int bombeVicine, Tipo tipo) {
        this.x = x;
        this.y = y;
        this.bombeVicine = bombeVicine;
        this.tipo = tipo;
        this.stato = Stato.NON_VISIBILE;
    }

    public void alternaBandiera(Runnable bandiera, Runnable nonVisibile) {
        if (stato == Stato.BANDIERA) {
            stato = Stato.NON_VISIBILE;
            bandiera.run();
        } else if (stato == Stato.NON_VISIBILE) {
            stato = Stato.BANDIERA;
            nonVisibile.run();
        }
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBombeVicine() {
        return bombeVicine;
    }

    public void aumentaBombeVicine() {
        bombeVicine++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cella cella = (Cella) o;
        return x == cella.x && y == cella.y && stato == cella.stato && tipo == cella.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, stato, tipo);
    }

    @Override
    public String toString() {
        return "Cella{" +
                "x=" + x +
                ", y=" + y +
                ", bombeVicine=" + bombeVicine +
                ", stato=" + stato +
                ", tipo=" + tipo +
                '}';
    }
}
