package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class CommandeService implements IDao<Commande, Integer> {
    
    private EntityManagerFactory emf;
    
    public CommandeService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Commande o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Commande o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Commande o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Commande findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Commande commande = em.find(Commande.class, id);
        em.close();
        return commande;
    }
    
    @Override
    public List<Commande> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Commande> commandes = em.createQuery("SELECT c FROM Commande c", Commande.class).getResultList();
        em.close();
        return commandes;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

