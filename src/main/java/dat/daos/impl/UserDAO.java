package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.IngredientsDTO;
import dat.entities.Ingredients;
import dat.exceptions.ApiException;
import dat.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDAO implements IDAO<UserDTO,Integer>{


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
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, integer);

            if (user != null) {
                return new UserDTO(user);
            }
            em.getTransaction().commit();
            return null;
        } catch (ApiException e) {
            e.getMessage();
            e.printStackTrace();
        }
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
