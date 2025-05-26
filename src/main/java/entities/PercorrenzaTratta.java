package entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "percorrenze_tratta")
public class PercorrenzaTratta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_percorrenza")
    private int idPercorrenza;

    @ManyToOne
    @JoinColumn(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "id_tratta", nullable = false)
    private Tratta tratta;

    @Column(name = "data_ora_partenza", nullable = false)
    private LocalDateTime dataOraPartenza;

    @Column(name = "data_ora_arrivo", nullable = false)
    private LocalDateTime dataOraArrivo;

    @Column(name = "tempo_effettivo_percorso")
    private Long tempoEffettivoPercorso;


    public PercorrenzaTratta() {
    }

    public PercorrenzaTratta(Mezzo mezzo, Tratta tratta, LocalDateTime dataOraPartenza, LocalDateTime dataOraArrivo) {
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.dataOraPartenza = dataOraPartenza;
        this.dataOraArrivo = dataOraArrivo;
        if (dataOraPartenza != null && dataOraArrivo != null) {
            this.tempoEffettivoPercorso = java.time.Duration.between(dataOraPartenza, dataOraArrivo).toMinutes();
        }
    }
    public int getIdPercorrenza() {
        return idPercorrenza;
    }

    public void setIdPercorrenza(int idPercorrenza) {
        this.idPercorrenza = idPercorrenza;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public LocalDateTime getDataOraPartenza() {
        return dataOraPartenza;
    }

    public void setDataOraPartenza(LocalDateTime dataOraPartenza) {
        this.dataOraPartenza = dataOraPartenza;
    }

    public LocalDateTime getDataOraArrivo() {
        return dataOraArrivo;
    }

    public void setDataOraArrivo(LocalDateTime dataOraArrivo) {
        this.dataOraArrivo = dataOraArrivo;
    }

    public Long getTempoEffettivoPercorso() {
        return tempoEffettivoPercorso;
    }

    public void setTempoEffettivoPercorso(Long tempoEffettivoPercorso) {
        this.tempoEffettivoPercorso = tempoEffettivoPercorso;
    }

    @Override
    public String toString() {
        return "PercorrenzaTratta{" +
                "idPercorrenza=" + idPercorrenza +
                ", idMezzo=" + (mezzo != null ? mezzo.getId() : "null") +
                ", idTratta=" + (tratta != null ? tratta.getIdTratta() : "null") +
                ", dataOraPartenza=" + dataOraPartenza +
                ", dataOraArrivo=" + dataOraArrivo +
                ", tempoEffettivoPercorso=" + tempoEffettivoPercorso +
                '}';
    }
}
