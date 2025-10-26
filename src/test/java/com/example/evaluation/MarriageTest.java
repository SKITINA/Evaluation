package com.example.evaluation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

public class MarriageTest {
    
    public static void main(String[] args) {
        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            // Create 5 men
            Homme h1 = new Homme("SAFI", "SAID", "0612345670", "Casablanca", sdf.parse("1960-01-15"));
            Homme h2 = new Homme("ALAOUI", "MOHAMED", "0612345671", "Rabat", sdf.parse("1965-03-20"));
            Homme h3 = new Homme("BENALI", "HASSAN", "0612345672", "Fes", sdf.parse("1970-05-10"));
            Homme h4 = new Homme("TALBI", "OMAR", "0612345673", "Marrakech", sdf.parse("1975-07-05"));
            Homme h5 = new Homme("CHAFI", "KARIM", "0612345674", "Tangier", sdf.parse("1980-09-30"));
            
            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);
            
            // Create 10 women
            Femme f1 = new Femme("SALIMA", "RAMI", "0622345670", "Casablanca", sdf.parse("1965-02-10"));
            Femme f2 = new Femme("AMAL", "ALI", "0622345671", "Rabat", sdf.parse("1968-04-15"));
            Femme f3 = new Femme("WAFA", "ALAOUI", "0622345672", "Casablanca", sdf.parse("1972-06-20"));
            Femme f4 = new Femme("KARIMA", "ALAMI", "0622345673", "Fes", sdf.parse("1963-03-12"));
            Femme f5 = new Femme("FATIMA", "BENALI", "0622345674", "Rabat", sdf.parse("1970-08-25"));
            Femme f6 = new Femme("AICHA", "CHAFI", "0622345675", "Marrakech", sdf.parse("1967-11-03"));
            Femme f7 = new Femme("SOUAD", "TALBI", "0622345676", "Casablanca", sdf.parse("1973-01-18"));
            Femme f8 = new Femme("NADIA", "SAFI", "0622345677", "Fes", sdf.parse("1969-07-22"));
            Femme f9 = new Femme("LATIFA", "ALAMI", "0622345678", "Rabat", sdf.parse("1971-09-14"));
            Femme f10 = new Femme("AMINA", "BENALI", "0622345679", "Casablanca", sdf.parse("1974-12-05"));
            
            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);
            
            // Create marriages for h1
            Mariage m1 = new Mariage(sdf.parse("1989-09-03"), sdf.parse("1990-09-03"), 0, h1, f4);
            Mariage m2 = new Mariage(sdf.parse("1990-09-03"), null, 4, h1, f1);
            Mariage m3 = new Mariage(sdf.parse("1995-09-03"), null, 2, h1, f2);
            Mariage m4 = new Mariage(sdf.parse("2000-11-04"), null, 3, h1, f3);
            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            
            // Create marriages for h2 (4 marriages)
            Mariage m5 = new Mariage(sdf.parse("1990-01-10"), null, 2, h2, f5);
            Mariage m6 = new Mariage(sdf.parse("1995-01-10"), null, 3, h2, f6);
            Mariage m7 = new Mariage(sdf.parse("2000-01-10"), null, 1, h2, f7);
            Mariage m8 = new Mariage(sdf.parse("2005-01-10"), null, 2, h2, f8);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);
            mariageService.create(m8);
            
            // Create marriages for h3 (2 marriages)
            Mariage m9 = new Mariage(sdf.parse("1995-05-15"), null, 3, h3, f9);
            Mariage m10 = new Mariage(sdf.parse("2000-05-15"), null, 1, h3, f10);
            mariageService.create(m9);
            mariageService.create(m10);
            
            // Create marriages for f6 (married twice - with h2 already)
            Mariage m11 = new Mariage(sdf.parse("2008-06-20"), null, 2, h4, f6);
            mariageService.create(m11);
            
            // Display test results
            System.out.println("=== List of all women ===");
            List<Femme> femmes = femmeService.findAll();
            for (Femme femme : femmes) {
                System.out.println(femme.getNom() + " " + femme.getPrenom() + " - Born: " +
                    new SimpleDateFormat("dd MMMM yyyy").format(femme.getDateNaissance()));
            }
            
            System.out.println("\n=== Oldest woman ===");
            Femme plusAgee = femmeService.findPlusAgee();
            if (plusAgee != null) {
                System.out.println("Name: " + plusAgee.getNom() + " " + plusAgee.getPrenom() +
                    " - Born: " + new SimpleDateFormat("dd MMMM yyyy").format(plusAgee.getDateNaissance()));
            }
            
            System.out.println("\n=== Wives of a man between dates ===");
            Date date1 = sdf.parse("1990-01-01");
            Date date2 = sdf.parse("2005-12-31");
            List<Mariage> epouses = hommeService.findEpousesByHommeEntreDates(h1.getId(), date1, date2);
            for (Mariage m : epouses) {
                System.out.println("Wife: " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                    " - Marriage date: " + new SimpleDateFormat("dd/MM/yyyy").format(m.getDateDebut()) +
                    " - Children: " + m.getNbrEnfant());
            }
            
            System.out.println("\n=== Number of children for a woman between dates ===");
            long nbrEnfants = femmeService.nbrEnfantsEntreDates(f1.getId(), date1, date2);
            System.out.println("Number of children: " + nbrEnfants);
            
            System.out.println("\n=== Women married at least twice ===");
            List<Femme> femmesMarieesDeuxFois = femmeService.findMarrieesAuMoinsDeuxFois();
            for (Femme femme : femmesMarieesDeuxFois) {
                System.out.println(femme.getNom() + " " + femme.getPrenom());
            }
            
            System.out.println("\n=== Men married to 4 women between dates ===");
            List<Homme> hommes = hommeService.findHommesMarieesQuatreFemmesEntreDates(date1, date2);
            for (Homme h : hommes) {
                System.out.println(h.getNom() + " " + h.getPrenom());
            }
            
            System.out.println("\n=== All marriages of a man with details ===");
            List<Mariage> mariages = hommeService.findMariagesByHomme(h1.getId());
            System.out.println("Nom : " + h1.getNom() + " " + h1.getPrenom());
            
            List<Mariage> mariagesEnCours = new java.util.ArrayList<>();
            List<Mariage> mariagesEchoues = new java.util.ArrayList<>();
            
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    mariagesEnCours.add(m);
                } else {
                    mariagesEchoues.add(m);
                }
            }
            
            if (!mariagesEnCours.isEmpty()) {
                System.out.println("Mariages En Cours :");
                int count = 1;
                for (Mariage m : mariagesEnCours) {
                    System.out.println(count + ". Femme : " + m.getFemme().getNom() + " " + 
                        m.getFemme().getPrenom() + "   Date Début : " + 
                        new SimpleDateFormat("dd/MM/yyyy").format(m.getDateDebut()) + 
                        "    Nbr Enfants : " + m.getNbrEnfant());
                    count++;
                }
            }
            
            if (!mariagesEchoues.isEmpty()) {
                System.out.println("\nMariages échoués :");
                int count = 1;
                for (Mariage m : mariagesEchoues) {
                    System.out.println(count + ". Femme : " + m.getFemme().getNom() + " " + 
                        m.getFemme().getPrenom() + "  Date Début : " + 
                        new SimpleDateFormat("dd/MM/yyyy").format(m.getDateDebut()) + 
                        "    Date Fin : " + new SimpleDateFormat("dd/MM/yyyy").format(m.getDateFin()) +
                        "    Nbr Enfants : " + m.getNbrEnfant());
                    count++;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hommeService.close();
            femmeService.close();
            mariageService.close();
        }
    }
}

