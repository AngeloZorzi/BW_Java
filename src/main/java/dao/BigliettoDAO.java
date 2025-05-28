package dao;

import entities.Biglietto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class BigliettoDAO {

    private final EntityManager em;

    public BigliettoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Biglietto b) {
        em.persist(b);
    }
    public void update(Biglietto biglietto) {
        em.merge(biglietto);
    }

    public List<Biglietto> findAll() {
        return em.createQuery("SELECT b FROM Biglietto b", Biglietto.class).getResultList();
    }

    public List<Biglietto> findByPeriodo(LocalDateTime start, LocalDateTime end) {
        TypedQuery<Biglietto> q = em.createQuery(
                "SELECT b FROM Biglietto b WHERE b.dataEmissione BETWEEN :start AND :end",
                Biglietto.class
        );
        q.setParameter("start", start);
        q.setParameter("end", end);
        return q.getResultList();
    }

    public long countByPuntoEmissione(Long puntoId, LocalDateTime start, LocalDateTime end) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b WHERE b.puntoEmissione.id = :pid AND b.dataEmissione BETWEEN :start AND :end",
                        Long.class
                )
                .setParameter("pid", puntoId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getSingleResult();
    }

    public List<Biglietto> findByTesseraId(Long tesseraId) {
        TypedQuery<Biglietto> query = em.createQuery(
                "SELECT b FROM Biglietto b WHERE b.tessera.id_tessera = :idTessera ORDER BY b.dataEmissione DESC",
                Biglietto.class
        );
        query.setParameter("idTessera", tesseraId);
        return query.getResultList();
    }

    public Biglietto getById(Long id) {
        return em.find(Biglietto.class, id);
    }
}
