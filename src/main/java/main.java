import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.Gestore;
import entities.Utente;
import entities.Tessera;
import enumerating.TipoAbbonamento;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionetrasporti");
        EntityManager em = emf.createEntityManager();

        Gestore gestore = new Gestore(em);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        Utente utenteLoggato = null;

        while (running) {
            if (utenteLoggato == null) {
                System.out.println("\n--- MENU PRINCIPALE ---");
                System.out.println("1. Registrati");
                System.out.println("2. Login");
                System.out.println("0. Esci");
                System.out.print("Scelta: ");
                int scelta = Integer.parseInt(scanner.nextLine());

                switch (scelta) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Cognome: ");
                        String cognome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();

                        em.getTransaction().begin();
                        gestore.registraUtente(nome, cognome, email, password);
                        em.getTransaction().commit();

                        System.out.println("Utente registrato!");
                        break;

                    case 2:
                        System.out.print("Email: ");
                        String emailLogin = scanner.nextLine();
                        System.out.print("Password: ");
                        String passLogin = scanner.nextLine();

                        utenteLoggato = gestore.login(emailLogin, passLogin);
                        if (utenteLoggato != null) {
                            System.out.println("Login effettuato, benvenuto " + utenteLoggato.getNome());
                        } else {
                            System.out.println("Credenziali errate, riprova.");
                        }
                        break;

                    case 0:
                        running = false;
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                }

            } else {
                // Menu utente loggato
                System.out.println("\n--- MENU UTENTE ---");
                System.out.println("1. Crea tessera");
                System.out.println("2. Visualizza tessere");
                System.out.println("3. Acquista biglietto");
                System.out.println("4. Visualizza biglietti di una tessera");
                System.out.println("5. Genera abbonamento");
                System.out.println("6. Logout");
                System.out.print("Scelta: ");
                int scelta = Integer.parseInt(scanner.nextLine());

                switch (scelta) {
                    case 1:
                        em.getTransaction().begin();
                        Tessera tessera = gestore.creaTessera(LocalDate.now(), utenteLoggato);
                        em.getTransaction().commit();
                        System.out.println("Tessera creata con ID: " + tessera.getId_tessera());
                        break;

                    case 2:

                        System.out.println("Funzionalità non implementata: visualizza tessere");
                        break;

                    case 3:
                        System.out.print("Inserisci ID tessera: ");
                        Long idTesseraBiglietto = Long.parseLong(scanner.nextLine());
                        Tessera tesseraPerBiglietto = gestore.getTesseraById(idTesseraBiglietto);
                        if (tesseraPerBiglietto == null) {
                            System.out.println("Tessera non trovata.");
                            break;
                        }
                        System.out.print("Inserisci ID punto emissione: ");
                        Long idPunto = Long.parseLong(scanner.nextLine());
                        var puntoEmissione = gestore.getPuntoEmissioneById(idPunto);
                        if (puntoEmissione == null) {
                            System.out.println("Punto emissione non trovato.");
                            break;
                        }

                        em.getTransaction().begin();
                        gestore.acquistaBiglietto(tesseraPerBiglietto, puntoEmissione);
                        em.getTransaction().commit();
                        System.out.println("Biglietto acquistato.");
                        break;

                    case 4:
                        System.out.print("Inserisci ID tessera: ");
                        Long idTesseraBiglietti = Long.parseLong(scanner.nextLine());
                        List<?> biglietti = gestore.getBigliettiByTessera(idTesseraBiglietti);
                        if (biglietti.isEmpty()) {
                            System.out.println("Nessun biglietto trovato.");
                        } else {
                            for (Object b : biglietti) {
                                System.out.println(b.toString());
                            }
                        }
                        break;

                    case 5:
                        System.out.print("Inserisci ID tessera: ");
                        Long idTesseraAbbonamento = Long.parseLong(scanner.nextLine());
                        Tessera tesseraAbbonamento = gestore.getTesseraById(idTesseraAbbonamento);
                        if (tesseraAbbonamento == null) {
                            System.out.println("Tessera non trovata.");
                            break;
                        }
                        System.out.print("Inserisci ID punto emissione: ");
                        Long idPuntoAbbonamento = Long.parseLong(scanner.nextLine());
                        var puntoAbbonamento = gestore.getPuntoEmissioneById(idPuntoAbbonamento);
                        if (puntoAbbonamento == null) {
                            System.out.println("Punto emissione non trovato.");
                            break;
                        }
                        System.out.println("Scegli tipo abbonamento (1=SETTIMANALE, 2=MENSILE): ");
                        int tipoAbbonamentoInt = Integer.parseInt(scanner.nextLine());
                        TipoAbbonamento tipoAbbonamento = (tipoAbbonamentoInt == 1) ? TipoAbbonamento.SETTIMANALE : TipoAbbonamento.MENSILE;

                        em.getTransaction().begin();
                        gestore.generaAbbonamento(tesseraAbbonamento, puntoAbbonamento, tipoAbbonamento);
                        em.getTransaction().commit();

                        System.out.println("Abbonamento generato.");
                        break;

                    case 6:
                        utenteLoggato = null;
                        System.out.println("Logout effettuato.");
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                }
            }
        }

        scanner.close();
        em.close();
        emf.close();

        System.out.println("Applicazione terminata.");
    }
}
