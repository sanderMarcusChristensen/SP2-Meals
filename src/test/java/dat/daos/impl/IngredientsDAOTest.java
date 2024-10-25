package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.dtos.IngredientsDTO;
import dat.entities.Ingredients;
import dat.routes.ingredients.PopulateIngredientsForTest;
import dat.routes.meals.Populator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class IngredientsDAOTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static IngredientsDAO dao;
    private static PopulateIngredientsForTest populator = new PopulateIngredientsForTest(dao,emf);

    private static IngredientsDTO i1, i2, i3,i4;
    private static List<IngredientsDTO> ingredientsDTOS;

    @BeforeAll
    static void beforeAll() {
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
    @DisplayName("test creat a ingredient")
    void create() {
        assertThat(dao.readAll(),hasSize(4));
        System.out.println(dao.readAll());
        IngredientsDTO i5 = new IngredientsDTO("Strawberry", "6 stk");
        dao.create(i5);

        assertThat(dao.readAll(),hasSize(5));
        System.out.println(dao.readAll());
    }

    @Test
    void update() {
        assertThat(i1.getName(), is("Olive Oil"));
        i1.setName("Strawberry");

        int id = i1.getId();
        dao.update(id,i1);
        assertThat(dao.read(i1.getId()).getName(), is("Strawberry"));
    }

    @Test
    @DisplayName("Get an ingredient by id")
    void getById() {

        Ingredients remadeIngredient = new Ingredients(i1);
        IngredientsDTO ingredients = dao.read(remadeIngredient.getId());
        assertThat(ingredients.getName(), is(remadeIngredient.getName()));
    }

    @Test
    @DisplayName("get all ingredients ")
    void getAll(){
        assertThat(ingredientsDTOS, hasSize(4));
        List<IngredientsDTO> retrievedIngredients = dao.readAll();
        assertThat(retrievedIngredients, hasSize(4));
        assertThat(retrievedIngredients, containsInAnyOrder(i1, i2, i3, i4));
    }

    @Test
    @DisplayName("delete an ingredient")
    void delete() {
        assertThat(dao.readAll(), hasSize(4));
        int id = i1.getId();
        dao.delete(id);
        assertThat(dao.readAll(), hasSize(3));
    }
}