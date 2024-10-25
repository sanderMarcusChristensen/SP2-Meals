package dat.config;

import dat.entities.Meal;
import dat.entities.Ingredients;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<Meal> meals = getMeals(); // Get meals with ingredients

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persist meals with ingredients
            for (Meal meal : meals) {
                em.persist(meal);
            }

            em.getTransaction().commit();
            em.close();
        }
    }

    @NotNull
    private static Set<Meal> getMeals() {
        // Creating meals with ingredients

        // Spaghetti Bolognese
        List<Ingredients> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredients("Spaghetti", "200g"));
        ingredients1.add(new Ingredients("Ground beef", "300g"));
        ingredients1.add(new Ingredients("Tomato sauce", "200ml"));
        ingredients1.add(new Ingredients("Onion", "1 medium"));
        ingredients1.add(new Ingredients("Garlic", "2 cloves"));
        Meal meal1 = new Meal("Spaghetti Bolognese", "Classic Italian pasta with meat sauce", "Cook pasta, prepare sauce", 45.0, 4.5);
        meal1.setIngredients(ingredients1);

        // Chicken Caesar Salad
        List<Ingredients> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredients("Romaine lettuce", "1 head"));
        ingredients2.add(new Ingredients("Grilled chicken breast", "200g"));
        ingredients2.add(new Ingredients("Caesar dressing", "100ml"));
        ingredients2.add(new Ingredients("Parmesan cheese", "50g"));
        ingredients2.add(new Ingredients("Croutons", "50g"));
        Meal meal2 = new Meal("Chicken Caesar Salad", "Salad with grilled chicken and Caesar dressing", "Grill chicken, toss salad", 25.0, 4.2);
        meal2.setIngredients(ingredients2);

        // Beef Stroganoff
        List<Ingredients> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredients("Beef sirloin", "300g"));
        ingredients3.add(new Ingredients("Mushrooms", "150g"));
        ingredients3.add(new Ingredients("Onion", "1 medium"));
        ingredients3.add(new Ingredients("Sour cream", "100ml"));
        ingredients3.add(new Ingredients("Butter", "2 tbsp"));
        Meal meal3 = new Meal("Beef Stroganoff", "Beef in a creamy mushroom sauce", "Cook beef, make sauce", 50.0, 4.8);
        meal3.setIngredients(ingredients3);

        // Vegetable Stir-fry
        List<Ingredients> ingredients4 = new ArrayList<>();
        ingredients4.add(new Ingredients("Broccoli", "100g"));
        ingredients4.add(new Ingredients("Carrot", "1 medium"));
        ingredients4.add(new Ingredients("Bell pepper", "1 medium"));
        ingredients4.add(new Ingredients("Soy sauce", "50ml"));
        ingredients4.add(new Ingredients("Garlic", "2 cloves"));
        Meal meal4 = new Meal("Vegetable Stir-fry", "Mixed vegetables stir-fried with soy sauce", "Chop veggies, stir-fry", 30.0, 4.3);
        meal4.setIngredients(ingredients4);

        // Margherita Pizza
        List<Ingredients> ingredients5 = new ArrayList<>();
        ingredients5.add(new Ingredients("Pizza dough", "1 ball"));
        ingredients5.add(new Ingredients("Tomato sauce", "100ml"));
        ingredients5.add(new Ingredients("Mozzarella cheese", "150g"));
        ingredients5.add(new Ingredients("Fresh basil", "10 leaves"));
        ingredients5.add(new Ingredients("Olive oil", "2 tbsp"));
        Meal meal5 = new Meal("Margherita Pizza", "Classic pizza with tomato, mozzarella, and basil", "Make dough, bake pizza", 60.0, 4.7);
        meal5.setIngredients(ingredients5);

        // Chocolate Cake
        List<Ingredients> ingredients6 = new ArrayList<>();
        ingredients6.add(new Ingredients("Flour", "200g"));
        ingredients6.add(new Ingredients("Cocoa powder", "50g"));
        ingredients6.add(new Ingredients("Sugar", "200g"));
        ingredients6.add(new Ingredients("Butter", "100g"));
        ingredients6.add(new Ingredients("Eggs", "2 large"));
        Meal meal6 = new Meal("Chocolate Cake", "Rich chocolate dessert", "Bake cake, prepare frosting", 90.0, 4.9);
        meal6.setIngredients(ingredients6);

        // Returning all meals as a set
        Set<Meal> mealSet = new HashSet<>();
        mealSet.add(meal1);
        mealSet.add(meal2);
        mealSet.add(meal3);
        mealSet.add(meal4);
        mealSet.add(meal5);
        mealSet.add(meal6);

        return mealSet;
    }
}
