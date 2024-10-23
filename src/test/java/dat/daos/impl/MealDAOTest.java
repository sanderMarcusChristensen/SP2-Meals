package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MealDAOTest {

    private static EntityManagerFactory emf;
    private static MealDAO mealDAO;
    private List<MealDTO> mealList;

    private MealDTO m1, m2, m3;

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

        MealDTO mDTO1 = new MealDTO(m1);
        MealDTO mDTO2 = new MealDTO(m2);
        MealDTO mDTO3 = new MealDTO(m3);

        mealDAO.create(mDTO1);
        mealDAO.create(mDTO2);
        mealDAO.create(mDTO3);

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
    void read() {

    }

    @Test
    void readAll() {
    }


    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void maxPrepTime() {
    }
}