package model;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContainerElementSaveTest.HibernateMappingConfiguration.class,
        loader = AnnotationConfigContextLoader.class)
//@ContextConfiguration("classpath:app-context.xml")
@Transactional
public class ContainerElementSaveTest {
    @Autowired
    private SessionFactory sessionFactory;
    private int counter;
    private boolean lazy = true;

    @Before
    public void setup() {
        lazy = true;
    }

    @Test
    public void saveManyToMany() throws Exception {
        Element element = createElementAndSave();
        Container container = new Container();
        container.setName("First Container");
        saveObject(container);

        container.setElementos(new HashSet<Element>());
        container.getElementos().add(element);
        container = saveObject(container);
        Iterator<Element> iterator = container.getElementos().iterator();
        assertThat(iterator.next().getName(), equalTo(element.getName()));
        Assert.assertFalse(iterator.hasNext());

        Element anotherElement = createElementAndSave();
        container.getElementos().add(anotherElement);
        TreeSet<Element> sortedSet = new TreeSet<Element>(container.getElementos());
        iterator = sortedSet.iterator();
        assertThat(iterator.next().getName(), equalTo(element.getName()));
        assertThat(iterator.next().getName(), equalTo(anotherElement.getName()));
        Assert.assertFalse(iterator.hasNext());

        container.getElementos().remove(element);
        container = saveObject(container);
        iterator = container.getElementos().iterator();
        assertThat(iterator.next().getName(), equalTo(anotherElement.getName()));
        assertThat(iterator.hasNext(), equalTo(false));

    }

    @Test
    public void saveNoLazy() throws Exception {
        lazy = false;
        Element element = createElementAndSave();

        Container container = new Container();
        container.setName("First Container");
        saveObject(container);

        container.setElementos(new HashSet<Element>());
        container.getElementos().add(element);
        saveObject(container);

        container.getElementos().add(createElementAndSave());
        saveObject(container);

        container.getElementos().remove(element);
        saveObject(container);
    }

    @Test
    public void saveStaleObjects() throws Exception {
        Element element = createElementAndSave();
        Container container = new Container();
        container.setName("First Container");
        container = saveObject(container);

        Container containerStale = new Container();
        containerStale.setId(container.getId());
        containerStale.setName(container.getName());
        containerStale.setElementos(new HashSet<Element>());
        Element staleElement = new Element();
        staleElement.setId(element.getId());
        staleElement.setName(System.currentTimeMillis() + "");
        containerStale.getElementos().add(staleElement);
        saveObject(containerStale);

        containerStale.getElementos().add(createElementAndSave());
        saveObject(containerStale);

        Element elementToRemove = (Element) containerStale.getElementos()
                .toArray()[1];
        containerStale.getElementos().remove(elementToRemove);
        saveObject(containerStale);
    }

    private Element createElementAndSave() {
        Element element = createElement();
        saveObject(element);
        return element;
    }

    private Element createElement() {
        Element element = new Element();
        element.setName("Element" + ++counter);
        return element;
    }

    private <T extends Identifier> T saveObject(T container) {
        //   sessionFactory.getCurrentSession().saveOrUpdate(container);
        flushAndClearSession();
        container = getEntityWithCriteria(container);

        System.out.println(container);
        // sessionFactory.getCurrentSession().clear();
        return container;
    }

    public void flushAndClearSession() {
        // sessionFactory.getCurrentSession().flush();
        //sessionFactory.getCurrentSession().clear();
    }

    private <T extends Identifier> T getEntityWithCriteria(T container) {
        Criteria add = getCriteria(container);
        if (lazy) {
            add.setFetchMode("elementos", FetchMode.LAZY);
            container = (T) add.uniqueResult();
        } else {
            add.setFetchMode("elementos", FetchMode.JOIN);
            container = (T) add.uniqueResult();
            //  sessionFactory.getCurrentSession().clear();
        }
        return container;
    }

    private Criteria getCriteria(Identifier container) {
        //return sessionFactory.getCurrentSession()
        //      .createCriteria(container.getClass())
        //    .add(Restrictions.eq("id", container.getId()));
        return null;
    }

    private <T> T getEntity(T container, Serializable id) {
        //return (T) sessionFactory.getCurrentSession().get(
        //      container.getClass(), id);
        return null;
    }

    @Configuration
    @ImportResource("classpath:app-context.xml")
    public static class HibernateMappingConfiguration {

        @Bean
        @Qualifier("mappings")
        public List getListMappings() {
            return Arrays.asList("model/Container.hbm.xml", "model/Elemento.hbm.xml");
        }
    }
}
