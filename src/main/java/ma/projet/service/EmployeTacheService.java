package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache, Integer> {
    
    private EntityManagerFactory emf;
    
    public EmployeTacheService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(EmployeTache o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(EmployeTache o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(EmployeTache o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public EmployeTache findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        EmployeTache employeTache = em.find(EmployeTache.class, id);
        em.close();
        return employeTache;
    }
    
    @Override
    public List<EmployeTache> findAll() {
        EntityManager em = emf.createEntityManager();
        List<EmployeTache> employeTaches = em.createQuery("SELECT et FROM EmployeTache et", EmployeTache.class).getResultList();
        em.close();
        return employeTaches;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

