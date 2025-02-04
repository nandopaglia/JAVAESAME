package com.azienda;

import com.azienda.utils.LogConfig;
import com.azienda.utils.DBConnection;
import com.azienda.manager.UserManager;
import com.azienda.manager.DocumentManager;
import com.azienda.model.User;        // <--- Importa la classe User
import com.azienda.model.Document;   // <--- Importa Document
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;               // <--- Import List
import java.util.Scanner;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        // 1) Inizializza log4j
        LogConfig.init();
        logger.info("Avvio applicazione Gestione Documentale CLI");

        // 2) Inizializza il database (crea tabelle, dati iniziali, ecc.)
        initDatabase();

        // 3) Menu CLI
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== MENU PRINCIPALE =====");
            System.out.println("1. Login");
            System.out.println("2. Gestione Documenti");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Login e (eventuale) menu admin
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    UserManager um = new UserManager();
                    User loggedUser = um.login(username, password);

                    if (loggedUser != null) {
                        System.out.println("Login OK! Ruolo: " + loggedUser.getRole());

                        if ("ADMIN".equalsIgnoreCase(loggedUser.getRole())) {
                            // Sottomenù gestione utenti
                            boolean backAdmin = false;
                            while (!backAdmin) {
                                System.out.println("\n=== GESTIONE UTENTI (ADMIN) ===");
                                System.out.println("1. Crea nuovo utente");
                                System.out.println("2. Elenca tutti gli utenti");
                                System.out.println("0. Torna al menu principale");
                                System.out.print("Scelta: ");
                                String adminChoice = scanner.nextLine();

                                switch (adminChoice) {
                                    case "1":
                                        // Creazione nuovo utente
                                        System.out.print("Nuovo username: ");
                                        String newU = scanner.nextLine();
                                        System.out.print("Nuova password: ");
                                        String newP = scanner.nextLine();
                                        System.out.print("Ruolo (ADMIN/USER): ");
                                        String newR = scanner.nextLine();
                                        um.createUser(newU, newP, newR);
                                        System.out.println("Utente creato!");
                                        break;

                                    case "2":
                                        // Elenco utenti
                                        List<User> userList = um.listUsers(); 
                                        // ^-- Assicurati che listUsers() esista in UserManager
                                        System.out.println("=== LISTA UTENTI ===");
                                        for (User u : userList) {
                                            System.out.println(
                                                "ID=" + u.getId()
                                                + " User=" + u.getUsername()
                                                + " Role=" + u.getRole()
                                            );
                                        }
                                        break;

                                    case "0":
                                        backAdmin = true;
                                        break;

                                    default:
                                        System.out.println("Scelta non valida in gestione utenti (ADMIN).");
                                }
                            }
                        } else {
                            System.out.println("Benvenuto, utente NON admin.");
                            // Se vuoi, altre funzioni per EMPLOYEE...
                        }

                    } else {
                        System.out.println("Credenziali errate.");
                    }
                    break;

                case "2":
                    // Gestione Documenti
                    boolean back = false;
                    while (!back) {
                        System.out.println("\n=== GESTIONE DOCUMENTI ===");
                        System.out.println("1. Crea nuovo documento");
                        System.out.println("2. Elenca documenti");
                        System.out.println("3. Cerca documenti");
                        System.out.println("0. Torna al menu principale");
                        System.out.print("Scelta: ");
                        String docChoice = scanner.nextLine();

                        DocumentManager dm = new DocumentManager();

                        switch (docChoice) {
                            case "1":
                                System.out.print("Titolo documento: ");
                                String title = scanner.nextLine();
                                System.out.print("Contenuto (stringa breve): ");
                                String content = scanner.nextLine();

                                dm.createDocument(title, content);
                                System.out.println("Documento creato con successo.");
                                break;

                            case "2":
                                System.out.println("=== ELENCO DOCUMENTI ===");
                                List<Document> docs = dm.listDocuments();
                                for (Document d : docs) {
                                    System.out.println("ID=" + d.getId()
                                        + ", Titolo=" + d.getTitle()
                                        + ", Data=" + d.getCreatedDate());
                                }
                                break;

                            case "3":
                                System.out.print("Inserisci parola chiave: ");
                                String keyword = scanner.nextLine();
                                List<Document> foundDocs = dm.searchDocuments(keyword);
                                System.out.println("=== RISULTATI RICERCA ===");
                                for (Document d : foundDocs) {
                                    System.out.println("ID=" + d.getId()
                                        + ", Titolo=" + d.getTitle());
                                }
                                break;

                            case "0":
                                back = true;
                                break;

                            default:
                                System.out.println("Scelta non valida nella gestione documenti.");
                        }
                    }
                    break;

                case "0":
                    exit = true;
                    logger.info("Chiusura applicazione.");
                    break;

                default:
                    System.out.println("Scelta non valida");
            }
        }
        scanner.close();
        System.out.println("Fine programma.");
    }

    /**
     * Legge il file schema.sql da src/main/resources/ e lo esegue
     * per creare le tabelle e popolare dati iniziali nel database H2.
     */
    private static void initDatabase() {
        try (Connection conn = DBConnection.getConnection()) {
            // Legge lo script SQL dalle risorse
            InputStream is = Main.class.getResourceAsStream("/schema.sql");
            if (is == null) {
                System.err.println("File schema.sql non trovato in /resources!");
                return;
            }

            // Converte il contenuto del file in stringa
            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // Esegue i comandi SQL
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();

            System.out.println("Database inizializzato correttamente.");
            logger.info("Database inizializzato correttamente.");
        } catch (IOException e) {
            logger.error("Errore IO nel caricamento di schema.sql", e);
        } catch (Exception e) {
            logger.error("Errore nell'inizializzazione del DB", e);
        }
    }
}
