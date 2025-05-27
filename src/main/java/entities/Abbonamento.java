package entities;

import enumerating.TipoAbbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codice_univoco;

    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipo;

    private LocalDate data_inizio_validita;
    private LocalDate data_fine_validita;

    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;

    @ManyToOne
    @JoinColumn(name = "id_punto_emissione")
    private PuntoEmissione puntoEmissione;


    // Getters, Setters
    public Abbonamento (){}

    public Abbonamento(Long codice_univoco, TipoAbbonamento tipo, LocalDate data_inizio_validita, LocalDate data_fine_validita, Tessera tessera, PuntoEmissione puntoEmissione) {
        this.codice_univoco = codice_univoco;
        this.tipo = tipo;
        this.data_inizio_validita = data_inizio_validita;
        this.data_fine_validita = data_fine_validita;
        this.tessera = tessera;
        this.puntoEmissione = puntoEmissione;
    }

    public Long getCodice_univoco() {
        return codice_univoco;
    }

    public void setCodice_univoco(Long codice_univoco) {
        this.codice_univoco = codice_univoco;
    }

    public TipoAbbonamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAbbonamento tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData_inizio_validita() {
        return data_inizio_validita;
    }

    public void setData_inizio_validita(LocalDate data_inizio_validita) {
        this.data_inizio_validita = data_inizio_validita;
    }

    public LocalDate getData_fine_validita() {
        return data_fine_validita;
    }

    public void setData_fine_validita(LocalDate data_fine_validita) {
        this.data_fine_validita = data_fine_validita;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public PuntoEmissione getPuntoEmissione() {
        return puntoEmissione;
    }

    public void setPuntoEmissione(PuntoEmissione puntoEmissione) {
        this.puntoEmissione = puntoEmissione;
    }

    public boolean isValid() {
        LocalDate oggi = LocalDate.now();
        return oggi.isAfter(data_inizio_validita.minusDays(1)) &&
                oggi.isBefore(data_fine_validita.plusDays(1));
    }
}
