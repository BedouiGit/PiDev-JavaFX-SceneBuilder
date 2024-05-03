package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {



    private final String USER="root";

    private final String PWD="";

            private final String URL= "jdbc:mysql://localhost:3306/badbud";

    private Connection connection;


    public static  MyDB instance;

//1 rendre le constructeur private
    private  MyDB(){

            try {
                connection= DriverManager.getConnection(URL,USER,PWD);
                System.out.println("connected to db");
            }
           catch (SQLException e){
                System.out.println(e.getMessage());
           }
    }


    //2 creer une methode static

    public static MyDB getInstance(){
    if(instance==null)  {
        instance= new MyDB();
    }
    return  instance;
    }


    public Connection getConnection() {
        return connection;
    }
}
