package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class ProjetService implements IDao<Projet, Integer> {
    
    private EntityManagerFactory emf;
    
    public ProjetService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Projet o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Projet o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Projet o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Projet findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Projet projet = em.find(Projet.class, id);
        em.close();
        return projet;
    }
    
    @Override
    public List<Projet> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Projet> projets = em.createQuery("SELECT p FROM Projet p", Projet.class).getResultList();
        em.close();
        return projets;
    }
    
    public List<Tache> findTachesByProjet(int projetId) {
        EntityManager em = emf.createEntityManager();
        List<Tache> taches = em.createQuery(
            "SELECT t FROM Tache t WHERE t.projet.id = :projetId", Tache.class)
            .setParameter("projetId", projetId)
            .getResultList();
        em.close();
        return taches;
    }
    
    public List<EmployeTache> findTachesRealiseesByProjet(int projetId) {
        EntityManager em = emf.createEntityManager();
        List<EmployeTache> employeTaches = em.createQuery(
            "SELECT et FROM EmployeTache et WHERE et.tache.projet.id = :projetId", EmployeTache.class)
            .setParameter("projetId", projetId)
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

