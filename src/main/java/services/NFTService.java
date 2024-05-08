package services;

import interfaces.IService;
import models.NFT;
import util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NFTService implements IService<NFT> {

    //att

    Connection cnx = MaConnexion.getInstance().getCnx();



    //actions
    @Override
    public void add(NFT nft) {

        String req = "INSERT INTO `nft`(`name`, `price`, `status`, `image`) VALUES (?,?,?,?)";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,nft.getName());
            ps.setDouble(2,nft.getPrice());
            ps.setString(3,nft.getStatus());
            ps.setString(4,nft.getImage());

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(NFT nft) {
        String req = "UPDATE `nft` SET `name` = ?, `price` = ?, `status` = ?, `image` = ? WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, nft.getName());
            ps.setDouble(2, nft.getPrice());
            ps.setString(3, nft.getStatus());
            ps.setString(4, nft.getImage());
            ps.setInt(5, nft.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(NFT nft) {
        String req = "DELETE FROM `nft` WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1, nft.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NFT> getAll() {

        List<NFT> nfts = new ArrayList<>();

        String req = "SELECT * FROM `nft`";
        try {

            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while(res.next()){

                NFT nft = new NFT();
                nft.setId(res.getInt("id"));
                nft.setName(res.getString("name"));
                nft.setPrice(res.getDouble("price"));
                nft.setStatus(res.getString("status"));
                nft.setImage(res.getString("image"));
                nfts.add(nft);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nfts;
    }


    public List<NFT> getAll(double minPrice) {
        List<NFT> nfts = new ArrayList<>();

        String req = "SELECT * FROM `nft` WHERE price > ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setDouble(1, minPrice);
            ResultSet res = pst.executeQuery();

            while(res.next()){
                NFT nft = new NFT();
                nft.setId(res.getInt("id"));
                nft.setName(res.getString("name"));
                nft.setPrice(res.getDouble("price"));
                nft.setStatus(res.getString("status"));
                nft.setImage(res.getString("image"));
                nfts.add(nft);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nfts;
    }

    @Override
    public NFT getOne(int id) {
        String req = "SELECT * FROM `nft` WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                NFT nft = new NFT();
                nft.setId(res.getInt("id"));
                nft.setName(res.getString("name"));
                nft.setPrice(res.getDouble("price"));
                nft.setStatus(res.getString("status"));
                nft.setImage(res.getString("image"));
                return nft;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public List<NFT> searchBy(String filterBy, String search) throws SQLException {
        List<NFT> NFTs = new ArrayList<>();
        String req = "SELECT * FROM `NFT` WHERE `"+filterBy+"` LIKE '%"+search+"%'";
        System.out.println(req);
        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()) {

            NFT nft = new NFT();
            nft.setId(res.getInt("id"));
            nft.setName(res.getString("name"));
            nft.setPrice(res.getDouble("price"));
            nft.setStatus(res.getString("status"));
            nft.setImage(res.getString("image"));

            NFTs.add(nft);
        }

        return NFTs;
    }

    public int getTotalnfts() {
        String req = "SELECT COUNT(*) AS total_nfts FROM nft";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return res.getInt("total_nfts");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


}
