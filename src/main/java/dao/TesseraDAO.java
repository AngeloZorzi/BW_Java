package dao;

import entities.Tessera;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TesseraDAO {

    private final EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tessera tessera) {
        em.persist(tessera);
    }

    public List<Tessera> findByUtenteId(Long utenteId) {
        return em.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :utenteId", Tessera.class)
                .setParameter("utenteId", utenteId)
                .getResultList();
    }


    public Tessera trovaPerId(Long id) {
        return em.find(Tessera.class, id);
    }

    public void rinnovaTessera(Long idTessera) {
        Tessera tessera = trovaPerId(idTessera);
        if (tessera != null) {
            tessera.aggiornaValidita();
            em.merge(tessera);
        }
    }

    public boolean isTesseraValida(Long idTessera) {
        Tessera tessera = trovaPerId(idTessera);
        return tessera != null && tessera.isValid();
    }
}