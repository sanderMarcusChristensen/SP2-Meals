package dat.config;

import dat.entities.Meal;
import dat.entities.Ingredients;
import dat.security.entities.Role;
import dat.security.entities.User;
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

           for(User u : getUsers()){
               em.persist(u);
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
        Meal meal1 = new Meal("Spaghetti Bolognese", "A hearty pasta with a rich meat sauce, made from ground beef, tomatoes, and herbs.",
                "1. Boil a large pot of water with salt. Cook spaghetti according to package instructions until al dente. Drain and set aside.\n" +
                        "2. In a pan, heat olive oil over medium heat. Add chopped onions and minced garlic, and sauté until soft and fragrant.\n" +
                        "3. Add the ground beef to the pan, breaking it apart with a spoon. Cook until browned and no longer pink.\n" +
                        "4. Pour in the tomato sauce, stir to combine, and let it simmer for 20 minutes. Add salt, pepper, and herbs to taste.\n" +
                        "5. Serve the sauce over the cooked spaghetti and garnish with fresh basil or Parmesan, if desired.", 45.0, 4.5);
        meal1.setIngredients(ingredients1);

        // Chicken Caesar Salad
        List<Ingredients> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredients("Romaine lettuce", "1 head"));
        ingredients2.add(new Ingredients("Grilled chicken breast", "200g"));
        ingredients2.add(new Ingredients("Caesar dressing", "100ml"));
        ingredients2.add(new Ingredients("Parmesan cheese", "50g"));
        ingredients2.add(new Ingredients("Croutons", "50g"));
        Meal meal2 = new Meal("Chicken Caesar Salad", "Fresh romaine lettuce, grilled chicken, parmesan cheese, and croutons, tossed in creamy Caesar dressing.",
                "1. Wash and dry the romaine lettuce. Tear it into bite-sized pieces and place in a large bowl.\n" +
                        "2. Grill chicken breasts until cooked through, about 6-7 minutes per side. Slice the chicken into thin strips.\n" +
                        "3. Add the sliced chicken, croutons, and Parmesan cheese to the lettuce.\n" +
                        "4. Drizzle Caesar dressing over the salad and toss to coat evenly. Serve immediately.", 25.0, 4.2);
        meal2.setIngredients(ingredients2);

        // Beef Stroganoff
        List<Ingredients> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredients("Beef sirloin", "300g"));
        ingredients3.add(new Ingredients("Mushrooms", "150g"));
        ingredients3.add(new Ingredients("Onion", "1 medium"));
        ingredients3.add(new Ingredients("Sour cream", "100ml"));
        ingredients3.add(new Ingredients("Butter", "2 tbsp"));
        Meal meal3 = new Meal("Beef Stroganoff", "Tender beef in a creamy mushroom sauce, served over egg noodles or rice.",
                "1. Slice the beef sirloin into thin strips.\n" +
                        "2. In a large pan, melt the butter over medium heat. Add the onions and cook until softened, about 5 minutes.\n" +
                        "3. Add the sliced mushrooms and cook until they release their moisture and become golden brown.\n" +
                        "4. Add the beef strips to the pan and cook until browned, about 3-4 minutes.\n" +
                        "5. Pour in the sour cream and stir to combine. Let the mixture simmer for 5-7 minutes until the sauce thickens.\n" +
                        "6. Serve the beef stroganoff over cooked egg noodles or rice, garnished with parsley.", 50.0, 4.8);
        meal3.setIngredients(ingredients3);

        // Vegetable Stir-fry
        List<Ingredients> ingredients4 = new ArrayList<>();
        ingredients4.add(new Ingredients("Broccoli", "100g"));
        ingredients4.add(new Ingredients("Carrot", "1 medium"));
        ingredients4.add(new Ingredients("Bell pepper", "1 medium"));
        ingredients4.add(new Ingredients("Soy sauce", "50ml"));
        ingredients4.add(new Ingredients("Garlic", "2 cloves"));
        Meal meal4 = new Meal("Vegetable Stir-fry", "A colorful mix of stir-fried vegetables, seasoned with soy sauce for a savory taste.",
                "1. Wash and chop the broccoli into small florets, slice the carrot into thin rounds, and chop the bell pepper into strips.\n" +
                        "2. Heat a wok or large pan over medium-high heat. Add a small amount of oil and sauté minced garlic until fragrant.\n" +
                        "3. Add the carrots and bell pepper to the pan and cook for 3-4 minutes.\n" +
                        "4. Add the broccoli and continue to stir-fry for another 5 minutes, until the vegetables are tender-crisp.\n" +
                        "5. Pour in the soy sauce and stir well to coat the vegetables. Cook for an additional 2 minutes and serve hot.", 30.0, 4.3);
        meal4.setIngredients(ingredients4);

        // Margherita Pizza
        List<Ingredients> ingredients5 = new ArrayList<>();
        ingredients5.add(new Ingredients("Pizza dough", "1 ball"));
        ingredients5.add(new Ingredients("Tomato sauce", "100ml"));
        ingredients5.add(new Ingredients("Mozzarella cheese", "150g"));
        ingredients5.add(new Ingredients("Fresh basil", "10 leaves"));
        ingredients5.add(new Ingredients("Olive oil", "2 tbsp"));
        Meal meal5 = new Meal("Margherita Pizza", "A classic pizza topped with tomato sauce, mozzarella, and fresh basil.",
                "1. Preheat the oven to 220°C (425°F).\n" +
                        "2. Roll out the pizza dough on a floured surface to your desired thickness.\n" +
                        "3. Spread the tomato sauce evenly over the dough, leaving a small border around the edges.\n" +
                        "4. Tear the mozzarella cheese into pieces and scatter it over the sauce.\n" +
                        "5. Bake the pizza for 12-15 minutes, or until the crust is golden and the cheese is bubbly.\n" +
                        "6. Remove from the oven and top with fresh basil leaves. Drizzle with olive oil before serving.", 60.0, 4.7);
        meal5.setIngredients(ingredients5);

        // Chocolate Cake
        List<Ingredients> ingredients6 = new ArrayList<>();
        ingredients6.add(new Ingredients("Flour", "200g"));
        ingredients6.add(new Ingredients("Cocoa powder", "50g"));
        ingredients6.add(new Ingredients("Sugar", "200g"));
        ingredients6.add(new Ingredients("Butter", "100g"));
        ingredients6.add(new Ingredients("Eggs", "2 large"));
        Meal meal6 = new Meal("Chocolate Cake", "A rich and moist chocolate cake, perfect for dessert or special occasions.",
                "1. Preheat the oven to 180°C (350°F) and grease a cake pan.\n" +
                        "2. In a large bowl, sift together the flour, cocoa powder, and sugar.\n" +
                        "3. In another bowl, whisk together the melted butter and eggs. Add the dry ingredients to the wet ingredients and mix until combined.\n" +
                        "4. Pour the batter into the prepared pan and bake for 30-35 minutes or until a toothpick inserted comes out clean.\n" +
                        "5. Let the cake cool before frosting or serving.", 90.0, 4.9);
        meal6.setIngredients(ingredients6);

        // Grilled Salmon
        List<Ingredients> ingredients7 = new ArrayList<>();
        ingredients7.add(new Ingredients("Salmon fillets", "2 fillets"));
        ingredients7.add(new Ingredients("Lemon", "1 medium"));
        ingredients7.add(new Ingredients("Olive oil", "2 tbsp"));
        ingredients7.add(new Ingredients("Garlic", "2 cloves"));
        ingredients7.add(new Ingredients("Fresh herbs", "1 tbsp"));
        Meal meal7 = new Meal("Gay Salmon", "Deliciously grilled salmon fillets with a light lemon and herb seasoning.",
                "1. Preheat the grill to medium heat.\n" +
                        "2. Brush the salmon fillets with olive oil and season with minced garlic, lemon juice, and fresh herbs.\n" +
                        "3. Grill the salmon for 5-7 minutes per side, depending on thickness, until the fish is cooked through and flakes easily.\n" +
                        "4. Serve the salmon with your choice of side dishes, such as rice or grilled vegetables.", 20.0, 4.6);
        meal7.setIngredients(ingredients7);

        // Returning all meals as a set
        Set<Meal> mealSet = new HashSet<>();
        mealSet.add(meal1);
        mealSet.add(meal2);
        mealSet.add(meal3);
        mealSet.add(meal4);
        mealSet.add(meal5);
        mealSet.add(meal6);
        mealSet.add(meal7);

        // Add 20 test meals
        //Delete this code block if you want to remove the test meals
        for (int i = 1; i <= 20; i++) {
            List<Ingredients> testIngredients = new ArrayList<>();
            testIngredients.add(new Ingredients("Test Ingredient " + i, i + "g"));
            testIngredients.add(new Ingredients("Extra Ingredient " + i, (i * 2) + "g"));

            Meal testMeal = new Meal(
                    "Test Meal " + i,
                    "This is a test meal description for Test Meal " + i + ".",
                    "1. Prepare Test Meal " + i + " with simple steps.\n2. Test cooking it for testing purposes.\n3. Serve Test Meal " + i + " hot.",
                    (double) (10 + i), // Random preparation time
                    3.0 + (i % 5) * 0.1 // Random rating
            );
            testMeal.setIngredients(testIngredients);
            mealSet.add(testMeal);
        }
        //End of test meals

        return mealSet;
    }

    private static Set<User> getUsers(){

        Set<User> userSet = new HashSet<>();

        //Roles
        Role admin = new Role("admin");
        Role user = new Role("user");

        // Set's with Roles
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(admin);

        Set<Role> userRole = new HashSet<>();
        userRole.add(user);

        //Users
        User chad = new User("Chad","admin");
        chad.setRoles(adminRole);

        User Sander = new User("Sander","admin");
        Sander.setRoles(adminRole);

        User Mateen = new User("Mateen","admin");
        Mateen.setRoles(adminRole);

        User Marcus = new User("Marcus","admin");
        Marcus.setRoles(adminRole);

        User Jon = new User("Jon","123");
        Jon.setRoles(userRole);

        User Thomas = new User("Thomas","123");
        Thomas.setRoles(userRole);

        User Thor = new User("Thor","123");
        Thor.setRoles(userRole);

        User Jens = new User("Jens","123");
        Jens.setRoles(userRole);

        User Bingo = new User("Bingo","123");
        Bingo.setRoles(userRole);

        userSet.add(chad);
        userSet.add(Sander);
        userSet.add(Mateen);
        userSet.add(Marcus);
        userSet.add(Jon);
        userSet.add(Thomas);
        userSet.add(Thor);
        userSet.add(Jens);
        userSet.add(Bingo);

        return userSet;

    }
}

