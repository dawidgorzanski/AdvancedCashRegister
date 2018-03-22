package cashregister.dao.interfaces;

import java.util.List;

public interface IBaseDao<T> {
    public T getById(final int id);
    public List<T> getAll();
    public T save(final T object);
    public void saveOrUpdate(final T object);
    public void delete(final Object object);
}
