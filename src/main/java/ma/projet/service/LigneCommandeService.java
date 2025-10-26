package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit, Integer> {
    
    private EntityManagerFactory emf;
    
    public LigneCommandeService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(LigneCommandeProduit o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(LigneCommandeProduit o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(LigneCommandeProduit o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public LigneCommandeProduit findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        LigneCommandeProduit ligne = em.find(LigneCommandeProduit.class, id);
        em.close();
        return ligne;
    }
    
    @Override
    public List<LigneCommandeProduit> findAll() {
        EntityManager em = emf.createEntityManager();
        List<LigneCommandeProduit> lignes = em.createQuery("SELECT l FROM LigneCommandeProduit l", LigneCommandeProduit.class).getResultList();
        em.close();
        return lignes;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

