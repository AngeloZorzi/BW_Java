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
        em.getTransaction().begin();
        try {
            em.persist(percorrenzaTratta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public PercorrenzaTratta trovaPerId(Integer id) {
        return em.find(PercorrenzaTratta.class, id);
    }

    public List<PercorrenzaTratta> trovaTutti() {
        TypedQuery<PercorrenzaTratta> query = em.createQuery("SELECT pt FROM PercorrenzaTratta pt", PercorrenzaTratta.class);
        return query.getResultList();
    }



    public PercorrenzaTratta aggiorna(PercorrenzaTratta percorrenzaTratta) {
        em.getTransaction().begin();
        PercorrenzaTratta mergedPercorrenza = null;
        try {
            mergedPercorrenza = em.merge(percorrenzaTratta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
        return mergedPercorrenza;
    }


    public void elimina(PercorrenzaTratta percorrenzaTratta) {
        em.getTransaction().begin();
        try {
            em.remove(em.contains(percorrenzaTratta) ? percorrenzaTratta : em.merge(percorrenzaTratta));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
