package dao;

import entities.PercorrenzaTratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PercorrenzaTrattaDAO {
    private final EntityManager em;

    public PercorrenzaTrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(PercorrenzaTratta percorrenzaTratta) {
        em.persist(percorrenzaTratta);
    }

    public PercorrenzaTratta trovaPerId(Integer id) {
        return em.find(PercorrenzaTratta.class, id);
    }

    public List<PercorrenzaTratta> trovaTutti() {
        TypedQuery<PercorrenzaTratta> query = em.createQuery("SELECT pt FROM PercorrenzaTratta pt", PercorrenzaTratta.class);
        return query.getResultList();
    }

    public PercorrenzaTratta aggiorna(PercorrenzaTratta percorrenzaTratta) {
        return em.merge(percorrenzaTratta);
    }

    public void elimina(PercorrenzaTratta percorrenzaTratta) {
        em.remove(em.contains(percorrenzaTratta) ? percorrenzaTratta : em.merge(percorrenzaTratta));
    }
}
