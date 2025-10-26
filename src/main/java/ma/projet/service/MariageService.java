package ma.projet.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;

public class MariageService implements IDao<Mariage, Integer> {
    
    private EntityManagerFactory emf;
    
    public MariageService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Mariage o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Mariage o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Mariage o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Mariage findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Mariage mariage = em.find(Mariage.class, id);
        em.close();
        return mariage;
    }
    
    @Override
    public List<Mariage> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Mariage> mariages = em.createQuery("SELECT m FROM Mariage m", Mariage.class).getResultList();
        em.close();
        return mariages;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

