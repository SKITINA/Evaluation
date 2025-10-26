package com.example.evaluation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;

public class ProductOrderTest {
    
    public static void main(String[] args) {
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();
        
        try {
            // Create categories
            Categorie cat1 = new Categorie("CAT1", "Electronics");
            Categorie cat2 = new Categorie("CAT2", "Clothing");
            categorieService.create(cat1);
            categorieService.create(cat2);
            
            // Create products
            Produit p1 = new Produit("ES12", 120, cat1);
            Produit p2 = new Produit("ZR85", 100, cat1);
            Produit p3 = new Produit("EE85", 200, cat2);
            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            
            // Create orders
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-01");
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-31");
            
            Commande cmd1 = new Commande(date1);
            Commande cmd2 = new Commande(date2);
            commandeService.create(cmd1);
            commandeService.create(cmd2);
            
            // Create order lines
            LigneCommandeProduit l1 = new LigneCommandeProduit(7, p1, cmd1);
            LigneCommandeProduit l2 = new LigneCommandeProduit(14, p2, cmd1);
            LigneCommandeProduit l3 = new LigneCommandeProduit(5, p3, cmd2);
            ligneCommandeService.create(l1);
            ligneCommandeService.create(l2);
            ligneCommandeService.create(l3);
            
            // Test: Get products by category
            System.out.println("=== Products by Category ===");
            List<Produit> produits = produitService.findByIdCategorie(cat1.getId());
            for (Produit p : produits) {
                System.out.println("Reference: " + p.getReference() + ", Prix: " + p.getPrix());
            }
            
            // Test: Get products ordered between dates
            System.out.println("\n=== Products ordered between dates ===");
            Date dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-01");
            Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse("2013-03-31");
            List<Produit> produitsCommande = produitService.findCommandesEntreDates(dateDebut, dateFin);
            for (Produit p : produitsCommande) {
                System.out.println("Reference: " + p.getReference() + ", Prix: " + p.getPrix());
            }
            
            // Test: Get products by order
            System.out.println("\n=== Order details ===");
            List<Object[]> lignes = produitService.findProduitsParCommande(cmd1.getId());
            System.out.println("Commande : " + cmd1.getId() + "  Date : " + 
                new SimpleDateFormat("dd MMMM yyyy").format(cmd1.getDate()));
            System.out.println("Liste des produits :");
            System.out.println("Référence   Prix    Quantité");
            for (Object[] ligne : lignes) {
                System.out.println(ligne[0] + "        " + ligne[1] + " DH  " + ligne[2]);
            }
            
            // Test: Get products with price > 100
            System.out.println("\n=== Products with price > 100 ===");
            List<Produit> produitsChers = produitService.findByPrixGreaterThan(100);
            for (Produit p : produitsChers) {
                System.out.println("Reference: " + p.getReference() + ", Prix: " + p.getPrix());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            categorieService.close();
            produitService.close();
            commandeService.close();
            ligneCommandeService.close();
        }
    }
}

