package gps.data.hibernate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Sony on 12/03/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:app-hibernate-layer.xml")
public class CrudDaoTest {
    @Autowired
    CrudDao crudDao;

    @Test
    public void testGetDao() throws Exception {
        Assert.assertTrue(crudDao instanceof HibernateTemplate);
    }
}
