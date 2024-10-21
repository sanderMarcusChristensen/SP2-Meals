package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.MealDTO;
import dat.entities.Meal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MealDAO implements IDAO<MealDTO, Integer> {

    private static MealDAO instance;
    private static EntityManagerFactory emf;

    public static MealDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MealDAO();
        }
        return instance;
    }

    @Override
    public MealDTO read(Integer integer) {
        return null;
    }

    @Override
    public List<MealDTO> readAll() {
        return List.of();
    }

    @Override
    public MealDTO create(MealDTO mealDTO) {
        return null;
    }

    @Override
    public MealDTO update(Integer integer, MealDTO mealDTO) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Meal meal = em.find(Meal.class, integer);
            return meal != null;
        }
    }
}