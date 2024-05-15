package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {


    //DB

    final String URL = "jdbc:mysql://localhost:3306/badbud";
    final String USR = "root";
    final String PWD = "";


    //att
    private Connection cnx;

    private static MaConnexion instance;
    //constructor
    //singelton (Design Pattern) needs a private constructor
    private MaConnexion(){
        try {
            cnx = DriverManager.getConnection(URL,USR,PWD);
            System.out.println("Connexion aver succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getURL() {
        return URL;
    }

    public String getUSR() {
        return USR;
    }

    public String getPWD() {
        return PWD;
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
        if(instance == null)
            instance = new MaConnexion();

        return instance;
    }
}
