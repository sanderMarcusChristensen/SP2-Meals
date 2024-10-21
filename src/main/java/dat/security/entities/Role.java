package dat.security.entities;


import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Purpose: To handle security in the API
 *  Author: Thomas Hartmann
 */
@Entity
@Table(name = "roles")
@NamedQueries(@NamedQuery(name = "Role.deleteAllRows", query = "DELETE from Role"))
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "name", length = 20)
    private String name;

    @Getter
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {}

    public Role(String roleName) {
        this.name = roleName;
    }

    public String getRoleName() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + name + '\'' +
                '}';
    }
}
