package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "periodi_servizio")
public class PeriodoServizio {

    @Id
    @GeneratedValue
    @Column(name = "id_servizio")
private int idServizio;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
private Mezzo mezzo;


@Column(name = "data_inizio")
private LocalDate dataInizio;
@Column(name = "data_fine")
private LocalDate dataFine;


//Costruttori

    public PeriodoServizio( LocalDate dataInizio, LocalDate dataFine) {

        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public PeriodoServizio() {
    }
    //Get e Set


    public int getIdServizio() {
        return idServizio;
    }

    public void setIdServizio(int idServizio) {
        this.idServizio = idServizio;
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
        return "PeriodoServizio{" +
                "idServizio=" + idServizio +
                ", mezzo=" + mezzo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}
