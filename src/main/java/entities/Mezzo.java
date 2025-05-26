package entities;

import enumerating.StatoMezzo;
import enumerating.TipoMezzo;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mezzi")
public class Mezzo {
    @Id
    @GeneratedValue
    @Column(name = "id_mezzo")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mezzo")
    private TipoMezzo tipoMezzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_mezzo")
    private StatoMezzo statoMezzo;

    private int capienza;

    @Column(name = "numero_identificativo")
    private int numeroIdentificativo;

    @OneToMany(mappedBy = "mezzo")
    private List<PeriodoManutenzione> periodoManutenzione;

    @OneToMany(mappedBy = "mezzo")
    private List<PeriodoServizio> periodoServizio;

    @OneToMany(mappedBy = "mezzo")
    private List<Biglietto> biglietti;

    @OneToMany(mappedBy = "mezzo")
    private List<PercorrenzaTratta> percorrenzaTratta;

    //Costruttori


    public Mezzo(TipoMezzo tipoMezzo, StatoMezzo statoMezzo, int numeroIdentificativo) {

        this.tipoMezzo = tipoMezzo;
        this.statoMezzo = statoMezzo;
        if (tipoMezzo == TipoMezzo.Autobus) {
            this.capienza = 50;
        } else {
            this.capienza = 350;
        }
        ;
        this.numeroIdentificativo = numeroIdentificativo;
    }

    public Mezzo() {
    }

    //Get e set


    public int getId() {
        return id;
    }

    public void setId(int idMezzo) {
        this.id = idMezzo;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public StatoMezzo getStatoMezzo() {
        return statoMezzo;
    }

    public void setStatoMezzo(StatoMezzo statoMezzo) {
        this.statoMezzo = statoMezzo;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public int getNumeroIdentificativo() {
        return numeroIdentificativo;
    }

    public void setNumeroIdentificativo(int numeroIdentificativo) {
        this.numeroIdentificativo = numeroIdentificativo;
    }

    public List<PeriodoManutenzione> getPeriodoManutenzione() {
        return periodoManutenzione;
    }

    public void setPeriodoManutenzione(List<PeriodoManutenzione> periodoManutenzione) {
        this.periodoManutenzione = periodoManutenzione;
    }

    public List<PeriodoServizio> getPeriodoServizio() {
        return periodoServizio;
    }

    public void setPeriodoServizio(List<PeriodoServizio> periodoServizio) {
        this.periodoServizio = periodoServizio;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    public List<PercorrenzaTratta> getPercorrenzaTratta() {
        return percorrenzaTratta;
    }

    public void setPercorrenzaTratta(List<PercorrenzaTratta> percorrenzaTratta) {
        this.percorrenzaTratta = percorrenzaTratta;
    }

    //ToString


    @Override
    public String toString() {
        return "Mezzo{" +
                "idMezzo=" + id +
                ", tipoMezzo=" + tipoMezzo +
                ", statoMezzo=" + statoMezzo +
                ", capienza=" + capienza +
                ", numeroIdentificativo=" + numeroIdentificativo +
                ", periodoManutenzione=" + periodoManutenzione +
                ", periodoServizio=" + periodoServizio +
                ", biglietti=" + biglietti +
                ", percorrenzaTratta=" + percorrenzaTratta +
                '}';
    }
}
