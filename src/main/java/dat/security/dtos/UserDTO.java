package dat.security.dtos;

import dat.security.entities.Role;
import dat.security.entities.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {


    private String userName;
    private String password;
    private Set<RoleDTO> roles = new HashSet<>();


    public UserDTO( String userName, String password, Set<RoleDTO> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public UserDTO(String username){
        this.userName = username;
    }

    public UserDTO(User user) {
        this.userName = user.getUsername();
        this.password = user.getPassword();
        if (user.getRoles() != null) {
            this.roles = user.getRoles().stream()
                    .map(role -> new RoleDTO(role.getRoleName())) // Only map role name or basic details
                    .collect(Collectors.toSet());
        }
    }

}
