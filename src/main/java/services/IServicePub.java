package services;

import java.util.List;

public interface IServicePub<T>{

    //CRUD
    void add(T t);
    void update(T t);
    void delete(T t);
    List<T> getAll();
    T getOne(int id);
    //rechere by something

}
