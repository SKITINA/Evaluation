package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit, Integer> {
    
    private EntityManagerFactory emf;
    
    public ProduitService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Produit o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Produit o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Produit o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Produit findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Produit produit = em.find(Produit.class, id);
        em.close();
        return produit;
    }
    
    @Override
    public List<Produit> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Produit> produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        em.close();
        return produits;
    }
    
    public List<Produit> findByIdCategorie(int categorieId) {
        EntityManager em = emf.createEntityManager();
        List<Produit> produits = em.createQuery(
            "SELECT p FROM Produit p WHERE p.categorie.id = :categorieId", Produit.class)
            .setParameter("categorieId", categorieId)
            .getResultList();
        em.close();
        return produits;
    }
    
    public List<Produit> findCommandesEntreDates(Date date1, Date date2) {
        EntityManager em = emf.createEntityManager();
        List<Produit> produits = em.createQuery(
            "SELECT DISTINCT p FROM Produit p JOIN p.lignesCommande lcp JOIN lcp.commande c " +
            "WHERE c.date BETWEEN :date1 AND :date2", Produit.class)
            .setParameter("date1", date1)
            .setParameter("date2", date2)
            .getResultList();
        em.close();
        return produits;
    }
    
    public List<Object[]> findProduitsParCommande(int commandeId) {
        EntityManager em = emf.createEntityManager();
        List<Object[]> resultats = em.createQuery(
            "SELECT p.reference, p.prix, lcp.quantite FROM LigneCommandeProduit lcp " +
            "JOIN lcp.produit p WHERE lcp.commande.id = :commandeId", Object[].class)
            .setParameter("commandeId", commandeId)
            .getResultList();
        em.close();
        return resultats;
    }
    
    public List<Produit> findByPrixGreaterThan(double prix) {
        EntityManager em = emf.createEntityManager();
        List<Produit> produits = em.createNamedQuery("Produit.findByPrixGreaterThan", Produit.class)
            .setParameter("prix", prix)
            .getResultList();
        em.close();
        return produits;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

