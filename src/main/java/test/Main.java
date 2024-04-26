package test;

import models.category;
import models.projets;
import services.CategoryService;
import services.ProjetsServices;
import utils.MyDB;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        MyDB bd1 = MyDB.getInstance();


        CategoryService serviceCategory = new CategoryService();

        category category1 = new category(1, "sarah2", "Description of Music category", "music.jpg");


        serviceCategory.ajouter(category1);


        List<category> allCategories = serviceCategory.afficher();

        for (category cat : allCategories) {
            System.out.println(cat);
        }

        category retrievedCategory = serviceCategory.getCategoryById(39);
        System.out.println("Retrieved Category: " + retrievedCategory);

        if (retrievedCategory != null) {
            retrievedCategory.setNom("Updated sarah");
            retrievedCategory.setDescription("Updated Description of Music category");
            retrievedCategory.setPhotoUrl("updated_music.jpg");
            serviceCategory.modifier(retrievedCategory);
        }

        serviceCategory.supprimer(category1);

        List<category> categoriesAfterDeletion = serviceCategory.afficher();
        System.out.println("Categories after deletion: " + categoriesAfterDeletion);









        ProjetsServices projetsServices = new ProjetsServices();

        projets projet1 = new projets(1, "Projet1", "Description of Projet1", "Wallet Address 1", new Date(System.currentTimeMillis()), "photo1.jpg",1);

        projetsServices.insererProjet(projet1);

        List<projets> allProjets = projetsServices.obtenirToutesLesProjets();

        for (projets proj : allProjets) {
            System.out.println(proj);
        }

        projets retrievedProjet = projetsServices.obtenirProjets(20);
        System.out.println("Retrieved Projet: " + retrievedProjet);

        if (retrievedProjet != null) {
            retrievedProjet.setNom("Updated Projet");
            retrievedProjet.setDescription("Updated Description of Projet");
            retrievedProjet.setWallet_address("Updated Wallet Address");
            retrievedProjet.setPhotoUrl("updated_photo.jpg");
            projetsServices.modifierProjet(retrievedProjet);
        }

        projetsServices.supprimerProjet(17);

        List<projets> projetsAfterDeletion = projetsServices.obtenirToutesLesProjets();
        System.out.println("Projets after deletion: " + projetsAfterDeletion);
    }
}
