package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.IngredientsDTO;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
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
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Meal meal = em.find(Meal.class, integer);
            return meal != null;
        }
    }

    @Override
    public MealDTO create(MealDTO mealDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Meal meal = new Meal(mealDTO);

            List<Ingredients> ingredientsToAdd = new ArrayList<>();
            for (IngredientsDTO ingredientDTO : mealDTO.getIngredients()) {
                Ingredients existingIngredient = findIngredient(em, ingredientDTO);
                if (existingIngredient != null) {
                    ingredientsToAdd.add(existingIngredient);
                } else {
                    Ingredients newIngredient = new Ingredients();
                    newIngredient.setName(ingredientDTO.getName());
                    newIngredient.setQuantity(ingredientDTO.getQuantity());
                    em.persist(newIngredient);
                    ingredientsToAdd.add(newIngredient);
                }
            }

            meal.setIngredients(ingredientsToAdd);
            em.persist(meal);
            em.getTransaction().commit();
            return new MealDTO(meal);
            //Catch exceptions
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Ingredients findIngredient(EntityManager em, IngredientsDTO ingredientDTO) {
        TypedQuery<Ingredients> query = em.createQuery("SELECT i FROM Ingredients i WHERE i.name = :name AND i.quantity = :quantity", Ingredients.class);
        query.setParameter("name", ingredientDTO.getName());
        query.setParameter("quantity", ingredientDTO.getQuantity());
        List<Ingredients> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
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

    public void delete(Integer mealId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Meal meal = em.find(Meal.class, mealId);
            if (meal != null) {
                em.remove(meal);
            }
            em.getTransaction().commit();
        }
    }

    public List<MealDTO> maxPrepTime(int prepTime) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<MealDTO> query = em.createQuery("SELECT new dat.dtos.MealDTO(m) FROM Meal m WHERE m.mealPrepTime <= :prepTime", MealDTO.class);
            query.setParameter("prepTime", prepTime);
            return query.getResultList();
        }
    }

    public List<MealDTO> urmom() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<MealDTO> query = em.createQuery("SELECT new dat.dtos.MealDTO(m) FROM Meal m", MealDTO.class);
            return query.getResultList();
        }
    }
}