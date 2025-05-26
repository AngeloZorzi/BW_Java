package dao;

import entities.PeriodoManutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PeriodoManutenzioneDAO {


    private EntityManager em;


    public PeriodoManutenzioneDAO() {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("****");
        em = emf.createEntityManager();
    }

    public void salvaPeriodoMan(PeriodoManutenzione p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public PeriodoManutenzione getById(int id){
        return em.find(PeriodoManutenzione.class,id);
    }

    public void rimuoviMezzo(int id){
        PeriodoManutenzione m = getById(id);
        if (m !=null){
            em.getTransaction().begin();
            em.remove(m);
            em.getTransaction().commit();
        }else {
            System.out.println("Periodo di manutenzione non trovato!");
        }
    }
}
