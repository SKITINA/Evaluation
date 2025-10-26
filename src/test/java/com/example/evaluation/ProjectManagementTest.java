package com.example.evaluation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;

public class ProjectManagementTest {
    
    public static void main(String[] args) {
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();
        
        try {
            // Create employees
            Employe emp1 = new Employe("Safi", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Alami", "Fatima", "0612345679");
            employeService.create(emp1);
            employeService.create(emp2);
            
            // Create projects
            Date projDateDebut = new SimpleDateFormat("yyyy-MM-dd").parse("2013-01-14");
            Date projDateFin = new SimpleDateFormat("yyyy-MM-dd").parse("2013-12-31");
            Projet proj1 = new Projet("Gestion de stock", projDateDebut, projDateFin, emp1);
            projetService.create(proj1);
            
            // Create tasks
            Date tache1Debut = new SimpleDateFormat("yyyy-MM-dd").parse("2013-02-10");
            Date tache1Fin = new SimpleDateFormat("yyyy-MM-dd").parse("2013-02-20");
            Date tache2Debut = new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-10");
            Date tache2Fin = new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-15");
            Date tache3Debut = new SimpleDateFormat("yyyy-MM-dd").parse("2013-04-10");
            Date tache3Fin = new SimpleDateFormat("yyyy-MM-dd").parse("2013-04-25");
            
            Tache tache1 = new Tache("Analyse", tache1Debut, tache1Fin, 800, proj1);
            Tache tache2 = new Tache("Conception", tache2Debut, tache2Fin, 1000, proj1);
            Tache tache3 = new Tache("Développement", tache3Debut, tache3Fin, 1200, proj1);
            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            
            // Create employee tasks
            Date dateDebutReelle1 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-02-10");
            Date dateFinReelle1 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-02-20");
            Date dateDebutReelle2 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-10");
            Date dateFinReelle2 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-15");
            Date dateDebutReelle3 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-04-10");
            Date dateFinReelle3 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-04-25");
            
            EmployeTache et1 = new EmployeTache(dateDebutReelle1, dateFinReelle1, emp1, tache1);
            EmployeTache et2 = new EmployeTache(dateDebutReelle2, dateFinReelle2, emp1, tache2);
            EmployeTache et3 = new EmployeTache(dateDebutReelle3, dateFinReelle3, emp1, tache3);
            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            
            // Test: Get tasks by employee
            System.out.println("=== Tasks by Employee ===");
            List<Tache> taches = employeService.findTachesByEmploye(emp1.getId());
            for (Tache t : taches) {
                System.out.println("Tache: " + t.getNom() + ", Prix: " + t.getPrix());
            }
            
            // Test: Get projects by employee
            System.out.println("\n=== Projects managed by employee ===");
            List<Projet> projets = employeService.findProjetsByEmploye(emp1.getId());
            for (Projet p : projets) {
                System.out.println("Projet: " + p.getNom() + ", ID: " + p.getId());
            }
            
            // Test: Get tasks by project
            System.out.println("\n=== Tasks planned for project ===");
            List<Tache> tachesProjet = projetService.findTachesByProjet(proj1.getId());
            for (Tache t : tachesProjet) {
                System.out.println("Tache: " + t.getNom() + ", Date debut: " + t.getDateDebut());
            }
            
            // Test: Get tasks realized for project
            System.out.println("\n=== Completed tasks with real dates ===");
            List<EmployeTache> tachesRealisees = projetService.findTachesRealiseesByProjet(proj1.getId());
            System.out.println("Projet : " + proj1.getId() + "      Nom : " + proj1.getNom() +
                "     Date début : " + new SimpleDateFormat("dd MMMM yyyy").format(proj1.getDateDebut()));
            System.out.println("Liste des tâches:");
            System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");
            for (EmployeTache et : tachesRealisees) {
                System.out.println(et.getId() + "  " + et.getTache().getNom() + "        " +
                    new SimpleDateFormat("dd/MM/yyyy").format(et.getDateDebutReelle()) + "          " +
                    new SimpleDateFormat("dd/MM/yyyy").format(et.getDateFinReelle()));
            }
            
            // Test: Get tasks with price > 1000
            System.out.println("\n=== Tasks with price > 1000 ===");
            List<Tache> tachesChere = tacheService.findByPrixGreaterThan(1000);
            for (Tache t : tachesChere) {
                System.out.println("Tache: " + t.getNom() + ", Prix: " + t.getPrix());
            }
            
            // Test: Get tasks realized between dates
            System.out.println("\n=== Tasks realized between dates ===");
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-01-01");
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-12-31");
            List<EmployeTache> tachesEntreDates = tacheService.findRealiseesEntreDates(date1, date2);
            for (EmployeTache et : tachesEntreDates) {
                System.out.println("Tache: " + et.getTache().getNom() + ", Date debut reelle: " +
                    et.getDateDebutReelle() + ", Date fin reelle: " + et.getDateFinReelle());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            employeService.close();
            projetService.close();
            tacheService.close();
            employeTacheService.close();
        }
    }
}

