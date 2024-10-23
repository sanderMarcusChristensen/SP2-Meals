package dat.routes;

import dat.daos.impl.MealDAO;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class Populator {

    private static EntityManagerFactory emf;
    private static MealDAO mealDAO;

    public Populator(EntityManagerFactory emf, MealDAO mealDAO) {
        this.emf = emf;
        this.mealDAO = mealDAO;
    }

    public void populateDatabase() {
        // Burger
        Meal m1 = Meal.builder()
                .mealName("Burger")
                .mealDescription("A delicious burger")
                .mealInstructions("Grill beef, assemble burger")
                .mealPrepTime(10)
                .mealRating(3.1)
                .build();
        m1.setIngredients(List.of(
                new Ingredients("Bun", "1"),
                new Ingredients("Beef patty", "150g"),
                new Ingredients("Cheddar cheese", "1 slice")
        ));

        // Pizza
        Meal m2 = Meal.builder()
                .mealName("Pizza")
                .mealDescription("A delicious pizza")
                .mealInstructions("Make dough, bake pizza")
                .mealPrepTime(15)
                .mealRating(4.1)
                .build();
        m2.setIngredients(List.of(
                new Ingredients("Pizza dough", "1 ball"),
                new Ingredients("Tomato sauce", "100ml"),
                new Ingredients("Mozzarella cheese", "100g")
        ));


        Meal m3 = Meal.builder()
                .mealName("Pasta")
                .mealDescription("A delicious pasta")
                .mealInstructions("Cook pasta, mix with sauce")
                .mealPrepTime(20)
                .mealRating(5.1)
                .build();
        m3.setIngredients(List.of(
                new Ingredients("Spaghetti", "200g"),
                new Ingredients("Tomato sauce", "100ml"),
                new Ingredients("Parmesan cheese", "50g")
        ));

        MealDTO mDTO1 = new MealDTO(m1);
        MealDTO mDTO2 = new MealDTO(m2);
        MealDTO mDTO3 = new MealDTO(m3);

        mealDAO.create(mDTO1);
        mealDAO.create(mDTO2);
        mealDAO.create(mDTO3);
    }


    public void clearDatabase() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Meal").executeUpdate();
            em.createQuery("DELETE FROM Ingredients").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE meal_meal_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE ingredients_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
