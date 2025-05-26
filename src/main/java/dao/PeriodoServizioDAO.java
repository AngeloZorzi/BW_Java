package dao;

import entities.PeriodoServizio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PeriodoServizioDAO {


    private EntityManager em;


    public PeriodoServizioDAO() {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("****");
        em = emf.createEntityManager();
    }

    public void salvaPeriodoMan(PeriodoServizio p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public PeriodoServizio getById(int id){
        return em.find(PeriodoServizio.class,id);
    }

    public void rimuoviMezzo(int id){
       PeriodoServizio m = getById(id);
        if (m !=null){
            em.getTransaction().begin();
            em.remove(m);
            em.getTransaction().commit();
        }else {
            System.out.println("Periodo di servizio non trovato!");
        }
    }

}
