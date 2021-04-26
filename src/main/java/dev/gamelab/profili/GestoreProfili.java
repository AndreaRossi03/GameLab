package dev.gamelab.profili;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.gamelab.util.Escludi;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestore dei profili nella configurazione, permette di:
 * - aggiungere, rimuovere o aggiornare profili durante l'utilizzo dell'app
 * - serializzare la lista di profili nel file config.json
 */
public class GestoreProfili {

    private Gson gson;
    private final Map<String, Profilo> profili;
    private final Type tipo = new TypeToken<Map<String, Profilo>>() {}.getType();
    private final Path confiPath = Paths.get(getClass().getResource("/configurazione/config.json").toURI());

    public GestoreProfili() throws IOException, URISyntaxException {

        //  Inizializzazione di Gson e aggiunta di una strategia di esclusione attraverso l'uso dell'annotazione Escludi
        this.gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) { return fieldAttributes.getAnnotation(Escludi.class) != null; }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) { return false; }
        }).create();


        //  Legge il file config.json e ne ricava la mappa
        try (Reader reader = Files.newBufferedReader(confiPath)) {
            Map<String, Profilo> tempProfili = gson.fromJson(reader, tipo);
            this.profili = tempProfili == null ? new HashMap<>() : tempProfili;
        }
    }

    public void aggiungiProfilo(Profilo... profilo) {
        for (Profilo p : profilo) {
            profili.put(p.getEmail(), p);
        }
    }

    public Profilo getProfilo(String email) {
        return profili.get(email);
    }

    public void salvaConfigurazione() throws IOException {
        try (Writer writer = Files.newBufferedWriter(confiPath)) {
            gson.toJson(profili, tipo, writer);
        }
    }

    public boolean mappaVuota() {
        return profili.isEmpty();
    }
}
