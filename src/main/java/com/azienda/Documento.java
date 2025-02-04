package com.azienda;

import java.util.ArrayList;
import java.util.List;

// Classe per rappresentare un documento nel sistema
public class Documento {
    private int id;
    private String titolo;
    private String contenuto;
    private String autore;
    private String categoria;
    private List<String> paroleChiave;
    private List<String> utentiCondivisi;
    private List<String> commenti;
    private List<String> versioni;

    public Documento(int id, String titolo, String contenuto, String autore, String categoria, List<String> paroleChiave) {
        this.id = id;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.autore = autore;
        this.categoria = categoria;
        this.paroleChiave = paroleChiave;
        this.utentiCondivisi = new ArrayList<>();
        this.commenti = new ArrayList<>();
        this.versioni = new ArrayList<>();
        this.versioni.add(contenuto);
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getAutore() {
        return autore;
    }

    public String getContenuto() {
        return contenuto;
    }

    public List<String> getParoleChiave() {
        return paroleChiave;
    }

    public List<String> getUtentiCondivisi() {
        return utentiCondivisi;
    }

    public void aggiungiCommento(String autore, String contenuto) {
        commenti.add(autore + ": " + contenuto);
    }

    public List<String> getVersioni() {  // ✅ Aggiunto getter per versioni
        return new ArrayList<>(versioni);
    }
}
