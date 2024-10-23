package dat.config;

import dat.entities.Ingredients;
import dat.entities.Meal;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("meals");

        Set<Meal> meals = getMeals(); // Get 6 meals
        Set<Ingredients> ingredients = getIngredients(); // Get 20 ingredients

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persist 6 meals
            for (Meal meal : meals) {
                em.persist(meal);
            }

            for (Ingredients ingredient : ingredients) {
                em.persist(ingredient);
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

    @NotNull
    private static Set<Ingredients> getIngredients() {
        Ingredients i1 = new Ingredients("Pasta", "200 grams");
        Ingredients i2 = new Ingredients("Tomato", "6");
        Ingredients i3 = new Ingredients("Carrots", "3");
        Ingredients i4 = new Ingredients("Ground beef", "500 grams");
        Ingredients i5 = new Ingredients("Onion", "1");
        Ingredients i6 = new Ingredients("Garlic", "4 cloves");
        Ingredients i7 = new Ingredients("Olive oil", "2 tablespoons");
        Ingredients i8 = new Ingredients("Parmesan cheese", "100 grams");
        Ingredients i9 = new Ingredients("Basil", "10 leaves");
        Ingredients i10 = new Ingredients("Salt", "1 teaspoon");
        Ingredients i11 = new Ingredients("Pepper", "1/2 teaspoon");
        Ingredients i12 = new Ingredients("Red wine", "100 ml");
        Ingredients i13 = new Ingredients("Mushrooms", "150 grams");
        Ingredients i14 = new Ingredients("Tomato paste", "3 tablespoons");
        Ingredients i15 = new Ingredients("Oregano", "1 teaspoon");
        Ingredients i16 = new Ingredients("Rice", "250 grams");
        Ingredients i17 = new Ingredients("Bay leaf", "2 leaves");
        Ingredients i18 = new Ingredients("Butter", "50 grams");
        Ingredients i19 = new Ingredients("Chicken broth", "500 ml");
        Ingredients i20 = new Ingredients("Bell pepper", "1");

        Ingredients[] ingredientsArray = {
                i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20
        };
        return Set.of(ingredientsArray);
    }


}

