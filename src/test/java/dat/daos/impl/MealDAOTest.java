package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import org.junit.jupiter.api.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MealDAOTest {

    private static EntityManagerFactory emf;
    private static MealDAO mealDAO;

    private MealDTO mDTO1, mDTO2, mDTO3;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        mealDAO = MealDAO.getInstance(emf);
    }

    @BeforeEach
    void setup () {

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
            // PASTAAA
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
            mDTO1 = new MealDTO(m1);
            mDTO2 = new MealDTO(m2);
            mDTO3 = new MealDTO(m3);

            mealDAO.create(mDTO1);
            mealDAO.create(mDTO2);
            mealDAO.create(mDTO3);
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Meal").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE meal_meal_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void create() {
        Meal m4 = Meal.builder()
                .mealName("Not Pasta")
                .mealDescription("Not A delicious pasta")
                .mealInstructions("Cook pasta, mix with sauce")
                .mealPrepTime(20)
                .mealRating(5.1)
                .build();
        m4.setIngredients(List.of(
                new Ingredients("Spaghetti", "200g"),
                new Ingredients("Tomato sauce", "100ml"),
                new Ingredients("Parmesan cheese", "50g")
        ));
        MealDTO mDTO4 = new MealDTO(m4);

        MealDTO actual = mealDAO.create(mDTO4);
        MealDTO expected = mealDAO.read(4);

        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Get Meal by ID")
    void read() {
        MealDTO expected = mealDAO.create(mDTO1);
        MealDTO actual = mealDAO.read(expected.getMealId());

        Assertions.assertEquals(expected, actual);
    }


    @Test
    void readAll() {
        int actual = mealDAO.readAll().size();
        int expected = 3;

        assertEquals(expected, actual);
    }


    @Test
    void update() {
        MealDTO mDTO = mealDAO.read(1);
        Meal newMeal = Meal.builder()
                .mealName("Vegan Burger")
                .mealDescription("A disgusting burger")
                .mealInstructions("Grill tofu, assemble burger")
                .mealPrepTime(20)
                .mealRating(2.9)
                .build();
        mDTO.setMealName(newMeal.getMealName());
        mealDAO.update(1, mDTO);

        String expected = "Vegan Burger";
        String actual = mealDAO.read(mDTO.getMealId()).getMealName();

        assertEquals(expected, actual);
    }


    @Test
    void delete() {
        int numberOf = mealDAO.readAll().size();
        mealDAO.delete(2);

        int expected = numberOf - 1;
        int actual = mealDAO.readAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void maxPrepTime() {
        int actual = mealDAO.maxPrepTime(15).size();
        int expected = 2;

        assertEquals(expected, actual);
    }
}