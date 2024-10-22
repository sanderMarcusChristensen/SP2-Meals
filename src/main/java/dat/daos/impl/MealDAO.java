package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.MealDTO;
import dat.entities.Meal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
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
        try (EntityManager em = emf.createEntityManager()) {
            Meal meal = em.find(Meal.class, integer);
            return new MealDTO(meal);
        }
    }

    @Override
    public List<MealDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<MealDTO> query = em.createQuery("SELECT new dat.dtos.MealDTO(m) FROM Meal m", MealDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public MealDTO create(MealDTO mealDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Meal meal = new Meal(mealDTO);
            em.persist(meal);
            em.getTransaction().commit();
            return new MealDTO(meal);
        }
    }

    @Override
    public MealDTO update(Integer id, MealDTO mealDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Meal m = em.find(Meal.class, id);

            if (m == null) {
                em.getTransaction().rollback();
                return null;
            }

            if (mealDTO.getMealName() != null && !mealDTO.getMealName().isEmpty()) {
                m.setMealName(mealDTO.getMealName());
            }
            if (mealDTO.getMealDescription() != null && !mealDTO.getMealDescription().isEmpty()) {
                m.setMealDescription(mealDTO.getMealDescription());
            }
            if (mealDTO.getMealInstructions() != null && !mealDTO.getMealInstructions().isEmpty()) {
                m.setMealInstructions(mealDTO.getMealInstructions());
            }
            if (mealDTO.getMealPrepTime() > 0) {
                m.setMealPrepTime(mealDTO.getMealPrepTime());
            }
            if (mealDTO.getMealRating() >= 0 && mealDTO.getMealRating() <= 5) {
                m.setMealRating(mealDTO.getMealRating());
            }

            Meal mergedMeal = em.merge(m);
            em.getTransaction().commit();
            return new MealDTO(mergedMeal);
        }
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