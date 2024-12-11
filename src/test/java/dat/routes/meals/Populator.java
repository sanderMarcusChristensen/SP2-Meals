package dat.routes.meals;

import dat.config.HibernateConfig;
import dat.daos.impl.MealDAO;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import dat.security.daos.SecurityDAO;
import dat.security.entities.Role;
import dat.security.entities.User;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Populator {

    private static EntityManagerFactory emf;
    private static MealDAO mealDAO;

    public Populator(EntityManagerFactory emf_, MealDAO mealDAO_) {
        this.emf = emf_;
        this.mealDAO = mealDAO_;
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

    //TODO Fix security related method, so the tests in securityRouteTest can be run

    //security
    public void createTestUser() throws ValidationException {
        EntityManagerFactory emf_ = HibernateConfig.getEntityManagerFactoryForTest();
        SecurityDAO securityDAO = SecurityDAO.getInstance(emf_);

        User createdUser = securityDAO.createUser("userTest", "1234");
        UserDTO createdUserDTO = securityDAO.getVerifiedUser(createdUser.getUsername(), createdUser.getPassword());
        securityDAO.addRole(createdUserDTO, "user");
    }


    public void clearDatabase() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createQuery("DELETE FROM Meal").executeUpdate();
            em.createQuery("DELETE FROM Ingredients").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();

            em.getTransaction().commit();
        }
    }
}