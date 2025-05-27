package dao;

import entities.Tessera;
import jakarta.persistence.EntityManager;

public class TesseraDAO {

    private final EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(Tessera tessera) {
        em.getTransaction().begin();
        em.persist(tessera);
        em.getTransaction().commit();
    }

    public Tessera trovaPerId(Long id) {
        return em.find(Tessera.class, id);
    }

    public void rinnovaTessera(Long idTessera) {
        Tessera tessera = trovaPerId(idTessera);
        if (tessera != null) {
            em.getTransaction().begin();
            tessera.aggiornaValidita();  // Questo metodo aggiorna la validità di un anno
            em.getTransaction().commit();
        }
    }

    public boolean isTesseraValida(Long idTessera) {
        Tessera tessera = trovaPerId(idTessera);
        return tessera != null && tessera.isValid();
    }
}