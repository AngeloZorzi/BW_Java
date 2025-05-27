package service;

import dao.*;
import entities.*;
import enumerating.TipoAbbonamento;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
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
    }


    public void registraUtente(String nome, String cognome, String email, String password) {
        Utente utente = new Utente(nome, cognome, email, password);
        utenteDAO.save(utente);
    }
    public Utente login(String email, String password) {
        return utenteDAO.trovaPerEmailPassword(email, password);
    }

    public Tessera creaTessera(LocalDate dataEmissione, Utente utente) {
        Tessera tessera = new Tessera(dataEmissione, utente);
        tesseraDAO.save(tessera);
        return tessera;
    }
    public void rinnovaTessera(Long idTessera) {
        tesseraDAO.rinnovaTessera(idTessera);
    }



    public Tessera getTesseraById(Long id) {
        return tesseraDAO.trovaPerId(id);
    }



    public void acquistaBiglietto(Tessera tessera, PuntoEmissione puntoEmissione) {
        Biglietto biglietto = new Biglietto(puntoEmissione, tessera);
        bigliettoDAO.save(biglietto);
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


    public void salvaMezzo(Mezzo mezzo) {
        mezzoDAO.salvaMezzo(mezzo);
    }

    public Mezzo getMezzoById(Long id) {
        return mezzoDAO.getId(id);
    }

    public Mezzo aggiornaMezzo(Mezzo mezzo) {
        return mezzoDAO.salvaMezzo(mezzo);
    }

    public void rimuoviMezzo(Long id) {
        mezzoDAO.rimuoviMezzo(id);
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
        trattaDAO.salva(tratta);
    }

    public Tratta getTrattaById(int id) {
        return trattaDAO.trovaPerId(id);
    }

    public List<Tratta> getAllTratte() {
        return trattaDAO.trovaTutti();
    }


    public void registraPuntoEmissione(PuntoEmissione punto) {
        puntoEmissioneDAO.save(punto);
    }

    public PuntoEmissione getPuntoEmissioneById(Long id) {
        return puntoEmissioneDAO.findById(id);
    }


}
