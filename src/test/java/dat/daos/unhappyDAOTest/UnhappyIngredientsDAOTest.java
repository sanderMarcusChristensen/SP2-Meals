package dat.daos.unhappyDAOTest;

import dat.config.HibernateConfig;
import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import dat.routes.ingredients.PopulateIngredientsForTest;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnhappyIngredientsDAOTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static IngredientsDAO dao;
    private static PopulateIngredientsForTest populator = new PopulateIngredientsForTest(dao,emf);

    private static IngredientsDTO i1, i2, i3,i4;
    private static List<IngredientsDTO> ingredientsDTOS;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        dao = IngredientsDAO.getInstance(emf);
    }


    @BeforeEach
    void setUp() {
        PopulateIngredientsForTest populator = new PopulateIngredientsForTest(dao,emf);
        populator.populateIngredientsInDatabase();
        ingredientsDTOS = dao.readAll();
        i1 = ingredientsDTOS.get(0);
        i2 = ingredientsDTOS.get(1);
        i3 = ingredientsDTOS.get(2);
        i4 = ingredientsDTOS.get(3);

    }

    @AfterEach
    void tearDown() {
        populator.cleanUpIngredients();
    }

    @Test
    @DisplayName("Attempt to delete a non-existent ingredient")
    void deleteNonExistent() {
        int nonExistentId = -1;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.delete(nonExistentId);
        });
        assertThat(exception.getMessage(), containsString("Something went wrong trying to delete an ingredient"));
    }

    @Test
    @DisplayName("Attempt to create an ingredient with null name")
    void createWithNullName() {
        IngredientsDTO invalidIngredient = new IngredientsDTO(null, "1 unit");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.create(invalidIngredient);
        });
        assertThat(exception.getMessage(), containsString("Ingredient name is required"));
    }

    @Test
    @DisplayName("Attempt to create an ingredient with null quantity")
    void createWithNullQuantity() {
        IngredientsDTO invalidIngredient = new IngredientsDTO("Salt",null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.create(invalidIngredient);
        });
        assertThat(exception.getMessage(), containsString("Quantity is required"));
    }

    @Test
    @DisplayName("Attempt to get an id that's not in database")
    void getIdNotInDatabase(){
        int nonExistentId = -1; // Assuming this ID doesn't exist
        IngredientsDTO ingredient = dao.read(nonExistentId);
        assertThat(ingredient, is(nullValue()));
    }

}



