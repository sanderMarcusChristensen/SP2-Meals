package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.dtos.IngredientsDTO;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import dat.routes.meals.Populator;
import org.junit.jupiter.api.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

class MealDAOTest {

    private static EntityManagerFactory emf;
    private static MealDAO mealDAO;
    private static Populator populator = new Populator(emf, mealDAO);

    private static MealDTO m1, m2, m3;
    private static List<MealDTO> meals;

    private MealDTO mDTO1, mDTO2, mDTO3;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        mealDAO = MealDAO.getInstance(emf);
    }

    @BeforeEach
    void setup () {
        /*
        populator.populateDatabase();
        meals = mealDAO.readAll(); // Directly assign the list of MealDTOs
        m1 = meals.get(0);
        m2 = meals.get(1);
        m3 = meals.get(2);
        */
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
    @DisplayName("Test For Create")
    void create() {
        assertThat(mealDAO.readAll(),hasSize(3));
        MealDTO m4 = new MealDTO("Tacos", "A delicious taco", "Assemble taco", 30, 4.8);
        m4.setIngredients(List.of(new IngredientsDTO("Tortilla", "1"), new IngredientsDTO("Beef", "100g")));
        mealDAO.create(m4);

        assertThat(mealDAO.readAll(), hasSize(4));
    }

    @Test
    @DisplayName("Get Meal By Id")
    void getById(){
        MealDTO expected = mealDAO.create(mDTO1);
        MealDTO actual = mealDAO.read(expected.getMealId());

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    @DisplayName("Get All Meals ")
    void getAll(){
        assertThat(mealDAO.readAll(), hasSize(3));
        List<MealDTO> retrievedMeals = mealDAO.readAll();

        assertThat(retrievedMeals, hasSize(3));
        //assertThat(retrievedMeals, containsInAnyOrder(mDTO1, mDTO2, mDTO3));
    }

    @Test
    @DisplayName("Test For Update")
    void update() {
        /*
        assertThat(mDTO1.getMealName(), is("Burger"));
        mDTO1.setMealName("Vegan Burger");

        mealDAO.update(mDTO1.getMealId(), mDTO1);
        assertThat(mealDAO.read(mDTO1.getMealId()).getMealName(), is("Vegan Burger"));
        */

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
    @DisplayName("Test For Delete")
    void delete() {
        int numberOf = mealDAO.readAll().size();
        mealDAO.delete(2);

        assertThat(mealDAO.readAll(), hasSize(numberOf - 1));
    }

    @Test
    @DisplayName("Test for maxPrepTime")
    void maxPrepTime2() {
        assertThat(mealDAO.maxPrepTime(15), hasSize(2));
    }
}