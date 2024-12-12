package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.security.dtos.RoleDTO;
import dat.security.dtos.UserDTO;
import dat.security.entities.Role;
import dat.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAO {

    private static UserDAO instance;
    private static EntityManagerFactory emf;

    public static UserDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }

    public UserDTO read(Integer integer) {
        return null;
    }


    public List<UserDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            List<User> users = query.getResultList();

            List<UserDTO> list = new ArrayList<>();
            for (User u : users) {
                UserDTO dto = new UserDTO(u);
                list.add(dto);
            }
            return list;
        }
    }






    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Ingredients ingredients = em.find(Ingredients.class, integer);
            return ingredients != null;
        }
    }
}
