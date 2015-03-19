package gps.data.hibernate;

import java.io.Serializable;

/**
 * Created by Sony on 12/03/2015.
 */
public interface CrudDao {
    <T> Serializable save(T message);

    <T> T load(Class<T> entityClass, Serializable id);
}
