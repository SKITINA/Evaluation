package ma.projet.classes;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommandeProduit> lignesCommande;
    
    public Commande() {
    }
    
    public Commande(Date date) {
        this.date = date;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public List<LigneCommandeProduit> getLignesCommande() {
        return lignesCommande;
    }
    
    public void setLignesCommande(List<LigneCommandeProduit> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }
}

