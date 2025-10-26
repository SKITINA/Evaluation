package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache, Integer> {
    
    private EntityManagerFactory emf;
    
    public TacheService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Tache o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Tache o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Tache o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Tache findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Tache tache = em.find(Tache.class, id);
        em.close();
        return tache;
    }
    
    @Override
    public List<Tache> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Tache> taches = em.createQuery("SELECT t FROM Tache t", Tache.class).getResultList();
        em.close();
        return taches;
    }
    
    public List<Tache> findByPrixGreaterThan(double prix) {
        EntityManager em = emf.createEntityManager();
        List<Tache> taches = em.createNamedQuery("Tache.findByPrixGreaterThan", Tache.class)
            .setParameter("prix", prix)
            .getResultList();
        em.close();
        return taches;
    }
    
    public List<EmployeTache> findRealiseesEntreDates(Date date1, Date date2) {
        EntityManager em = emf.createEntityManager();
        List<EmployeTache> employeTaches = em.createQuery(
            "SELECT et FROM EmployeTache et WHERE et.dateDebutReelle >= :date1 AND et.dateFinReelle <= :date2", 
            EmployeTache.class)
            .setParameter("date1", date1)
            .setParameter("date2", date2)
            .getResultList();
        em.close();
        return employeTaches;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

