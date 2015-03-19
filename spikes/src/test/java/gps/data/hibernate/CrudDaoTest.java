package gps.data.hibernate;

import gps.data.hibernate.entities.Message;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Sony on 12/03/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:app-hibernate-layer.xml")
@Transactional
public class CrudDaoTest {
    @Autowired
    CrudDao crudDao;
    @Autowired
    HibernateTemplate hibernateTemplate;

    @Test
    public void testGetDao() throws Exception {
        Assert.assertTrue(crudDao instanceof HibernateTemplate);
    }

    @Test
    public void testSave() throws Exception {
        Message message = new Message();
        Serializable entityId = crudDao.save(message);
        Assert.assertThat(entityId, CoreMatchers.notNullValue());
    }

    @Test
    public void testUpdateWithManyToOne() throws Exception {
        Serializable idEntity = crudDao.save(new Message());
        Message message = crudDao.load(Message.class, idEntity);
        message.setText("Greetings Earthling");
        Message nextMessage = new Message("Take me to your leader (please)");
        message.setNextMessage(nextMessage);
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        message = hibernateTemplate.get(Message.class, idEntity);
        Assert.assertThat(message.getNextMessage().getText(), CoreMatchers.equalTo("Take me to your leader (please)"));
    }
}
