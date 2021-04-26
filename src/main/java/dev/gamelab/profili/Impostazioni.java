package dev.gamelab.profili;

public class Impostazioni {
    private boolean effettiSonori;

    public Impostazioni() {
        effettiSonori = true;
    }

    public boolean isEffettiSonori() {
        return effettiSonori;
    }

    public void setEffettiSonori(boolean effettiSonori) {
        this.effettiSonori = effettiSonori;
    }
}
