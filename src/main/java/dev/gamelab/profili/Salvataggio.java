package dev.gamelab.profili;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Salvataggio {

    private Map<String, Integer> valori = new HashMap<>();

    public void aggiornaValore(String chiave, Integer valore) {
        valori.put(chiave, valore);
    }

    public void aumentaValore(String chiave) {
        valori.putIfAbsent(chiave, 0);
        valori.put(chiave, valori.get(chiave) + 1);
    }

    public void diminuisciValore(String chiave) {
        valori.putIfAbsent(chiave, 0);

        int nuovoValore = valori.get(chiave) - 1;

        if (nuovoValore >= 0) {
            valori.put(chiave, nuovoValore);
        }
    }

    public Map<String, Integer> getValori() {
        return Collections.unmodifiableMap(valori);
    }
}
