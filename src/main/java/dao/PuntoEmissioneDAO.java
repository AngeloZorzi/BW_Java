package dao;

import entities.PuntoEmissione;
import jakarta.persistence.EntityManager;

public class PuntoEmissioneDAO {

    private EntityManager em;

    public PuntoEmissioneDAO(EntityManager em) {
        this.em = em;
    }

    public void save(PuntoEmissione p) {
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public PuntoEmissione findById(Long id) {
        return em.find(PuntoEmissione.class, id);
    }
}
