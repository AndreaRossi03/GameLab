package dev.gamelab.giochi.campominato;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CampoMinato {
    private int righe;
    private int colonne;
    private int numeroBombe;
    private Cella[][] celle;
    private List<Cella> bombe;

    public CampoMinato(int righe, int colonne, int numeroBombe) {
        this.righe = righe;
        this.colonne = colonne;
        this.numeroBombe = numeroBombe;
        this.celle = new Cella[righe][colonne];
        this.bombe = new ArrayList<>(numeroBombe);
    }

    public void creaCampo() {
        // INIZIALIZZAZIONE CELLE
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                celle[i][j] = new Cella(i, j, 0, Cella.Tipo.NUMERO);
            }
        }

        Random random = new Random();
        int x, y;
        for (int i = 0; i < numeroBombe; i++) {
            // Inizializzazione posizioni random delle bombe
            do {
                x = random.nextInt(colonne - 1);
                y = random.nextInt(righe - 1);
            } while (celle[x][y].getTipo() == Cella.Tipo.BOMBA);
            Cella bomba = new Cella(x, y, 0, Cella.Tipo.BOMBA);
            celle[x][y] = bomba;
            bombe.add(bomba);

            // Aumenta il numero di bombeVicine a tutte le celle adiacenti alla bomba
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    if (x + j < colonne && y + k < righe && x + j >= 0 && y + k >= 0) {
                        celle[x + j][y + k].aumentaBombeVicine();
                    }
                }
            }
        }
    }

    public List<Cella> getBombe() {
        return bombe;
    }

    public int getRighe() {
        return righe;
    }

    public int getColonne() {
        return colonne;
    }

    public int getNumeroBombe() {
        return numeroBombe;
    }

    public Cella[][] getCelle() {
        return celle;
    }
}
