import entities.*;
import enumerating.StatoMezzo;
import enumerating.TipoAbbonamento;
import enumerating.Ruolo;
import enumerating.TipoMezzo;
import service.Gestore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class main {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Gestore gestore;
    private static Scanner scanner = new Scanner(System.in);
    private static Utente loggedInUser;

    public static void main(String[] args) {
        try {

            emf = Persistence.createEntityManagerFactory("gestionetrasporti");
            em = emf.createEntityManager();
            gestore = new Gestore(em);

            // Main loop
            boolean running = true;
            while (running) {
                System.out.println("\n=== Sistema Trasporti Pubblici ===");
                System.out.println("1. Login");
                System.out.println("2. Registrazione");
                System.out.println("0. Esci");
                System.out.print("Scelta: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Scelta non valida.");
                }
            }
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
            scanner.close();
        }
    }

    private static void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        loggedInUser = gestore.login(email, password);
        if (loggedInUser != null) {
            System.out.println("Login effettuato con successo!");
            if (loggedInUser.getRuolo() == Ruolo.ADMIN) {
                showAdminMenu();
            } else {
                showClientMenu();
            }
        } else {
            System.out.println("Credenziali non valide.");
        }
    }

    private static void register() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        gestore.registraUtente(nome, cognome, email, password);
        System.out.println("Registrazione completata con successo!");
    }

    private static void showAdminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== MENU AMMINISTRATORE ===");
            System.out.println("1. Gestione Mezzi");
            System.out.println("2. Gestione Tratte");
            System.out.println("3. Gestione Punti di Emissione");
            System.out.println("4. Gestione Manutenzioni");
            System.out.println("5. Gestione Servizi");
            System.out.println("6. Valida Biglietto");
            System.out.println("7. Crea Tessera");
            System.out.println("8. Visualizza Tessera");
            System.out.println("9. Visualizza Biglietti");
            System.out.println("10.Visualizza Abbonamenti");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageVehicles();
                    break;
                case 2:
                    manageRoutes();
                    break;
                case 3:
                    manageEmissionPoints();
                    break;
                case 4:
                    manageMaintenance();
                    break;
                case 5:
                    manageServices();
                    break;
                case 6:
                    validateTicket();
                    break;
                case 7:
                    createCard();
                    break;
                case 8:
                    viewCard();
                    break;
                case 9:
                    viewTickets();
                    break;
                case 10:
                    viewSubscriptions();
                    break;
                case 0:
                    back = true;
                    loggedInUser = null;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    private static void showClientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Crea Tessera");
            System.out.println("2. Acquista Biglietto");
            System.out.println("3. Acquista Abbonamento");
            System.out.println("4. Visualizza Tessera");
            System.out.println("5. Visualizza Biglietti");
            System.out.println("6. Visualizza Abbonamenti");
            System.out.println("7. Valida Biglietti");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createCard();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    buySubscription();
                    break;
                case 4:
                    viewCard();
                    break;
                case 5:
                    viewTickets();
                    break;
                case 6:
                    viewSubscriptions();
                    break;
                case 7:
                    validateTicket();
                    break;
                case 0:
                    back = true;
                    loggedInUser = null;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    // Admin menu methods
    private static void manageVehicles() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== GESTIONE MEZZI ===");
            System.out.println("1. Aggiungi Mezzo");
            System.out.println("2. Modifica Mezzo");
            System.out.println("3. Rimuovi Mezzo");
            System.out.println("4. Visualizza Mezzi");
            System.out.println("0. Indietro");
            System.out.print("Scelta: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    editVehicle();
                    break;
                case 3:
                    removeVehicle();
                    break;
                case 4:
                    viewVehicles();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    private static void addVehicle() {
        System.out.println("Tipo mezzo (1. Tram, 2. Autobus):");
        int tipoChoice = scanner.nextInt();
        scanner.nextLine();
        TipoMezzo tipoMezzo = (tipoChoice == 1) ? TipoMezzo.Tram : TipoMezzo.Autobus;

        System.out.println("Stato mezzo (1. In servizio, 2. In manutenzione, 3. Fuori servizio):");
        int statoChoice = scanner.nextInt();
        scanner.nextLine();
        StatoMezzo statoMezzo = StatoMezzo.values()[statoChoice-1];

        System.out.print("Numero identificativo: ");
        int numeroIdentificativo = scanner.nextInt();
        scanner.nextLine();

        Mezzo mezzo = new Mezzo(tipoMezzo, statoMezzo, numeroIdentificativo);
        gestore.salvaMezzo(mezzo);
        System.out.println("Mezzo aggiunto con successo!");
    }


    private static void editVehicle() {
        System.out.print("Inserisci l'ID del mezzo da modificare: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Mezzo mezzo = gestore.getMezzoById(id);
        if (mezzo == null) {
            System.out.println("Mezzo non trovato.");
            return;
        }

        System.out.println("Tipo mezzo attuale: " + mezzo.getTipoMezzo());
        System.out.println("Cambiare tipo? (1. Tram, 2. Autobus, 0. Mantieni):");
        int tipoChoice = scanner.nextInt();
        scanner.nextLine();
        if (tipoChoice > 0) {
            mezzo.setTipoMezzo((tipoChoice == 1) ? TipoMezzo.Tram : TipoMezzo.Autobus);
        }

        System.out.println("Stato mezzo attuale: " + mezzo.getStatoMezzo());
        System.out.println("Cambiare stato? (1. In servizio, 2. In manutenzione, 3. Fuori servizio, 0. Mantieni):");
        int statoChoice = scanner.nextInt();
        scanner.nextLine();
        if (statoChoice > 0) {
            mezzo.setStatoMezzo(StatoMezzo.values()[statoChoice-1]);
        }

        System.out.println("Numero identificativo attuale: " + mezzo.getNumeroIdentificativo());
        System.out.print("Nuovo numero identificativo (0 per mantenere): ");
        int nuovoNumero = scanner.nextInt();
        scanner.nextLine();
        if (nuovoNumero > 0) {
            mezzo.setNumeroIdentificativo(nuovoNumero);
        }

        gestore.aggiornaMezzo(mezzo);
        System.out.println("Mezzo aggiornato con successo!");
    }

    private static void removeVehicle() {
        System.out.print("Inserisci l'ID del mezzo da rimuovere: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        gestore.rimuoviMezzo(id);
        System.out.println("Mezzo rimosso con successo!");
    }

    private static void viewVehicles() {
        List<Mezzo> mezzi = gestore.getAllVehicles();
        if (mezzi == null || mezzi.isEmpty()) {
            System.out.println("Nessun mezzo trovato.");
            return;
        }

        System.out.println("Elenco mezzi disponibili:");
        for (Mezzo m : mezzi) {
            System.out.printf("ID: %d | Tipo: %s | Stato: %s | Numero: %d | Capienza: %d%n",
                    m.getId(),
                    m.getTipoMezzo(),
                    m.getStatoMezzo(),
                    m.getNumeroIdentificativo(),
                    m.getCapienza());
        }
    }

    private static void manageRoutes() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== GESTIONE TRATTE ===");
            System.out.println("1. Aggiungi Tratta");
            System.out.println("2. Visualizza Tratte");
            System.out.println("0. Indietro");
            System.out.print("Scelta: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addRoute();
                    break;
                case 2:
                    viewRoutes();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    private static void addRoute() {
        System.out.print("Inserisci la zona di partenza: ");
        String partenza = scanner.nextLine();
        System.out.print("Inserisci la zona di arrivo: ");
        String arrivo = scanner.nextLine();
        System.out.print("Inserisci il tempo medio di percorrenza (minuti): ");
        long tempoMedio = scanner.nextLong();  // long per coerenza con il campo
        scanner.nextLine(); // consuma newline

        Tratta tratta = new Tratta();
        tratta.setZonaPartenza(partenza);
        tratta.setCapolinea(arrivo);
        tratta.setTempoPrevistoPartenza(tempoMedio);  // <-- qui

        gestore.registraTratta(tratta);
        System.out.println("Tratta aggiunta con successo!");
    }

    private static void viewRoutes() {
        List<Tratta> tratte = gestore.getAllTratte();
        if (tratte.isEmpty()) {
            System.out.println("Nessuna tratta trovata.");
        } else {
            for (Tratta t : tratte) {
                System.out.println("ID: " + t.getIdTratta() +
                        " | Partenza: " + t.getZonaPartenza() +
                        " | Arrivo: " + t.getCapolinea() +
                        " | Tempo previsto (min): " + t.getTempoPrevistoPartenza());
            }
        }
    }


    private static void manageEmissionPoints() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== GESTIONE PUNTI DI EMISSIONE ===");
            System.out.println("1. Aggiungi Punto di Emissione");
            System.out.println("2. Visualizza Punti di Emissione");
            System.out.println("0. Indietro");
            System.out.print("Scelta: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmissionPoint();
                    break;
                case 2:
                    viewEmissionPoints();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    private static void addEmissionPoint() {
        System.out.print("Tipo (rivenditore/distributore): ");
        String tipo = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        boolean attivo = true;
        if (tipo.equalsIgnoreCase("distributore")) {
            System.out.print("Attivo (true/false): ");
            attivo = scanner.nextBoolean();
            scanner.nextLine();
        }

        gestore.creaPuntoEmissione(tipo, nome, attivo);
    }

    private static void viewEmissionPoints() {
        List<PuntoEmissione> punti = gestore.getAllPuntiEmissione();
        if (punti.isEmpty()) {
            System.out.println("Nessun punto di emissione trovato.");
        } else {
            System.out.println("Punti di emissione disponibili:");
            for (PuntoEmissione p : punti) {
                System.out.println(p);
            }
        }
    }

    private static void manageMaintenance() {
        System.out.print("Inserisci l'ID del mezzo: ");
        Long idMezzo = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Data inizio manutenzione (YYYY-MM-DD): ");
        String inizioStr = scanner.nextLine();
        LocalDate dataInizio = LocalDate.parse(inizioStr);

        System.out.print("Data fine manutenzione (YYYY-MM-DD): ");
        String fineStr = scanner.nextLine();
        LocalDate dataFine = LocalDate.parse(fineStr);

        Mezzo mezzo = gestore.getMezzoById(idMezzo);
        if (mezzo == null) {
            System.out.println("Mezzo non trovato.");
            return;
        }

        gestore.registraManutenzione(mezzo, dataInizio, dataFine);
    }

    private static void manageServices() {
        System.out.print("Inserisci l'ID del mezzo: ");
        Long idMezzo = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Inserisci l'ID della tratta: ");
        int idTratta = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Data inizio servizio (YYYY-MM-DD): ");
        String inizioStr = scanner.nextLine();
        LocalDate dataInizio = LocalDate.parse(inizioStr);

        System.out.print("Data fine servizio (YYYY-MM-DD): ");
        String fineStr = scanner.nextLine();
        LocalDate dataFine = LocalDate.parse(fineStr);

        Mezzo mezzo = gestore.getMezzoById(idMezzo);
        Tratta tratta = gestore.getTrattaById(idTratta);

        if (mezzo == null || tratta == null) {
            System.out.println("Mezzo o tratta non trovati.");
            return;
        }

        gestore.assegnaTrattaAMezzo(mezzo, tratta, dataInizio, dataFine);
    }

    private static void validateTicket() {
        System.out.print("Inserisci l'ID del biglietto: ");
        Long idBiglietto = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Inserisci l'ID del mezzo: ");
        Long idMezzo = scanner.nextLong();
        scanner.nextLine();

        gestore.validaBiglietto(idBiglietto, idMezzo);
    }

    // Client menu methods
    private static void createCard() {
        if (loggedInUser == null) {
            System.out.println("Devi essere loggato per creare una tessera.");
            return;
        }

        Tessera tessera = gestore.creaTessera(LocalDate.now(), loggedInUser);
        System.out.println("Tessera creata con successo! ID: " + tessera.getId_tessera());
    }

    private static void buyTicket() {
        if (loggedInUser == null) {
            System.out.println("Devi essere loggato per acquistare un biglietto.");
            return;
        }

        List<Tessera> tessere = gestore.getTessereByUtente(loggedInUser.getId_utente());
        if (tessere.isEmpty()) {
            System.out.println("Devi prima creare una tessera.");
            return;
        }

        System.out.println("Le tue tessere:");
        for (Tessera t : tessere) {
            System.out.println("ID: " + t.getId_tessera() + " - Data emissione: " + t.getData_emissione());
        }

        System.out.print("Inserisci l'ID della tessera da usare: ");
        Long idTessera = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Inserisci l'ID del punto di emissione: ");
        Long idPuntoEmissione = scanner.nextLong();
        scanner.nextLine();

        Tessera tessera = gestore.getTesseraById(idTessera);
        PuntoEmissione puntoEmissione = gestore.getPuntoEmissioneById(idPuntoEmissione);

        if (tessera == null || puntoEmissione == null) {
            System.out.println("Tessera o punto di emissione non trovati.");
            return;
        }

        gestore.acquistaBiglietto(tessera, puntoEmissione);
        System.out.println("Biglietto acquistato con successo!");
    }

    private static void buySubscription() {
        if (loggedInUser == null) {
            System.out.println("Devi essere loggato per acquistare un abbonamento.");
            return;
        }

        List<Tessera> tessere = gestore.getTessereByUtente(loggedInUser.getId_utente());
        if (tessere.isEmpty()) {
            System.out.println("Devi prima creare una tessera.");
            return;
        }

        System.out.println("Le tue tessere:");
        for (Tessera t : tessere) {
            System.out.println("ID: " + t.getId_tessera() + " - Data emissione: " + t.getData_emissione());
        }

        System.out.print("Inserisci l'ID della tessera da usare: ");
        Long idTessera = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Inserisci l'ID del punto di emissione: ");
        Long idPuntoEmissione = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Tipo abbonamento:");
        System.out.println("1. Settimanale");
        System.out.println("2. Mensile");
        System.out.print("Scelta: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        TipoAbbonamento tipoAbbonamento = (tipo == 1) ? TipoAbbonamento.SETTIMANALE : TipoAbbonamento.MENSILE;

        Tessera tessera = gestore.getTesseraById(idTessera);
        PuntoEmissione puntoEmissione = gestore.getPuntoEmissioneById(idPuntoEmissione);

        if (tessera == null || puntoEmissione == null) {
            System.out.println("Tessera o punto di emissione non trovati.");
            return;
        }

        gestore.generaAbbonamento(tessera, puntoEmissione, tipoAbbonamento);
        System.out.println("Abbonamento acquistato con successo!");
    }

    private static void viewCard() {
        if (loggedInUser == null) {
            System.out.println("Devi essere loggato per visualizzare le tessere.");
            return;
        }

        List<Tessera> tessere = gestore.getTessereByUtente(loggedInUser.getId_utente());
        if (tessere.isEmpty()) {
            System.out.println("Nessuna tessera trovata.");
            return;
        }

        System.out.println("\nLe tue tessere:");
        for (Tessera t : tessere) {
            System.out.println(t);
        }
    }

    private static void viewTickets() {
        if (loggedInUser == null) {
            System.out.println("Devi essere loggato per visualizzare i biglietti.");
            return;
        }

        List<Tessera> tessere = gestore.getTessereByUtente(loggedInUser.getId_utente());
        if (tessere.isEmpty()) {
            System.out.println("Nessuna tessera trovata.");
            return;
        }

        System.out.println("\nI tuoi biglietti:");
        for (Tessera t : tessere) {
            List<Biglietto> biglietti = gestore.getBigliettiByTessera(t.getId_tessera());
            if (!biglietti.isEmpty()) {
                System.out.println("\nTessera ID: " + t.getId_tessera());
                for (Biglietto b : biglietti) {
                    System.out.println(b);
                }
            }
        }
    }

    private static void viewSubscriptions() {
        if (loggedInUser == null) {
            System.out.println("Devi essere loggato per visualizzare gli abbonamenti.");
            return;
        }

        List<Tessera> tessere = gestore.getTessereByUtente(loggedInUser.getId_utente());
        if (tessere.isEmpty()) {
            System.out.println("Nessuna tessera trovata.");
            return;
        }

        System.out.println("\nI tuoi abbonamenti:");
        for (Tessera t : tessere) {
            List<Abbonamento> abbonamenti = gestore.getAbbonamentiByTessera(t.getId_tessera());
            if (!abbonamenti.isEmpty()) {
                System.out.println("\nTessera ID: " + t.getId_tessera());
                for (Abbonamento a : abbonamenti) {
                    System.out.println(a);
                }
            }
        }
    }
}