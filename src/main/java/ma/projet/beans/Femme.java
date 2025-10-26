package ma.projet.beans;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "femmes")
@NamedQuery(name = "Femme.marrieesAuMoinsDeuxFois", 
    query = "SELECT f FROM Femme f JOIN f.mariages m GROUP BY f.id HAVING COUNT(m.id) >= 2")
public class Femme extends Personne {
    
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;
    
    public Femme() {
        super();
    }
    
    public Femme(String nom, String prenom, String telephone, String adresse, java.util.Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
    
    public List<Mariage> getMariages() {
        return mariages;
    }
    
    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}

