package dao;

import entities.PuntoEmissione;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PuntoEmissioneDAO {

    private EntityManager em;

    public PuntoEmissioneDAO(EntityManager em) {
        this.em = em;
    }

    public void save(PuntoEmissione p) {
        em.persist(p);
    }

    public PuntoEmissione findById(Long id) {
        return em.find(PuntoEmissione.class, id);
    }
    public List<PuntoEmissione> findAll() {
        return em.createQuery("SELECT p FROM PuntoEmissione p", PuntoEmissione.class).getResultList();
    }
}
