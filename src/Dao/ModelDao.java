package Dao;

import java.util.List;

public interface ModelDao <A>{
    public void insert (A object);
    public void update(A object);
    public void delete (String id);
    public List<A> getAll();
    public List<A> getCari(String key);
}


