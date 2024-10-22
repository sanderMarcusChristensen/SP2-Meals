package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.IngredientsDTO;
import dat.entities.Ingredients;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientsDAO implements IDAO<IngredientsDTO,Integer> {

    private EntityManagerFactory emf;

    public IngredientsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public IngredientsDTO read(Integer integer) {
       try(EntityManager em = emf.createEntityManager()) {
           em.getTransaction().begin();
           Ingredients ingredients = em.find(Ingredients.class, integer);

           if(ingredients != null){
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
        return null;

    }

    @Override
    public IngredientsDTO create(IngredientsDTO ingredientsDTO) {
        Ingredients ingredients = new Ingredients(ingredientsDTO);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

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
    public IngredientsDTO update(Integer integer, IngredientsDTO ingredientsDTO) {
        return null;
    }

    @Override
    public void delete(Integer integer) {


    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return false;
    }
}

