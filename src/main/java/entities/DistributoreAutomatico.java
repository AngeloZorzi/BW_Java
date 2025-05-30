package entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DISTRIBUTORE")
public class DistributoreAutomatico extends PuntoEmissione {

    private boolean attivo;

    public DistributoreAutomatico() {}

    public DistributoreAutomatico(String nome, boolean attivo) {
        super(nome);
        this.attivo = attivo;
    }

    public void setAttivo(boolean stato) {
        this.attivo = stato;
    }

    public boolean isAttivo() {
        return attivo;
    }
}
