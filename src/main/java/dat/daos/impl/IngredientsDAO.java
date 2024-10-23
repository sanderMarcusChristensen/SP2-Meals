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

    private final EntityManagerFactory emf;

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
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Ingredients ingredients = em.find(Ingredients.class, id);

            if(ingredients == null){
                em.getTransaction().rollback();
                return null;
            }
            if(ingredients.getName() != null ){
                ingredients.setName(ingredientsDTO.getName());
            }

            if (ingredients.getQuantity() != null){
                ingredients.setQuantity(ingredientsDTO.getQuantity());
            }

            em.merge(ingredients);
            em.getTransaction().commit();
            return new IngredientsDTO(ingredients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Integer id) {


    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Ingredients ingredients = em.find(Ingredients.class, integer);
            return ingredients != null;
        }
    }

    public IngredientsDTO addCreated(IngredientsDTO dto) {

        Ingredients ingredients = new Ingredients(dto);


        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(ingredients);
            em.getTransaction().commit();

            return new IngredientsDTO(ingredients);

        }
    }
}

