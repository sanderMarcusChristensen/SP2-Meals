package dat.routes;

import dat.daos.impl.MealDAO;
import dat.dtos.MealDTO;
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
            em.createNativeQuery("ALTER SEQUENCE meal_meal_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
