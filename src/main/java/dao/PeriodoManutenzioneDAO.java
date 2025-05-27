package dao;

import entities.PeriodoManutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PeriodoManutenzioneDAO {


    private EntityManager em;
    public PeriodoManutenzioneDAO(EntityManager em) {
        this.em = em;
    }


    public PeriodoManutenzioneDAO() {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("gestionetrasporti");
        em = emf.createEntityManager();
    }

    public void salvaPeriodoMan(PeriodoManutenzione p){
        em.persist(p);
    }

    public PeriodoManutenzione getById(int id){
        return em.find(PeriodoManutenzione.class,id);
    }

    public void rimuoviPeriodo(int id) {
        PeriodoManutenzione m = getById(id);
        if (m != null) {
            em.remove(m); // transazione gestita dal Gestore
        } else {
            System.out.println("Periodo di manutenzione non trovato!");
        }
    }
    public long contaPeriodiManutenzione(Long idMezzo) {
        String jpql = "SELECT COUNT(pm) FROM PeriodoManutenzione pm WHERE pm.mezzo.id = :idMezzo";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("idMezzo", idMezzo)
                .getSingleResult();
        return count != null ? count : 0;
    }
}
