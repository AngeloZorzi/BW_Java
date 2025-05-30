package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "periodi_manutenzione")
public class PeriodoManutenzione {
    @Id
    @GeneratedValue
    private int idManutenzione;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;



    //Costruttori


    public PeriodoManutenzione(LocalDate dataInizio, LocalDate dataFine, Mezzo mezzo) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.mezzo = mezzo;
    }

    public PeriodoManutenzione() {
    }

    //Get e set


    public int getIdManutenzione() {
        return idManutenzione;
    }

    public void setIdManutenzione(int idManutenzione) {
        this.idManutenzione = idManutenzione;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }
    //ToString


    @Override
    public String toString() {
        return "PeriodoManutenzione{" +
                "idManutenzione=" + idManutenzione +
                ", mezzo=" + mezzo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}
