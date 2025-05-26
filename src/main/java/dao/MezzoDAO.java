package dao;

import entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MezzoDAO {
    private EntityManager em;


    public MezzoDAO() {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("****");
        em = emf.createEntityManager();
    }

    public void salvaMezzo(Mezzo mezzo){
        em.getTransaction().begin();
        em.persist(mezzo);
        em.getTransaction().commit();
    }

    public Mezzo getId(int id){
        return em.find(Mezzo.class,id);
    }

    public void rimuoviMezzo(int id){
        Mezzo m = getId(id);
        if (m !=null){
            em.getTransaction().begin();
            em.remove(m);
            em.getTransaction().commit();
        }else {
            System.out.println("Mezzo non trovato!");
        }
    }

    public long contaPeriodiManutenzione(int idMezzo) {
        return em.createQuery("SELECT COUNT(pm) FROM PeriodoManutenzione pm WHERE pm.mezzo.id = :id", Long.class)
                .setParameter("id", idMezzo)
                .getSingleResult();
    }

    public long contaPeriodiServizio(int idMezzo) {
        return em.createQuery("SELECT COUNT(ps) FROM PeriodoServizio ps WHERE ps.mezzo.id = :id", Long.class)
                .setParameter("id", idMezzo)
                .getSingleResult();
    }

    public long tempoTotaleServizio(int idMezzo) {
        return em.createQuery("SELECT SUM(DATEDIFF(ps.dataFine, ps.dataInizio)) FROM PeriodoServizio ps WHERE ps.mezzo.id = :id", Long.class)
                .setParameter("id", idMezzo)
                .getSingleResult();
    }
}
