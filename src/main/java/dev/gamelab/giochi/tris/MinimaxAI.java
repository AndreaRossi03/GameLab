package dev.gamelab.giochi.tris;

import javafx.util.Pair;

public class MinimaxAI {

    public static boolean mosseDisponibili(Tris.Cella[][] mappa) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (mappa[x][y] == Tris.Cella.VUOTO) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int valoreMappa(Tris.Cella[][] mappa) {
        for (int x = 0; x < 3; x++) {
            if (mappa[x][0] == mappa[x][1] && mappa[x][1] == mappa[x][2]) {
                if (mappa[x][0] == Tris.Cella.CERCHIO) {
                    return -10;
                } else if (mappa[x][0] == Tris.Cella.CROCE) {
                    return +10;
                }
            }
        }

        for (int y = 0; y < 3; y++) {
            if (mappa[0][y] == mappa[1][y] && mappa[1][y] == mappa[2][y]) {
                if (mappa[0][y] == Tris.Cella.CERCHIO) {
                    return -10;
                } else if (mappa[0][y] == Tris.Cella.CROCE) {
                    return +10;
                }
            }
        }

        if (mappa[0][0] == mappa[1][1] && mappa[1][1] == mappa[2][2]) {
            if (mappa[0][0] == Tris.Cella.CERCHIO) {
                return -10;
            } else if (mappa[0][0] == Tris.Cella.CROCE) {
                return +10;
            }
        }

        if (mappa[0][2] == mappa[1][1] && mappa[1][1] == mappa[2][0]) {
            if (mappa[0][2] == Tris.Cella.CERCHIO) {
                return -10;
            } else if (mappa[0][2] == Tris.Cella.CROCE) {
                return +10;
            }
        }

        return 0;
    }

    private static int minimax(Tris.Cella[][] mappa, int profondita, boolean massimo) {
        int punteggio = valoreMappa(mappa);

        if (punteggio == 10 || punteggio == -10) {
            return punteggio;
        }

        if (!mosseDisponibili(mappa)) {
            return 0;
        }

        int migliore;
        if (massimo) {
            migliore = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (mappa[i][j] == Tris.Cella.VUOTO) {
                        mappa[i][j] = Tris.Cella.CERCHIO;

                        migliore = Math.max(migliore, minimax(mappa, profondita+1, false));

                        mappa[i][j] = Tris.Cella.VUOTO;
                    }
                }
            }
        } else {
            migliore = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (mappa[i][j] == Tris.Cella.VUOTO) {
                        mappa[i][j] = Tris.Cella.CERCHIO;

                        migliore = Math.min(migliore, minimax(mappa, profondita+1, true));

                        mappa[i][j] = Tris.Cella.VUOTO;
                    }
                }
            }
        }
        return migliore;
    }

    public static Pair<Integer, Integer> trovaPosizioneMigliore(Tris.Cella[][] mappa) {
        int migliorValore = -1000;
        Pair<Integer, Integer> migliorPosizione = new Pair<>(-1, -1);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mappa[i][j] == Tris.Cella.VUOTO) {
                    mappa[i][j] = Tris.Cella.CERCHIO;

                    int valoreMossa = minimax(mappa, 0, false);

                    mappa[i][j] = Tris.Cella.VUOTO;

                    if (valoreMossa > migliorValore) {
                        migliorPosizione = new Pair<>(i, j);
                        migliorValore = valoreMossa;
                    }
                }
            }
        }

        return migliorPosizione;
    }
}
