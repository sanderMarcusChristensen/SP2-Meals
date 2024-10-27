package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.IngredientsDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


import java.util.List;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class IngredientsDAO implements IDAO<IngredientsDTO, Integer> {

    private static IngredientsDAO instance;
    private static EntityManagerFactory emf;

    public static IngredientsDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new IngredientsDAO();
        }
        return instance;
    }


    @Override
    public IngredientsDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Ingredients ingredients = em.find(Ingredients.class, integer);

            if (ingredients != null) {
                return new IngredientsDTO(ingredients);
            }
            em.getTransaction().commit();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<IngredientsDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<IngredientsDTO> query = em.createQuery("SELECT new dat.dtos.IngredientsDTO(i) FROM Ingredients i", IngredientsDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public IngredientsDTO create(IngredientsDTO ingredientsDTO) {
        Ingredients ingredients = new Ingredients(ingredientsDTO);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            if (ingredientsDTO.getName() == null) {
                throw new IllegalArgumentException("Ingredient name is required");
            }

            if (ingredientsDTO.getQuantity() == null) {
                throw new IllegalArgumentException("Quantity is required");
            }

            TypedQuery<Ingredients> query = em.createQuery("SELECT i FROM Ingredients i WHERE i.name = :name", Ingredients.class);
            query.setParameter("name", ingredients.getName());

            List<Ingredients> existingIngredients = query.getResultList();

            if (!existingIngredients.isEmpty()) {
                return new IngredientsDTO(existingIngredients.get(0));
            }

            em.persist(ingredients);
            em.getTransaction().commit();

            return new IngredientsDTO(ingredients);
        }
    }

    @Override
    public IngredientsDTO update(Integer id, IngredientsDTO ingredientsDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Ingredients ingredients = em.find(Ingredients.class, id);

            if (ingredients == null) {
                em.getTransaction().rollback();
                return null;
            }
            if (ingredients.getName() != null) {
                ingredients.setName(ingredientsDTO.getName());
            }

            if (ingredients.getQuantity() != null) {
                ingredients.setQuantity(ingredientsDTO.getQuantity());
            }

            em.merge(ingredients);
            em.getTransaction().commit();
            return new IngredientsDTO(ingredients);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(500,"Something went wrong trying to update an ingredient");
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            // Find the ingredient to delete
            Ingredients ingredients = em.find(Ingredients.class, id);

            if (ingredients == null) {
                throw new ApiException(404, "Ingredient not found");
            }

            // Remove the ingredient from all meals that reference it
            List<Meal> meals = em.createQuery("SELECT m FROM Meal m JOIN m.ingredients i WHERE i.id = :ingredientId", Meal.class)
                    .setParameter("ingredientId", id)
                    .getResultList();

            for (Meal meal : meals) {
                meal.getIngredients().remove(ingredients);
                em.merge(meal); // Update the meal in the database
            }

            // Now remove the ingredient
            em.remove(ingredients);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Rollback on error
            e.printStackTrace();
            throw new IllegalArgumentException("Something went wrong trying to delete an ingredient");
        } finally {
            em.close(); // Ensure entity manager is closed
        }
    }


    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Ingredients ingredients = em.find(Ingredients.class, integer);
            return ingredients != null;
        }
    }

}

