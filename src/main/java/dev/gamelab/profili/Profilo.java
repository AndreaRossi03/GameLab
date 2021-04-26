package dev.gamelab.profili;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe rappresenta il profilo dell'utente che sta attualmente usando l'applicazione
 */
public class Profilo {

    private String nome;
    private String email;
    private String password;

    /**
     * Mappa contenente i salvataggi dei singoli giochi
     */
    private Map<String, Salvataggio> salvataggi;

    private Impostazioni impostazioni;

    public Profilo(String nome, String email, String password) {
        this(nome, email, password, new HashMap<>(), new Impostazioni());
    }

    /**
     * Costruttore del profilo
     * @param nome nome dell'utente
     * @param email email dell'utente
     * @param password password dell'utente, oscurata dall'algoritmo di hashing
     * @param salvataggi mappa dei salvataggi dei giochi
     * @param impostazioni impostazioni uniche dell'utente
     */
    public Profilo(String nome, String email, String password, Map<String, Salvataggio> salvataggi, Impostazioni impostazioni) {
        this.nome = nome;
        this.email = email;
        this.password = DigestUtils.sha1Hex(password);
        this.salvataggi = salvataggi;
        this.impostazioni = impostazioni;
    }

    public void aggiungiSalvataggio(String nome, Salvataggio salvataggio) {
        salvataggi.put(nome, salvataggio);
    }

    public Salvataggio getSalvataggio(String nome) {
        return salvataggi.get(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha1Hex(password);
    }

    public String getPassword() {
        return password;
    }

    public Map<String, Salvataggio> getSalvataggi() {
        return Collections.unmodifiableMap(salvataggi);
    }

    public Impostazioni getImpostazioni() {
        return impostazioni;
    }

    public void setImpostazioni(Impostazioni impostazioni) {
        this.impostazioni = impostazioni;
    }

    @Override
    public String toString() {
        return "Profilo{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salvataggi=" + salvataggi +
                ", impostazioni=" + impostazioni +
                '}';
    }
}
