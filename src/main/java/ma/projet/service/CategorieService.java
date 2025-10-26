package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class CategorieService implements IDao<Categorie, Integer> {
    
    private EntityManagerFactory emf;
    
    public CategorieService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Categorie o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Categorie o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Categorie o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Categorie findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Categorie categorie = em.find(Categorie.class, id);
        em.close();
        return categorie;
    }
    
    @Override
    public List<Categorie> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Categorie> categories = em.createQuery("SELECT c FROM Categorie c", Categorie.class).getResultList();
        em.close();
        return categories;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

