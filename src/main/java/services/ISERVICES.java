package services;

import java.sql.SQLException;
import java.util.List;

public interface ISERVICES<T> {

    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(T t) throws SQLException;

    public T getCategoryById(int id) throws SQLException;


    public T getCategoryByName(String Nom) throws SQLException;
    public List<T> afficher() throws SQLException;
}
