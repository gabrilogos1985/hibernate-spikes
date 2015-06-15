package gps.data.hibernate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Sony on 10/06/2015.
 */
@Entity
@Table(name = "User")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class User {
    @EmbeddedId
    // @AttributeOverride(name = "firstName", column = @Column(name = "fld_firstname"))
    private UserId userId;

    private Integer age;

}
