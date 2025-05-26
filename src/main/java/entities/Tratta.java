package entities;
import jakarta.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "tratta")
    private List<PercorrenzaTratta> percorrenzeTratta;


    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, Long tempoPrevistoPartenza) {
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
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public Long getTempoPrevistoPartenza() {
        return tempoPrevistoPartenza;
    }

    public void setTempoPrevistoPartenza(Long tempoPrevistoPartenza) {
        this.tempoPrevistoPartenza = tempoPrevistoPartenza;
    }

    public List<PercorrenzaTratta> getPercorrenzeTratta() {
        return percorrenzeTratta;
    }

    public void setPercorrenzeTratta(List<PercorrenzaTratta> percorrenzeTratta) {
        this.percorrenzeTratta = percorrenzeTratta;
    }


    @Override
    public String toString() {
        return "Tratta{" +
                "idTratta=" + idTratta +
                ", zonaPartenza='" + zonaPartenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoPrevistoPartenza=" + tempoPrevistoPartenza +
                '}';
    }
}
