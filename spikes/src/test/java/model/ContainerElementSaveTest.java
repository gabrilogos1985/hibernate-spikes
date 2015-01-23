package model;

import java.util.HashSet;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:app-context.xml")
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
	public void save() throws Exception {
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
		saveObject(container);

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

	private void saveObject(Identifier container) {
		sessionFactory.getCurrentSession().saveOrUpdate(container);
		sessionFactory.getCurrentSession().flush();
		sessionFactory.getCurrentSession().clear();
		container = (Identifier) sessionFactory.getCurrentSession().get(
				container.getClass(), ((Identifier) container).getId());
		Criteria add = sessionFactory.getCurrentSession()
				.createCriteria(container.getClass())
				.add(Restrictions.eq("id", container.getId()));
		if (lazy) {
			add.setFetchMode("elementos", FetchMode.LAZY);
			container = (Identifier) add.uniqueResult();
		} else {
			add.setFetchMode("elementos", FetchMode.JOIN);
			container = (Identifier) add.uniqueResult();
			sessionFactory.getCurrentSession().clear();
		}

		System.out.println(container);
		sessionFactory.getCurrentSession().clear();
	}
}
