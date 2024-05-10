package services;

import models.Commande;
import utils.MaConnexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommandeServicePub implements IServicePub<Commande> {


    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Commande commande) {

        String req = "INSERT INTO `commande`(`email`, `wallet`, `total`) VALUES (?,?,?)";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,commande.getEmail());
            ps.setString(2,commande.getWallet());
            ps.setDouble(3,commande.getTotal());

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Commande commande) {
        String req = "UPDATE `commande` SET `email` = ?, `wallet` = ?, `total` = ? WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, commande.getEmail());
            ps.setString(2, commande.getWallet());
            ps.setDouble(3, commande.getTotal());
            ps.setInt(4, commande.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Commande commande) {
        String req = "DELETE FROM `commande` WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, commande.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Commande> getAll() {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT * FROM `commande`";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setEmail(rs.getString("email"));
                commande.setWallet(rs.getString("wallet"));
                commande.setTotal(rs.getDouble("total"));
                Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date = null;
                if (timestamp != null) {
                    date = timestamp.toLocalDateTime();
                }
                commande.setDate(date);
                commandes.add(commande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commandes;
    }

    @Override
    public Commande getOne(int id) {
        String req = "SELECT * FROM `commande` WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setEmail(rs.getString("email"));
                commande.setWallet(rs.getString("wallet"));
                commande.setTotal(rs.getDouble("total"));
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                commande.setDate(date);
                return commande;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public List<Commande> searchBy(String filterBy, String search) throws SQLException {
        List<Commande> Commandes = new ArrayList<>();
        String req = "SELECT * FROM `commande` WHERE `"+filterBy+"` LIKE '%"+search+"%'";
        System.out.println(req);
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {

            Commande commande = new Commande();
            commande.setId(res.getInt("id"));
            commande.setEmail(res.getString("email"));
            commande.setWallet(res.getString("wallet"));
            commande.setTotal(res.getDouble("total"));

            Commandes.add(commande);
        }

        return Commandes;
    }

    public List<Commande> getRecentCommandes(int x) {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT * FROM `commande` ORDER BY `id` DESC LIMIT ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, x);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setEmail(rs.getString("email"));
                commande.setWallet(rs.getString("wallet"));
                commande.setTotal(rs.getDouble("total"));

                Timestamp timestamp = rs.getTimestamp("date");
                LocalDateTime date;
                if (timestamp != null) {
                    date = timestamp.toLocalDateTime();
                } else {
                    date = LocalDateTime.now(); // Use the current system time if the timestamp is null
                }
                commande.setDate(date);
                commandes.add(commande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commandes;
    }

}
