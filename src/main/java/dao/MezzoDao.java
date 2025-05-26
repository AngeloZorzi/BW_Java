package dao;

import entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MezzoDao {
    private EntityManager em;


    public MezzoDao() {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("****");
        em = emf.createEntityManager();
    }

    public void salvaMezzo(Mezzo mezzo){
        em.getTransaction().begin();
        em.persist(mezzo);
        em.getTransaction().commit();
    }

    public Mezzo getById(int id){
        return em.find(Mezzo.class,id);
    }

    public void rimuoviMezzo(int id){
        Mezzo m = getById(id);
        if (m !=null){
            em.getTransaction().begin();
            em.remove(m);
            em.getTransaction().commit();
        }else {
            System.out.println("Mezzo non trovato!");
        }
    }

}
