package gps.data.hibernate;

import gps.data.hibernate.entities.Message;
import org.hamcrest.CoreMatchers;
import org.hibernate.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sony on 18/03/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:app-hibernate-layer.xml")
@Transactional
public class HibernateTemplateTest {

    @Autowired
    HibernateTemplate template;

    @Test(expected = ObjectNotFoundException.class)
    public void testLoad() throws Exception {
        Message message = template.load(Message.class, Long.valueOf(1));
        Assert.assertThat(message.getIdMessage(), CoreMatchers.equalTo(1L));
        message.getMessage();
    }
}
