package models;

import java.util.Date;

public class projets {

    private int id;
    private String nom;
    private  String wallet_address;
    private String description;
    private Date dateDeCreation;
    private String photoUrl;

    private int category_id;


    public projets(int id, String nom, String description, String wallet_address , Date dateDeCreation, String photoUrl , int category_id) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.wallet_address=wallet_address;
        this.dateDeCreation = dateDeCreation;
        this.photoUrl = photoUrl;
        this.category_id=category_id;
    }

    public projets() {

    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }


    public int getId() {
        return id;
    }

    public String getWallet_address() {
        return wallet_address;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateDeCreation() {
        return dateDeCreation;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWallet_address(String wallet_address) {
        this.wallet_address = wallet_address;
    }

    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", walletAdress='" + wallet_address + '\'' +
                ", dateDeCreation=" + dateDeCreation +
                ", photoUrl='" + photoUrl + '\'' +
                ", category_id='" + category_id + '\'' +
                '}';
    }
}
