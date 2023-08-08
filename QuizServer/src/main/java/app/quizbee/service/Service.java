package app.quizbee.service;

import java.util.List;

public interface Service<E> {

    public List<E> select(String query, Object... args);

    public void insert(E modal);

    public void delete(E modal);

    public void update(E modal);
}
