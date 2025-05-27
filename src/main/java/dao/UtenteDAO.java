package dao;

import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UtenteDAO {

    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente utente) {
        em.persist(utente);
    }

    public Utente trovaPerId(Long id) {
        return em.find(Utente.class, id);
    }

    public List<Utente> trovaTutti() {
        TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u", Utente.class);
        return query.getResultList();
    }
    public Utente trovaPerEmailPassword(String email, String password) {
        TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE u.email = :email AND u.password = :password", Utente.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        List<Utente> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}

