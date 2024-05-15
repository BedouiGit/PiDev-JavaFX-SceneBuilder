package models;

import java.time.LocalDateTime;

public class Commande {

    // Att
    private int id;
    private String email, wallet;
    private double total;

    private LocalDateTime date ;


    // consturctor
    public Commande(int id, String email, String wallet, double total) {
        this.id = id;
        this.email = email;
        this.wallet = wallet;
        this.total = total;
    }
    public Commande(String email, String wallet, double total) {
        this.email = email;
        this.wallet = wallet;
        this.total = total;
    }

    public Commande() {
    }

    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // ToString

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", wallet='" + wallet + '\'' +
                ", total=" + total +
                '}';
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime  date) {
        this.date = date;
    }

}
