package pl.compo;

public interface Dao<T> extends AutoCloseable {
    T read(String name) throws DaoException;

    void write(T obj, String name) throws DaoException;

}
