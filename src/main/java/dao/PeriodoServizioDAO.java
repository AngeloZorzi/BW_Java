package dao;

import entities.PeriodoServizio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PeriodoServizioDAO {


    private EntityManager em;
    public PeriodoServizioDAO(EntityManager em) {
        this.em = em;
    }


    public PeriodoServizioDAO() {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("gestionetrasporti");
        em = emf.createEntityManager();
    }

    public void salvaPeriodoMan(PeriodoServizio p){
        em.persist(p);
    }

    public PeriodoServizio getById(int id){
        return em.find(PeriodoServizio.class,id);
    }

    public void rimuoviMezzo(int id){
       PeriodoServizio m = getById(id);
        if (m !=null){
            em.remove(m);
        }else {
            System.out.println("Periodo di servizio non trovato!");
        }
    }
    public long contaPeriodiServizio(Long idMezzo) {
        String jpql = "SELECT COUNT(ps) FROM PeriodoServizio ps WHERE ps.mezzo.id = :idMezzo";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("idMezzo", idMezzo)
                .getSingleResult();
        return count != null ? count : 0;
    }
    public long tempoTotaleServizio(Long idMezzo) {

        String jpql = "SELECT SUM(ps.dataFine - ps.dataInizio) FROM PeriodoServizio ps WHERE ps.mezzo.id = :idMezzo";

        Long totalDays = em.createQuery(jpql, Long.class)
                .setParameter("idMezzo", idMezzo)
                .getSingleResult();

        return totalDays != null ? totalDays : 0;
    }


}
