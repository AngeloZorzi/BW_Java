package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "biglietti")
public class Biglietto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codiceUnivoco;

    private boolean vidimato;

    @Column(name = "data_emissione", nullable = false)
    private LocalDateTime dataEmissione;

    @Column(name = "data_vidimazione")
    private LocalDateTime dataVidimazione;

    @ManyToOne
    @JoinColumn(name = "punto_emissione_id")
    private PuntoEmissione puntoEmissione;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera;

    public Biglietto() {
        this.codiceUnivoco = UUID.randomUUID().toString();
        this.vidimato = false;
        this.dataEmissione = LocalDateTime.now();
    }

    public Biglietto(PuntoEmissione puntoEmissione, Tessera tessera) {
        this();
        this.puntoEmissione = puntoEmissione;
        this.tessera = tessera;
    }

    public void valida(Mezzo mezzo) {
        this.vidimato = true;
        this.dataVidimazione = LocalDateTime.now();
        this.mezzo = mezzo;
    }

    // Getters e setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodiceUnivoco() { return codiceUnivoco; }
    public void setCodiceUnivoco(String codiceUnivoco) { this.codiceUnivoco = codiceUnivoco; }

    public boolean isVidimato() { return vidimato; }
    public void setVidimato(boolean vidimato) { this.vidimato = vidimato; }

    public LocalDateTime getDataEmissione() { return dataEmissione; }
    public void setDataEmissione(LocalDateTime dataEmissione) { this.dataEmissione = dataEmissione; }

    public LocalDateTime getDataVidimazione() { return dataVidimazione; }
    public void setDataVidimazione(LocalDateTime dataVidimazione) { this.dataVidimazione = dataVidimazione; }

    public PuntoEmissione getPuntoEmissione() { return puntoEmissione; }
    public void setPuntoEmissione(PuntoEmissione puntoEmissione) { this.puntoEmissione = puntoEmissione; }

    public Mezzo getMezzo() { return mezzo; }
    public void setMezzo(Mezzo mezzo) { this.mezzo = mezzo; }

    public Tessera getTessera() { return tessera; }
    public void setTessera(Tessera tessera) { this.tessera = tessera; }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", codiceUnivoco='" + codiceUnivoco + '\'' +
                ", vidimato=" + vidimato +
                ", dataEmissione=" + dataEmissione +
                ", dataVidimazione=" + dataVidimazione +
                ", puntoEmissione=" + (puntoEmissione != null ? puntoEmissione.getId() : null) +
                ", mezzo=" + (mezzo != null ? mezzo.getId() : null) +
                ", tessera=" + (tessera != null ? tessera.getId_tessera() : null) +
                '}';
    }
}
