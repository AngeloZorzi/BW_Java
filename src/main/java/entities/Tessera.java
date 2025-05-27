package entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Tessera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tessera;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    private LocalDate data_emissione;
    private LocalDate data_scadenza;
    private boolean attiva;

    @OneToMany(mappedBy = "tessera")
    private List<Abbonamento> abbonamenti;

    public boolean isValid() {
        return attiva && LocalDate.now().isBefore(data_scadenza);
    }

    public void aggiornaValidita() {
        this.data_emissione = LocalDate.now();
        this.data_scadenza = this.data_emissione.plusYears(1);
    }

    public void rinnova() {
        this.data_emissione = LocalDate.now();
        this.data_scadenza = data_emissione.plusYears(1);
        this.attiva = true;
    }

    // Getters & Setters
    public Tessera (){}

    public Tessera(List<Abbonamento> abbonamenti, boolean attiva, LocalDate data_scadenza, LocalDate data_emissione, Utente utente, Long id_tessera) {
        this.abbonamenti = abbonamenti;
        this.attiva = attiva;
        this.data_scadenza = data_scadenza;
        this.data_emissione = data_emissione;
        this.utente = utente;
        this.id_tessera = id_tessera;
    }

    public Long getId_tessera() {
        return id_tessera;
    }

    public void setId_tessera(Long id_tessera) {
        this.id_tessera = id_tessera;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public LocalDate getData_emissione() {
        return data_emissione;
    }

    public void setData_emissione(LocalDate data_emissione) {
        this.data_emissione = data_emissione;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDate data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public boolean isAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }
}
