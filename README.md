GESTIONE DOCUMENTALE CLI
Questo progetto implementa un sistema di gestione documentale (Document Management System) CLI-based in Java, con funzionalità di autenticazione utenti, gestione di ruoli (Admin, User), creazione e ricerca di documenti, il tutto senza richiedere l’installazione di un database esterno (grazie a H2 embedded).

DESCRIZIONE GENERALE
L’applicazione consente di:

Eseguire il login con username e password, verificando i ruoli (Admin o User).
Gestire utenti (solo Admin) creando nuovi account e visualizzando l’elenco di tutti gli utenti.
Creare documenti assegnando un titolo e un contenuto testuale.
Visualizzare l’elenco dei documenti presenti nel sistema.
Cercare documenti per parola chiave (sia nel titolo che nel contenuto).
Il progetto utilizza un database H2 in modalità embedded (in-memory o file), quindi non richiede l’installazione di alcun RDBMS.
Il logging è gestito tramite Log4j.

FUNZIONALITA' PRINCIPALI
Autenticazione

Login tramite username/password.
Distinzione ruoli: Admin e User.
Gestione Utenti (solo Admin)

Creazione nuovi utenti con username, password e ruolo.
Elenco di tutti gli utenti esistenti.
Gestione Documenti

Creazione di nuovi documenti (titolo, contenuto, data creazione).
Elenco di tutti i documenti presenti.
Ricerca documenti per keyword su titolo/contenuto.
Struttura Database

Tabella users (id, username, password, role).
Tabella documents (id, title, content, created_date).
Caricamento e inizializzazione delle tabelle all’avvio (file schema.sql).

REQUISITI DI SISTEMA
Java versione 8 o superiore.
Maven (per compilare ed eseguire con tutte le dipendenze).
Nessun database esterno: viene utilizzato H2 embedded.


STRUTTURA DEL PROGETTO
Di seguito una panoramica essenziale:


gestione-documentale-cli/
├── pom.xml                      (file di configurazione Maven)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/azienda/
│   │   │       ├── Main.java            (classe principale CLI)
│   │   │       ├── manager/
│   │   │       │   ├── UserManager.java
│   │   │       │   └── DocumentManager.java
│   │   │       ├── model/
│   │   │       │   ├── User.java
│   │   │       │   └── Document.java
│   │   │       └── utils/
│   │   │           ├── DBConnection.java
│   │   │           └── LogConfig.java
│   │   └── resources/
│   │       ├── log4j.properties         (configurazione logging)
│   │       └── schema.sql               (creazione tabelle, dati iniziali)
└── README.md

Main.java: Contiene il punto di ingresso (metodo main) e gestisce il menù CLI.
manager/: Classi che implementano la logica per utenti (UserManager) e documenti (DocumentManager).
model/: Classi di modello dati (User, Document).
utils/: Classi di utilità (connessione a H2, configurazione log4j, etc.).
schema.sql: Script SQL che crea le tabelle (users, documents) e inserisce dati di prova (ad es. utente admin/admin).

ISTRUZIONI PER L'ESECUZIONE
Clonare o scaricare il progetto in locale (o in un ambiente con Maven e Java).

Aprire un terminale e posizionarsi nella cartella radice del progetto (dove si trova pom.xml).

Compilare con Maven:
mvn clean compile

Eseguire con Maven:
mvn exec:java

L’applicazione avvierà la CLI, inizializzerà il database (H2 in-memory o su file, a seconda della configurazione) e mostrerà il menù principale.

Menu principale:

Login: inserire username e password per accedere alle funzionalità.
Utente admin (ruolo ADMIN) può gestire utenti, creare nuovi account, visualizzare utenti.
Utente employee (ruolo EMPLOYEE) può accedere solo alle funzionalità di gestione documenti.
Gestione Documenti: creare nuovi documenti, elencarli, cercarli tramite keyword.
Chiusura: digitare 0 dal menù principale per uscire.

Note:
Non è necessario installare alcun DB esterno: H2 embedded si occuperà di creare le tabelle in memoria o su file.
Per personalizzare l’ambiente (nome DB, credenziali, ecc.), modificare i parametri in DBConnection.java.
Log4j scrive i log in console (o in application.log, se configurato). La configurazione è in log4j.properties.
È possibile estendere il progetto con ulteriori funzionalità (commenti, condivisione documenti, versioning, statistiche avanzate, interfaccia grafica, ecc.).