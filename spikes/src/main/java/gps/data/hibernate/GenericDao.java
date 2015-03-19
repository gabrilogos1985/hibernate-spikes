package gps.data.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 * Created by Sony on 12/03/2015.
 */
public class GenericDao<T> extends HibernateTemplate implements CrudDao {
    public GenericDao() {
    }

    public GenericDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
