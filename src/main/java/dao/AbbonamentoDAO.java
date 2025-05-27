package dao;

import entities.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import entities.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class AbbonamentoDAO {

    private final EntityManager em;

    public AbbonamentoDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(Abbonamento abbonamento) {
        em.getTransaction().begin();
        em.persist(abbonamento);
        em.getTransaction().commit();
    }

    public Abbonamento trovaPerId(Long id) {
        return em.find(Abbonamento.class, id);
    }

    public boolean isAbbonamentoValidoPerTessera(Long idTessera) {
        TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.tessera.id_tessera = :idTessera ORDER BY a.data_fine_validita DESC",
                Abbonamento.class
        );
        query.setParameter("idTessera", idTessera);
        query.setMaxResults(1);

        try {
            Abbonamento ultimo = query.getSingleResult();
            return ultimo.isValid();
        } catch (Exception e) {
            return false;
        }
    }

    public long countAbbonamentiEmessiTra(LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a) FROM Abbonamento a WHERE a.data_inizio_validita BETWEEN :inizio AND :fine",
                Long.class
        );
        query.setParameter("inizio", dataInizio);
        query.setParameter("fine", dataFine);
        return query.getSingleResult();
    }

    public long countAbbonamentiEmessiDaPuntoTra(Long idPunto, LocalDate dataInizio, LocalDate dataFine) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a) FROM Abbonamento a WHERE a.puntoEmissione.id = :idPunto AND a.data_inizio_validita BETWEEN :inizio AND :fine",
                Long.class
        );
        query.setParameter("idPunto", idPunto);
        query.setParameter("inizio", dataInizio);
        query.setParameter("fine", dataFine);
        return query.getSingleResult();
    }

    public List<Abbonamento> trovaAbbonamentiPerTessera(Long idTessera) {
        TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.tessera.id_tessera = :idTessera ORDER BY a.data_inizio_validita DESC",
                Abbonamento.class
        );
        query.setParameter("idTessera", idTessera);
        return query.getResultList();
    }
}