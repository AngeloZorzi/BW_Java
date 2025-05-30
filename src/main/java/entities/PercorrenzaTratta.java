package entities;
import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "percorrenze_tratta")
public class PercorrenzaTratta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_percorrenza")
    private int idPercorrenza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @ManyToOne(fetch = FetchType.LAZY)
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

        if (mezzo == null) {
            throw new IllegalArgumentException("Mezzo cannot be null for PercorrenzaTratta.");
        }
        if (tratta == null) {
            throw new IllegalArgumentException("Tratta cannot be null for PercorrenzaTratta.");
        }

        // Validate date/time inputs
        if (dataOraPartenza == null) {
            throw new IllegalArgumentException("Data ora partenza cannot be null.");
        }
        if (dataOraArrivo == null) {
            throw new IllegalArgumentException("Data ora arrivo cannot be null.");
        }
        if (dataOraArrivo.isBefore(dataOraPartenza)) {
            throw new IllegalArgumentException("Data ora arrivo cannot be before Data ora partenza.");
        }

        this.mezzo = mezzo;
        this.tratta = tratta;
        this.dataOraPartenza = dataOraPartenza;
        this.dataOraArrivo = dataOraArrivo;
        this.tempoEffettivoPercorso = Duration.between(dataOraPartenza, dataOraArrivo).toMinutes();
    }



    public int getIdPercorrenza() {
        return idPercorrenza;
    }

    public void setIdPercorrenza(int idPercorrenza) {
        // Optional: add validation if idPercorrenza must be non-negative
        this.idPercorrenza = idPercorrenza;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        if (mezzo == null) {
            throw new IllegalArgumentException("Mezzo cannot be null.");
        }
        this.mezzo = mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        if (tratta == null) {
            throw new IllegalArgumentException("Tratta cannot be null.");
        }
        this.tratta = tratta;
    }

    public LocalDateTime getDataOraPartenza() {
        return dataOraPartenza;
    }

    public void setDataOraPartenza(LocalDateTime dataOraPartenza) {
        if (dataOraPartenza == null) {
            throw new IllegalArgumentException("Data ora partenza cannot be null.");
        }

        if (this.dataOraArrivo != null && dataOraPartenza.isAfter(this.dataOraArrivo)) {
            throw new IllegalArgumentException("Data ora partenza cannot be after Data ora arrivo.");
        }
        this.dataOraPartenza = dataOraPartenza;
        if (this.dataOraArrivo != null) {
            this.tempoEffettivoPercorso = Duration.between(this.dataOraPartenza, this.dataOraArrivo).toMinutes();
        }
    }

    public LocalDateTime getDataOraArrivo() {
        return dataOraArrivo;
    }

    public void setDataOraArrivo(LocalDateTime dataOraArrivo) {
        if (dataOraArrivo == null) {
            throw new IllegalArgumentException("Data ora arrivo cannot be null.");
        }
        if (this.dataOraPartenza != null && dataOraArrivo.isBefore(this.dataOraPartenza)) {
            throw new IllegalArgumentException("Data ora arrivo cannot be before Data ora partenza.");
        }
        this.dataOraArrivo = dataOraArrivo;
        if (this.dataOraPartenza != null) {
            this.tempoEffettivoPercorso = Duration.between(this.dataOraPartenza, this.dataOraArrivo).toMinutes();
        }
    }

    public Long getTempoEffettivoPercorso() {
        return tempoEffettivoPercorso;
    }

    public void setTempoEffettivoPercorso(Long tempoEffettivoPercorso) {

        this.tempoEffettivoPercorso = tempoEffettivoPercorso;
    }


    @Override
    public String toString() {

        long mezzoId = (mezzo != null) ? mezzo.getId() : -1L;
        int trattaId = (tratta != null) ? tratta.getIdTratta() : -1;

        return "PercorrenzaTratta{" +
                "idPercorrenza=" + idPercorrenza +
                ", mezzoId=" + mezzoId +
                ", trattaId=" + trattaId +
                ", dataOraPartenza=" + dataOraPartenza +
                ", dataOraArrivo=" + dataOraArrivo +
                ", tempoEffettivoPercorso=" + tempoEffettivoPercorso +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercorrenzaTratta that = (PercorrenzaTratta) o;
        return idPercorrenza == that.idPercorrenza;
    }


    @Override
    public int hashCode() {
        return Objects.hash(idPercorrenza);
    }
}