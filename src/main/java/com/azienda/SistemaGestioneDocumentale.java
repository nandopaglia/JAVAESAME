package com.azienda;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SistemaGestioneDocumentale {
    private static final Logger logger = LogManager.getLogger(SistemaGestioneDocumentale.class);
    private static Scanner scanner = new Scanner(System.in);
    private static GestoreDocumenti gestoreDocumenti = new GestoreDocumenti();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nOpzioni:");
            System.out.println("1) Aggiungi Documento");
            System.out.println("2) Aggiungi Commento");
            System.out.println("3) Ricerca Documenti");
            System.out.println("4) Visualizza Documenti Condivisi");
            System.out.println("5) Condividi Documento");
            System.out.println("6) Visualizza Cronologia Versioni");
            System.out.println("7) Genera Report");
            System.out.println("8) Esci");
            System.out.print("Scelta: ");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    aggiungiDocumento();
                    break;
                case 2:
                    aggiungiCommento();
                    break;
                case 3:
                    ricercaDocumenti();
                    break;
                case 4:
                    visualizzaDocumentiCondivisi();
                    break;
                case 5:
                    condividiDocumento();
                    break;
                case 6:
                    visualizzaCronologiaVersioni();
                    break;
                case 7:
                    generaReport();
                    break;
                case 8:
                    logger.info("Chiusura del sistema.");
                    System.out.println("Chiusura sistema.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Scelta non valida, riprova.");
            }
        }
    }

    private static void aggiungiDocumento() {
        System.out.print("Titolo documento: ");
        String titolo = scanner.nextLine();
        System.out.print("Contenuto documento: ");
        String contenuto = scanner.nextLine();
        System.out.print("Autore: ");
        String autore = scanner.nextLine();
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();
        System.out.print("Inserisci parole chiave separate da virgola: ");
        List<String> paroleChiave = Arrays.asList(scanner.nextLine().split(","));

        gestoreDocumenti.aggiungiDocumento(titolo, contenuto, autore, categoria, paroleChiave);
    }

    private static void aggiungiCommento() {
        System.out.print("ID documento: ");
        int idDocumento = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Autore del commento: ");
        String autore = scanner.nextLine();
        System.out.print("Commento: ");
        String contenuto = scanner.nextLine();
        gestoreDocumenti.aggiungiCommento(idDocumento, autore, contenuto);
    }

    private static void ricercaDocumenti() {
        System.out.print("Inserisci parola chiave o autore da cercare: ");
        String query = scanner.nextLine();
        gestoreDocumenti.ricercaDocumenti(query);
    }

    private static void visualizzaDocumentiCondivisi() {
        System.out.print("Inserisci nome utente: ");
        String nomeUtente = scanner.nextLine();
        gestoreDocumenti.visualizzaDocumentiCondivisi(nomeUtente);
    }

    private static void condividiDocumento() {
        System.out.print("ID documento da condividere: ");
        int idDocumento = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome utente destinatario: ");
        String nomeUtente = scanner.nextLine();
        gestoreDocumenti.condividiDocumento(idDocumento, nomeUtente);
    }

    private static void visualizzaCronologiaVersioni() {
        System.out.print("ID documento: ");
        int idDocumento = scanner.nextInt();
        scanner.nextLine();
        gestoreDocumenti.visualizzaCronologiaVersioni(idDocumento);
    }

    private static void generaReport() {
        gestoreDocumenti.generaReport();
    }
}
