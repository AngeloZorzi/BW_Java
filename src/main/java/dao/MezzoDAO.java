package dao;

import entities.Mezzo;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;

public class MezzoDAO {
    private EntityManager em;


    public MezzoDAO(EntityManager em) {
        this.em = em;
    }

    public Mezzo salvaMezzo(Mezzo mezzo) {
        em.persist(mezzo);
        return mezzo;
    }

    public Mezzo getId(Long id){
        return em.find(Mezzo.class,id);
    }

    public void rimuoviMezzo(Long id) {
        Mezzo m = getId(id);
        if (m != null) {
            em.remove(m);
        } else {
            System.out.println("Mezzo non trovato!");
        }
    }

    public long contaPeriodiManutenzione(Long idMezzo) {
        return em.createQuery("SELECT COUNT(pm) FROM PeriodoManutenzione pm WHERE pm.mezzo.id = :id", Long.class)
                .setParameter("id", idMezzo)
                .getSingleResult();
    }

    public long contaPeriodiServizio(Long idMezzo) {
        return em.createQuery("SELECT COUNT(ps) FROM PeriodoServizio ps WHERE ps.mezzo.id = :id", Long.class)
                .setParameter("id", idMezzo)
                .getSingleResult();
    }

    public long tempoTotaleServizio(Long idMezzo) {
        return em.createQuery("SELECT SUM(DATEDIFF(ps.dataFine, ps.dataInizio)) FROM PeriodoServizio ps WHERE ps.mezzo.id = :id", Long.class)
                .setParameter("id", idMezzo)
                .getSingleResult();
    }
    public long contaBigliettiVidimati(Long idMezzo, LocalDate inizio, LocalDate fine) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b WHERE b.mezzo.id = :id AND b.dataVidimazione BETWEEN :inizio AND :fine",
                        Long.class)
                .setParameter("id", idMezzo)
                .setParameter("inizio", inizio)
                .setParameter("fine", fine)
                .getSingleResult();
    }
    public long contaTrattePercorse(Long idMezzo) {
        return em.createQuery(
                        "SELECT COUNT(t) FROM TrattaPercorsa t WHERE t.mezzo.id = :id", Long.class)
                .setParameter("id", idMezzo)
                .getSingleResult();
    }

}
