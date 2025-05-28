package entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tratte")
public class Tratta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratta")
    private int idTratta;

    @Column(name = "zona_partenza", nullable = false)
    private String zonaPartenza;

    @Column(name = "capolinea", nullable = false)
    private String capolinea;

    @Column(name = "tempo_previsto_partenza")
    private Long tempoPrevistoPartenza;

    @OneToMany(mappedBy = "tratta", fetch = FetchType.LAZY)
    private List<PercorrenzaTratta> percorrenzeTratta;

    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, Long tempoPrevistoPartenza) {
        if (zonaPartenza == null || zonaPartenza.trim().isEmpty()) {
            throw new IllegalArgumentException("Zona partenza cannot be null or empty.");
        }
        if (capolinea == null || capolinea.trim().isEmpty()) {
            throw new IllegalArgumentException("Capolinea cannot be null or empty.");
        }
        if (tempoPrevistoPartenza == null || tempoPrevistoPartenza <= 0) {
            throw new IllegalArgumentException("Tempo previsto partenza must be a positive value.");
        }

        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevistoPartenza = tempoPrevistoPartenza;
    }


    public int getIdTratta() {
        return idTratta;
    }

    public void setIdTratta(int idTratta) {
        this.idTratta = idTratta;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        if (zonaPartenza == null || zonaPartenza.trim().isEmpty()) {
            throw new IllegalArgumentException("Zona partenza cannot be null or empty.");
        }
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        if (capolinea == null || capolinea.trim().isEmpty()) {
            throw new IllegalArgumentException("Capolinea cannot be null or empty.");
        }
        this.capolinea = capolinea;
    }

    public Long getTempoPrevistoPartenza() {
        return tempoPrevistoPartenza;
    }

    public void setTempoPrevistoPartenza(Long tempoPrevistoPartenza) {
        if (tempoPrevistoPartenza == null || tempoPrevistoPartenza <= 0) {
            throw new IllegalArgumentException("Tempo previsto partenza must be a positive value.");
        }
        this.tempoPrevistoPartenza = tempoPrevistoPartenza;
    }

    public List<PercorrenzaTratta> getPercorrenzeTratta() {
        return percorrenzeTratta;
    }

    public void setPercorrenzeTratta(List percorrenzeTratta) {
        this.percorrenzeTratta = percorrenzeTratta;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "idTratta=" + idTratta +
                ", zonaPartenza='" + (zonaPartenza != null ? zonaPartenza : "N/A") + '\'' +
                ", capolinea='" + (capolinea != null ? capolinea : "N/A") + '\'' +
                ", tempoPrevistoPartenza=" + (tempoPrevistoPartenza != null ? tempoPrevistoPartenza : "N/A") +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tratta tratta = (Tratta) o;
        return idTratta == tratta.idTratta;
    }


    @Override
    public int hashCode() {
        return Objects.hash(idTratta);
    }
}