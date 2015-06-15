package gps.data.hibernate;

import gps.data.hibernate.model.User;
import gps.data.hibernate.model.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hamcrest.CoreMatchers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;

import static org.junit.Assert.assertThat;

/**
 * Created by Sony on 02/05/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:app-hibernate-layer.xml")
@Transactional()
public class CompositeIdTest {
    @Autowired
    HibernateTemplate hibernateTemplate;

    @org.junit.Test
    @Rollback(false)
    public void saveWithComposite() {
        User user = new User();
        user.setUserId(new UserId("Juan1", "Perez"));
        hibernateTemplate.save(user);
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        User userGet = hibernateTemplate.get(User.class, user.getUserId());
        assertThat(userGet, CoreMatchers.equalTo(user));

        Customer customer = new Customer();
        customer.setUser(user);
        CustomerId customerId = new CustomerId();
        customerId.setUserId(user.getUserId());
        customerId.setCustomerNumber("1020");
        customer.setId(customerId);
        //customer.setPreferredCustomer(true);
        customerId = (CustomerId) hibernateTemplate.save(customer);
        assertThat(customerId, CoreMatchers.notNullValue());
    }

    @Entity
    @Table(name = "Customer")
    @Getter
    @Setter
    static class Customer {
        @EmbeddedId
        CustomerId id;
        boolean preferredCustomer;

        @MapsId("userId")
        @JoinColumns({
                @JoinColumn(name = "userfirstname_fk", referencedColumnName = "firstName"),
                @JoinColumn(name = "userlastname_fk", referencedColumnName = "lastName")
        })
        @OneToOne
        User user;
    }

    @Embeddable
    @EqualsAndHashCode
    @Setter
    @Getter
    public static class CustomerId implements Serializable {
        UserId userId;
        String customerNumber;
    }

}
