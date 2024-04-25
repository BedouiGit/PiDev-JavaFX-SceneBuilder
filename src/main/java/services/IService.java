package services;

import entities.actualite;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    public boolean ajouter (T t) throws SQLException;
    public void modifier (T t) throws SQLException;
    public void supprimer (T t) throws SQLException;
    public List<T> afficher () throws SQLException;


    List<actualite> recuperer() throws SQLException;
}
