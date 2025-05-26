package dao;
import entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class TrattaDAO {
    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }
    public void salva(Tratta tratta) {
        em.getTransaction().begin();
        try {
            em.persist(tratta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }


    public Tratta trovaPerId(Integer id) {
        return em.find(Tratta.class, id);
    }


    public List<Tratta> trovaTutti() {
        TypedQuery<Tratta> query = em.createQuery("SELECT t FROM Tratta t", Tratta.class);
        return query.getResultList();
    }

    public Tratta aggiorna(Tratta tratta) {
        em.getTransaction().begin();
        Tratta mergedTratta = null;
        try {
            mergedTratta = em.merge(tratta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
        return mergedTratta;
    }


    public void elimina(Tratta tratta) {
        em.getTransaction().begin();
        try {
            em.remove(em.contains(tratta) ? tratta : em.merge(tratta));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
