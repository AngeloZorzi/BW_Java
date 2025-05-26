package entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RIVENDITORE")
public class RivenditoreAutorizzato extends PuntoEmissione {

    public RivenditoreAutorizzato() {}

    public RivenditoreAutorizzato(String nome) {
        super(nome);
    }
}
