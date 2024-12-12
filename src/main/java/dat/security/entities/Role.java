package dat.security.entities;

import dat.security.dtos.RoleDTO;
import jakarta.persistence.*;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
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

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(String roleName) {
        this.name = roleName;
    }

    public String getRoleName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Role{" + "roleName='" + name + '\'' + '}';
    }


    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Role(RoleDTO dto) {
        this.name = dto.getName();
        if (dto.getUsers() != null) {
            this.users = dto.getUsers().stream()
                    .map(User::new)
                    .collect(Collectors.toSet()); } }
}