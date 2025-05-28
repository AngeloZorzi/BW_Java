package service;

import dao.*;
import entities.*;
import enumerating.TipoAbbonamento;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Gestore {

    private final BigliettoDAO bigliettoDAO;
    private final AbbonamentoDAO abbonamentoDAO;
    private final TesseraDAO tesseraDAO;
    private final MezzoDAO mezzoDAO;
    private final TrattaDAO trattaDAO;
    private final PuntoEmissioneDAO puntoEmissioneDAO;
    private final UtenteDAO utenteDAO;
    private final PeriodoServizioDAO periodoServizioDAO;
    private final PeriodoManutenzioneDAO periodoManutenzioneDAO;
    private final PercorrenzaTrattaDAO percorrenzaTrattaDAO;
    private EntityManager em;

    public Gestore(EntityManager em) {
        this.em = em;

        this.bigliettoDAO = new BigliettoDAO(em);
        this.abbonamentoDAO = new AbbonamentoDAO(em);
        this.tesseraDAO = new TesseraDAO(em);
        this.mezzoDAO = new MezzoDAO(em);
        this.trattaDAO = new TrattaDAO(em);
        this.puntoEmissioneDAO = new PuntoEmissioneDAO(em);
        this.utenteDAO = new UtenteDAO(em);
        this.periodoManutenzioneDAO = new PeriodoManutenzioneDAO(em);
        this.periodoServizioDAO = new PeriodoServizioDAO(em);
        this.percorrenzaTrattaDAO = new PercorrenzaTrattaDAO(em);
    }

    public void registraUtente(String nome, String cognome, String email, String password) {
        try {
            em.getTransaction().begin();
            Utente utente = new Utente(nome, cognome, email, password);
            utenteDAO.save(utente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public Utente login(String email, String password) {
        return utenteDAO.trovaPerEmailPassword(email, password);
    }

    public Tessera creaTessera(LocalDate dataEmissione, Utente utente) {
        Tessera tessera = new Tessera(dataEmissione, utente);
        try {
            em.getTransaction().begin();
            tesseraDAO.save(tessera);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            tessera = null; // oppure gestisci diversamente
        }
        return tessera;
    }


    public List<Tessera> getTessereByUtente(Long utenteId) {
        return tesseraDAO.findByUtenteId(utenteId);
    }



    public Tessera getTesseraById(Long id) {
        return tesseraDAO.trovaPerId(id);
    }



    public void acquistaBiglietto(Tessera tessera, PuntoEmissione puntoEmissione) {
        try {
            em.getTransaction().begin();
            Biglietto biglietto = new Biglietto(puntoEmissione, tessera);
            bigliettoDAO.save(biglietto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public List<Biglietto> getBigliettiByTessera(Long tesseraId) {
        return bigliettoDAO.findByTesseraId(tesseraId);
    }


    public void generaAbbonamento(Tessera tessera, PuntoEmissione puntoEmissione, TipoAbbonamento tipoAbbonamento) {
        LocalDate oggi = LocalDate.now();
        LocalDate dataFine;

        switch (tipoAbbonamento) {
            case SETTIMANALE:
                dataFine = oggi.plusWeeks(1);
                break;
            case MENSILE:
                dataFine = oggi.plusMonths(1);
                break;
            default:
                dataFine = oggi.plusMonths(1); // fallback sicuro
        }

        Abbonamento abbonamento = new Abbonamento();
        abbonamento.setData_inizio_validita(oggi);
        abbonamento.setData_fine_validita(dataFine);
        abbonamento.setTipo(tipoAbbonamento);
        abbonamento.setPuntoEmissione(puntoEmissione);
        abbonamento.setTessera(tessera);

        abbonamentoDAO.salva(abbonamento);
    }

    public List<Abbonamento> getAbbonamentiByTessera(Long tesseraId) {
        return abbonamentoDAO.trovaAbbonamentiPerTessera(tesseraId);
    }

    public List<Mezzo> getAllVehicles() {
        return mezzoDAO.findAll();
    }


    public void salvaMezzo(Mezzo mezzo) {
        try {
            em.getTransaction().begin();
            mezzoDAO.salvaMezzo(mezzo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public Mezzo getMezzoById(Long id) {
        return mezzoDAO.getId(id);
    }

    public Mezzo aggiornaMezzo(Mezzo mezzo) {
        Mezzo result = null;
        try {
            em.getTransaction().begin();
            result = mezzoDAO.salvaMezzo(mezzo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
        return result;
    }

    public void rimuoviMezzo(Long id) {
        try {
            em.getTransaction().begin();
            Mezzo mezzo = mezzoDAO.getId(id);
            if (mezzo != null) {
                mezzoDAO.rimuoviMezzo(id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public long contaManutenzioniMezzo(Long idMezzo) {
        return periodoManutenzioneDAO.contaPeriodiManutenzione(idMezzo);
    }

    public long contaServiziMezzo(Long idMezzo) {
        return periodoServizioDAO.contaPeriodiServizio(idMezzo);
    }

    public long getGiorniTotaliServizio(Long idMezzo) {
        return periodoServizioDAO.tempoTotaleServizio(idMezzo);
    }


    public void registraTratta(Tratta tratta) {
        try {
            em.getTransaction().begin();
            trattaDAO.salva(tratta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public Tratta getTrattaById(int id) {
        return trattaDAO.trovaPerId(id);
    }

    public List<Tratta> getAllTratte() {
        return trattaDAO.trovaTutti();
    }

    public void creaPuntoEmissione(String tipo, String nome, boolean attivo) {
        em.getTransaction().begin();
        try {
            if (tipo.equalsIgnoreCase("rivenditore")) {
                RivenditoreAutorizzato r = new RivenditoreAutorizzato(nome);
                puntoEmissioneDAO.save(r);
                System.out.println("Rivenditore creato.");
            } else if (tipo.equalsIgnoreCase("distributore")) {
                DistributoreAutomatico d = new DistributoreAutomatico(nome, attivo);
                puntoEmissioneDAO.save(d);
                System.out.println("Distributore creato.");
            } else {
                System.out.println("Tipo non riconosciuto.");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Errore nella creazione del punto di emissione: " + e.getMessage());
        }
    }


    public List<PuntoEmissione> getAllPuntiEmissione() {
        return puntoEmissioneDAO.findAll();
    }


    public PuntoEmissione getPuntoEmissioneById(Long id) {
        return puntoEmissioneDAO.findById(id);
    }

    public void validaBiglietto(Long idBiglietto, Long idMezzo) {
        Biglietto biglietto = bigliettoDAO.getById(idBiglietto);
        if (biglietto == null) {
            System.out.println("Biglietto non trovato.");
            return;
        }

        if (biglietto.isVidimato()) {
            System.out.println("Il biglietto è già stato vidimato.");
            return;
        }

        Mezzo mezzo = mezzoDAO.getId(idMezzo);
        if (mezzo == null) {
            System.out.println("Mezzo non trovato.");
            return;
        }

        biglietto.valida(mezzo);
        bigliettoDAO.update(biglietto);
        System.out.println("Biglietto vidimato con successo.");
    }
    public void registraManutenzione(Mezzo mezzo, LocalDate dataInizio, LocalDate dataFine) {
        if (mezzo == null || dataInizio == null || dataFine == null || dataFine.isBefore(dataInizio)) {
            System.out.println("Dati non validi per la manutenzione.");
            return;
        }
        try {
            em.getTransaction().begin();
            PeriodoManutenzione manutenzione = new PeriodoManutenzione(dataInizio, dataFine, mezzo);
            periodoManutenzioneDAO.salvaPeriodoMan(manutenzione);
            em.getTransaction().commit();
            System.out.println("Manutenzione registrata per il mezzo " + mezzo.getId());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    public void assegnaTrattaAMezzo(Mezzo mezzo, Tratta tratta, LocalDate dataInizio, LocalDate dataFine) {
        if (mezzo == null || tratta == null || dataInizio == null || dataFine == null || dataFine.isBefore(dataInizio)) {
            System.out.println("Dati non validi per il periodo di servizio.");
            return;
        }
        try {
            em.getTransaction().begin();
            PeriodoServizio servizio = new PeriodoServizio(dataInizio, dataFine, mezzo, tratta);
            periodoServizioDAO.salvaPeriodoMan(servizio);
            em.getTransaction().commit();
            System.out.println("Tratta " + tratta.getIdTratta() + " assegnata al mezzo " + mezzo.getId());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

}
