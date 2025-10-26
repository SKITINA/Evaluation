package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme, Integer> {
    
    private EntityManagerFactory emf;
    
    public HommeService() {
        emf = Persistence.createEntityManagerFactory("evaluation");
    }
    
    @Override
    public boolean create(Homme o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean update(Homme o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public boolean delete(Homme o) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
        em.close();
        return true;
    }
    
    @Override
    public Homme findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Homme homme = em.find(Homme.class, id);
        em.close();
        return homme;
    }
    
    @Override
    public List<Homme> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Homme> hommes = em.createQuery("SELECT h FROM Homme h", Homme.class).getResultList();
        em.close();
        return hommes;
    }
    
    public List<Mariage> findEpousesByHommeEntreDates(int hommeId, Date date1, Date date2) {
        EntityManager em = emf.createEntityManager();
        List<Mariage> mariages = em.createQuery(
            "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId " +
            "AND m.dateDebut >= :date1 AND m.dateDebut <= :date2", Mariage.class)
            .setParameter("hommeId", hommeId)
            .setParameter("date1", date1)
            .setParameter("date2", date2)
            .getResultList();
        em.close();
        return mariages;
    }
    
    public List<Homme> findHommesMarieesQuatreFemmesEntreDates(Date date1, Date date2) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Homme> cq = cb.createQuery(Homme.class);
        Root<Mariage> m = cq.from(Mariage.class);
        
        Predicate datePredicate = cb.and(
            cb.greaterThanOrEqualTo(m.get("dateDebut"), date1),
            cb.lessThanOrEqualTo(m.get("dateDebut"), date2)
        );
        
        cq.select(m.get("homme"))
          .where(datePredicate)
          .groupBy(m.get("homme"))
          .having(cb.greaterThanOrEqualTo(cb.count(m), 4L));
        
        List<Homme> hommes = em.createQuery(cq).getResultList();
        em.close();
        return hommes;
    }
    
    public List<Mariage> findMariagesByHomme(int hommeId) {
        EntityManager em = emf.createEntityManager();
        List<Mariage> mariages = em.createQuery(
            "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId", Mariage.class)
            .setParameter("hommeId", hommeId)
            .getResultList();
        em.close();
        return mariages;
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}

