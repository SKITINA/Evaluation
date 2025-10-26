package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class EmployeService implements IDao<Employe, Integer> {
    
    private EntityManagerFactory emf;
    
    public EmployeService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Employe o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Employe o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Employe o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Employe findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Employe employe = em.find(Employe.class, id);
        em.close();
        return employe;
    }
    
    @Override
    public List<Employe> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Employe> employes = em.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
        em.close();
        return employes;
    }
    
    public List<Tache> findTachesByEmploye(int employeId) {
        EntityManager em = emf.createEntityManager();
        List<Tache> taches = em.createQuery(
            "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :employeId", Tache.class)
            .setParameter("employeId", employeId)
            .getResultList();
        em.close();
        return taches;
    }
    
    public List<Projet> findProjetsByEmploye(int employeId) {
        EntityManager em = emf.createEntityManager();
        List<Projet> projets = em.createQuery(
            "SELECT p FROM Projet p WHERE p.chefDeProjet.id = :employeId", Projet.class)
            .setParameter("employeId", employeId)
            .getResultList();
        em.close();
        return projets;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

