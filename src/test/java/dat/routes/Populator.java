package dat.routes;

import dat.daos.impl.MealDAO;
import dat.entities.Meal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populator {

    private static EntityManagerFactory emf;
    private static MealDAO mealDAO;

    public Populator(EntityManagerFactory emf, MealDAO mealDAO) {
        this.emf = emf;
        this.mealDAO = mealDAO;
    }

    public void populateDatabase() {
        try (EntityManager em = emf.createEntityManager()) {
            Meal m1 = Meal.builder()
                    .mealName("Burger")
                    .mealDescription("A delicious burger")
                    .mealInstructions("Eat it")
                    .mealPrepTime(10)
                    .mealRating(4.5)
                    .build();
            Meal m2 = Meal.builder()
                    .mealName("Pizza")
                    .mealDescription("A delicious pizza")
                    .mealInstructions("Eat it")
                    .mealPrepTime(20)
                    .mealRating(4.7)
                    .build();
            Meal m3 = Meal.builder()
                    .mealName("Pasta")
                    .mealDescription("A delicious pasta")
                    .mealInstructions("Eat it")
                    .mealPrepTime(15)
                    .mealRating(4.6)
                    .build();
            em.getTransaction().begin();
            em.persist(m1);
            em.persist(m2);
            em.persist(m3);
            em.getTransaction().commit();
        }
    }

    public void clearDatabase() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Meal").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE meal_meal_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
