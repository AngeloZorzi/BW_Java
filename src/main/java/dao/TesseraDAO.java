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
}