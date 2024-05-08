package models;


import java.time.LocalDateTime;

public class NFT {


    // Att

    private int id;
    private String name, status, image;
    private double price;
    private LocalDateTime  creation_date ;


    // consturctor


    public NFT(int id, String name, String status, String image, double price) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.image = image;
        this.price = price;
    }

    public NFT(String name, String status, String image, double price) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.price = price;
    }

    public NFT() {
    }


    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ToString

    @Override
    public String toString() {
        return "nft{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }


    public LocalDateTime getDate() {
        return creation_date;
    }

    public void setDate(LocalDateTime  date) {
        this.creation_date = date;
    }

}
