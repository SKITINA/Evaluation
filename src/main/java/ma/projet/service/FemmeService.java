package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme, Integer> {
    
    private EntityManagerFactory emf;
    
    public FemmeService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Femme o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Femme o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Femme o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Femme findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Femme femme = em.find(Femme.class, id);
        em.close();
        return femme;
    }
    
    @Override
    public List<Femme> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Femme> femmes = em.createQuery("SELECT f FROM Femme f", Femme.class).getResultList();
        em.close();
        return femmes;
    }
    
    public long nbrEnfantsEntreDates(int femmeId, Date date1, Date date2) {
        EntityManager em = emf.createEntityManager();
        Long count = (Long) em.createNamedQuery("Mariage.nbrEnfantsEntreDates")
            .setParameter("femmeId", femmeId)
            .setParameter("date1", date1)
            .setParameter("date2", date2)
            .getSingleResult();
        em.close();
        return count != null ? count : 0;
    }
    
    public List<Femme> findMarrieesAuMoinsDeuxFois() {
        EntityManager em = emf.createEntityManager();
        List<Femme> femmes = em.createNamedQuery("Femme.marrieesAuMoinsDeuxFois", Femme.class).getResultList();
        em.close();
        return femmes;
    }
    
    public Femme findPlusAgee() {
        EntityManager em = emf.createEntityManager();
        List<Femme> femmes = em.createQuery(
            "SELECT f FROM Femme f ORDER BY f.dateNaissance ASC", Femme.class)
            .setMaxResults(1)
            .getResultList();
        em.close();
        return femmes.isEmpty() ? null : femmes.get(0);
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

