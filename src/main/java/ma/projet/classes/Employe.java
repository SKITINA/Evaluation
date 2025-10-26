package ma.projet.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employe")
public class Employe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length = 50)
    private String nom;
    
    @Column(length = 50)
    private String prenom;
    
    @Column(length = 20)
    private String telephone;
    
    @OneToMany(mappedBy = "chefDeProjet")
    private List<Projet> projetsGeres;
    
    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL)
    private List<EmployeTache> taches;
    
    public Employe() {
    }
    
    public Employe(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public List<Projet> getProjetsGeres() {
        return projetsGeres;
    }
    
    public void setProjetsGeres(List<Projet> projetsGeres) {
        this.projetsGeres = projetsGeres;
    }
    
    public List<EmployeTache> getTaches() {
        return taches;
    }
    
    public void setTaches(List<EmployeTache> taches) {
        this.taches = taches;
    }
}

