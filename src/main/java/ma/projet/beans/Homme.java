package ma.projet.beans;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hommes")
public class Homme extends Personne {
    
    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;
    
    public Homme() {
        super();
    }
    
    public Homme(String nom, String prenom, String telephone, String adresse, java.util.Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
    
    public List<Mariage> getMariages() {
        return mariages;
    }
    
    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}

