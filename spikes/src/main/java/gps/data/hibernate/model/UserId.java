package gps.data.hibernate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Sony on 10/06/2015.
 */
@Embeddable
@Table(name = "userId")
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class UserId implements Serializable {
    private String firstName;
    private String lastName;

    public UserId(String name, String lasName) {
        this.firstName = name;
        this.lastName = lasName;
    }
}
