package dat.daos.impl;

import dat.daos.IDAO;
import dat.entities.Ingredients;
import dat.security.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDAO implements IDAO<UserDTO, Integer> {

    private static UserDAO instance;
    private static EntityManagerFactory emf;

    public static UserDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }
    @Override
    public UserDTO read(Integer integer) {
        return null;
    }

    @Override
    public List<UserDTO> readAll() {
        return List.of();
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO update(Integer integer, UserDTO userDTO) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }


    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Ingredients ingredients = em.find(Ingredients.class, integer);
            return ingredients != null;
        }
    }
}
