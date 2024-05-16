package test;

import models.Person;
import services.ServicePerson;
import services.ServicePosts;
import utils.BadWordFilter;
import utils.DBConnection;

import java.sql.SQLException;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        Person p = new Person("Ben Daoued","Yosra", 22);

        ServicePerson sp = new ServicePerson();

        try {
            //sp.insertOne(p);
            System.out.println(sp.selectAll());
        } catch (SQLException e) {
            System.err.println("Erreur: "+e.getMessage());
        }
        ServicePosts spp=new ServicePosts();

        List<entities.Posts> posts ;
        try {
            spp.afficher();
            System.out.println("showed succefully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;



        String input="not a bad word";
        String output = BadWordFilter.getCensoredText(input);
        System.out.println(output);


    }
}
