package dao;

import entities.Abbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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

    public boolean isAbbonamentoValidoPerNumeroTessera(Long idTessera) {
        TypedQuery<Abbonamento> query = em.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.tessera.id_tessera = :idTessera ORDER BY a.data_fine_validita DESC",
                Abbonamento.class
        );
        query.setParameter("idTessera", idTessera);
        query.setMaxResults(1);
        try {
            Abbonamento abbonamento = query.getSingleResult();
            return abbonamento.isValid();
        } catch (Exception e) {
            return false;
        }
    }
}
