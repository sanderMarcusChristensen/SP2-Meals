package dat.security.dtos;


import dat.entities.Ingredients;
import dat.security.entities.Role;
import dat.security.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data

public class RoleDTO {


    private String name;
    private Set<UserDTO> users = new HashSet<>();

    public RoleDTO( String name, Set<UserDTO> users) {  // Måske id her

        this.name = name;
        this.users = users;
    }

    public RoleDTO(Role role){
        this.name = role.getRoleName();

        if (role.getUsers() != null) {
            this.users = role.getUsers().stream()
                    .map(UserDTO::new)
                    .collect(Collectors.toSet()); }
    }


}
