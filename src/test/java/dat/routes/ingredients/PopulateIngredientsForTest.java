package dat.routes.ingredients;

import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import jakarta.persistence.EntityManagerFactory;

public class PopulateIngredientsForTest {

    private IngredientsDAO dao;
    private EntityManagerFactory emf;
    private IngredientsDTO i1, i2, i3, i4;


    public PopulateIngredientsForTest(IngredientsDAO dao, EntityManagerFactory emf) {
        this.dao = dao;
        this.emf = emf;
    }

    public void populateIngredientsInDatabase() {

        i1 = new IngredientsDTO("Olive Oil", "1 tablespoon");
        i2 = new IngredientsDTO("Garlic", "3 cloves");
        i3 = new IngredientsDTO("Chicken Breast", "500 grams");
        i4 = new IngredientsDTO("Basil", "1 cup (fresh)");

        dao.create(i1);
        dao.create(i2);
        dao.create(i3);
        dao.create(i4);
    }

    public void cleanUpIngredients() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Ingredients").executeUpdate();
            em.createQuery("DELETE FROM Meal").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE ingredients_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE meal_meal_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





