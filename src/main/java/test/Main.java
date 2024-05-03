package test;

import entities.Posts;
import services.ServicePosts;
import utils.BadWordFilter;
import utils.MyDB;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {

        MyDB db = MyDB.getInstance();
        // MyDB db1 = new MyDB();

        System.out.println(db.hashCode());
        // System.out.println(db1.hashCode());


//Posts p= new Posts("wiw","wiw");
      // Posts p3= new Posts("wiw3","wiw3");

       // Posts p2= new Posts("wiw","wiw_edit");
        ServicePosts sp=new ServicePosts();

        List<Posts> posts ;
        try {
            sp.afficher();
            System.out.println("showed succefully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;



        String input="not a bad word";
        String output = BadWordFilter.getCensoredText(input);
        System.out.println(output);


/*
        try {
            sp.ajouter(p3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            sp.modifier(p2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
*/


    }

}
