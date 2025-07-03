package model;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Passeggero}
 */
public class Passeggero {
    private final String nome;
    private final String secondoNome;
    private final String cognome;
    private final String codiceFiscale;
    private final ArrayList<Bagaglio> bagagli = new ArrayList<>();

    /**
     * Costruttore di Passeggero.
     *
     * @param nome          il nome del passeggero
     * @param secondoNome   il secondo nome del passeggero
     * @param cognome       il cognome del passeggero
     * @param codiceFiscale il codice fiscale del passeggero
     */
    public Passeggero(String nome, String secondoNome, String cognome, String codiceFiscale){
        this.nome = nome;
        this.secondoNome = secondoNome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Aggiunge un bagaglio alla lista dei bagagli del passeggero.
     *
     * @param b il bagaglio da aggiungere
     */
    public void addBagaglio(Bagaglio b){
        bagagli.add(b);
    }

    /**
     * Getter nome.
     *
     * @return il nome del passeggero
     */
    public String getNome() {
        return nome;
    }

    /**
     * Getter bagagli.
     *
     * @return lista dei bagagli del passeggero
     */
    public List<Bagaglio> getBagagli() {return bagagli;}

    /**
     * Getter secondoNome
     *
     * @return il secondo nome del passeggero
     */
    public String getSecondoNome() {
        return secondoNome;
    }

    /**
     * Getter cognome.
     *
     * @return il cognome del passeggero
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Getter codice fiscale.
     *
     * @return il codice fiscale del passeggero
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
}
