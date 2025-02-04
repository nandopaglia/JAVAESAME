package com.azienda;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Classe per la gestione dei documenti
public class GestoreDocumenti {
    private static final Logger logger = LogManager.getLogger(GestoreDocumenti.class);
    
    private Map<Integer, Documento> documenti;
    private int contatoreDocumenti;

    public GestoreDocumenti() {
        documenti = new HashMap<>();
        contatoreDocumenti = 1;
    }

    public void aggiungiDocumento(String titolo, String contenuto, String autore, String categoria, List<String> paroleChiave) {
        Documento doc = new Documento(contatoreDocumenti++, titolo, contenuto, autore, categoria, paroleChiave);
        documenti.put(doc.getId(), doc);
        logger.info("Documento aggiunto: " + titolo + " da " + autore);
    }

    public void aggiungiCommento(int idDocumento, String autore, String contenuto) {
        Documento doc = documenti.get(idDocumento);
        if (doc != null) {
            doc.aggiungiCommento(autore, contenuto);
            logger.info("Commento aggiunto al documento " + idDocumento + " da " + autore);
        } else {
            logger.warn("Tentativo di aggiungere un commento a un documento inesistente (ID: " + idDocumento + ")");
        }
    }

    public List<Documento> ricercaDocumenti(String parolaChiave) {
        List<Documento> risultati = new ArrayList<>();
        for (Documento doc : documenti.values()) {
            if (doc.getParoleChiave().contains(parolaChiave)) {
                risultati.add(doc);
            }
        }
        return risultati;
    }

    public List<Documento> visualizzaDocumentiCondivisi(String utente) {
        List<Documento> condivisi = new ArrayList<>();
        for (Documento doc : documenti.values()) {
            if (doc.getUtentiCondivisi().contains(utente)) {
                condivisi.add(doc);
            }
        }
        return condivisi;
    }

    public void condividiDocumento(int idDocumento, String utente) {
        Documento doc = documenti.get(idDocumento);
        if (doc != null) {
            doc.getUtentiCondivisi().add(utente);
            logger.info("Documento " + idDocumento + " condiviso con " + utente);
        } else {
            logger.warn("Tentativo di condividere un documento inesistente (ID: " + idDocumento + ")");
        }
    }

    public List<String> visualizzaCronologiaVersioni(int idDocumento) {
        Documento doc = documenti.get(idDocumento);
        return (doc != null) ? doc.versioni : Collections.emptyList();
    }

    public void generaReport() {
        logger.info("Generazione del report dei documenti...");
        for (Documento doc : documenti.values()) {
            logger.info("Documento ID: " + doc.getId() + ", Titolo: " + doc.getTitolo() + ", Autore: " + doc.getAutore());
        }
    }
}
