package dat;

import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class PopulateIngredientsForTest {

    private IngredientsDAO dao;
    private EntityManagerFactory emf;
    private IngredientsDTO i1, i2, i3, i4;


    public PopulateIngredientsForTest(IngredientsDAO dao, EntityManagerFactory emf) {
        this.dao = dao;
        this.emf = emf;
    }

    public List<IngredientsDTO> populateIngredientsInDatabase() {


        List<IngredientsDTO> ingredientsDTOS = null;

        ingredientsDTOS = new ArrayList<>();

        i1 = new IngredientsDTO(null, "Olive Oil", "1 tablespoon");
        i2 = new IngredientsDTO(null, "Garlic", "3 cloves");
        i3 = new IngredientsDTO(null, "Chicken Breast", "500 grams");
        i4 = new IngredientsDTO(null, "Basil", "1 cup (fresh)");

        IngredientsDTO saved1 = dao.addCreated(i1);
        IngredientsDTO saved2 = dao.addCreated(i2);
        IngredientsDTO saved3 = dao.addCreated(i3);
        IngredientsDTO saved4 = dao.addCreated(i4);

        ingredientsDTOS.add(saved1);
        ingredientsDTOS.add(saved2);
        ingredientsDTOS.add(saved3);
        ingredientsDTOS.add(saved4);


        return ingredientsDTOS;

    }


    public void cleanUpIngredients() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            //em.createQuery("DELETE FROM Ingredients").executeUpdate();
            // em.createNativeQuery("ALTER SEQUENCE ingredient_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





