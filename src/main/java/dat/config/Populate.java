package dat.config;

import dat.entities.Meal;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("meals");

        Set<Meal> meals = getMeals(); // Get 6 meals

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persist 6 meals
            for (Meal meal : meals) {
                em.persist(meal);
            }

            em.getTransaction().commit();
            em.close();
        }
    }

    @NotNull
    private static Set<Meal> getMeals() {
        Meal meal1 = new Meal("Spaghetti Bolognese", "Classic Italian pasta with meat sauce", "Cook pasta, prepare sauce", 45.0, 4.5);
        Meal meal2 = new Meal("Chicken Caesar Salad", "Salad with grilled chicken and Caesar dressing", "Grill chicken, toss salad", 25.0, 4.2);
        Meal meal3 = new Meal("Beef Stroganoff", "Beef in a creamy mushroom sauce", "Cook beef, make sauce", 50.0, 4.8);
        Meal meal4 = new Meal("Vegetable Stir-fry", "Mixed vegetables stir-fried with soy sauce", "Chop veggies, stir-fry", 30.0, 4.3);
        Meal meal5 = new Meal("Margherita Pizza", "Classic pizza with tomato, mozzarella, and basil", "Make dough, bake pizza", 60.0, 4.7);
        Meal meal6 = new Meal("Chocolate Cake", "Rich chocolate dessert", "Bake cake, prepare frosting", 90.0, 4.9);

        Meal[] mealArray = {meal1, meal2, meal3, meal4, meal5, meal6};
        return Set.of(mealArray);
    }
}

