package dat.routes.ingredients;

import dat.config.AppConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import dat.utils.ApiProperties;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class IngredientsRouteTest {


    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:8008/api/ingredients";
    private static IngredientsDAO dao = IngredientsDAO.getInstance(emf);
    private static PopulateIngredientsForTest populateIngredientsForTest = new PopulateIngredientsForTest(dao, emf);

    private static IngredientsDTO i1, i2, i3, i4;
    private static List<IngredientsDTO> ingredientsDTOList;

    @BeforeAll
    static void init() {
        app = AppConfig.startServer(ApiProperties.PORT); // Start the server using AppConfig
    }

    @BeforeEach
    void setUp() {
        populateIngredientsForTest.populateIngredientsInDatabase();
        ingredientsDTOList = dao.readAll();
        i1 = ingredientsDTOList.get(0);
        i2 = ingredientsDTOList.get(1);
        i3 = ingredientsDTOList.get(2);
        i4 = ingredientsDTOList.get(3);
    }

    @AfterEach
    void tearDown() {
        populateIngredientsForTest.cleanUpIngredients();
    }

    @AfterAll
    static void afterAll() {
        AppConfig.stopServer(app);
    }

    @Test
    @DisplayName("Get all the ingredients in the database")
    void getAllTheIngredientsInTheDatabase() {
        // Verify setup
        assertEquals(4, ingredientsDTOList.size(), "Expected 4 ingredients to be populated.");

        // Call the API to fetch the ingredients
        IngredientsDTO[] array =
                given()
                        .when()
                        .get(BASE_URL)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(IngredientsDTO[].class);

        for (IngredientsDTO ingredientsDTO : array) {
            System.out.println(ingredientsDTO);
        }

        assertThat(array, arrayContainingInAnyOrder(i1, i2, i3, i4));
    }

    @Test
    @DisplayName("Test get single ingredient")
    void getOneIngredientById() {
        IngredientsDTO ingredients =
                given()
                        .when()
                        .get(BASE_URL + "/" + i1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(IngredientsDTO.class);
        assertThat(i1, samePropertyValuesAs(ingredients));
    }

    @Test
    @DisplayName("Creating a new Ingredient to the database")
    void testCreateIngredient() {
        IngredientsDTO ingredientsDTO = new IngredientsDTO( "Salt", "1 tablespoon");

        IngredientsDTO created =
                given()
                        .contentType("application/json")
                        .body(ingredientsDTO)
                        .when()
                        .post(BASE_URL)
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .as(IngredientsDTO.class);

        assertThat(created.getId(), notNullValue());
        assertThat(created.getName(), equalTo("Salt"));
    }

    @Test
    @DisplayName("Update an Ingredient in the database ")
    void testUpdateIngredient() {
        i1.setName("Strawberry");

        IngredientsDTO updatedHotel =
                given()
                        .contentType("application/json")
                        .body(i1)
                        .when()
                        .put(BASE_URL + "/" + i1.getId())
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .as(IngredientsDTO.class);

        assertThat(updatedHotel.getName(), equalTo("Strawberry"));
    }

    @Test
    @DisplayName("Test delete Ingredient")
    void deleteIngredient() {
        // Ensure the ingredient exists before deletion
        given()
                .when()
                .get(BASE_URL + "/" + i1.getId())
                .then()
                .log().all()
                .statusCode(200); // Ingredient exists

        // Perform the deletion
        given()
                .when()
                .delete(BASE_URL + "/" + i1.getId())
                .then()
                .statusCode(204); // Successful deletion

        // Try to get the deleted ingredient and expect 404 Not Found
        given()
                .when()
                .get(BASE_URL + "/" + i1.getId())
                .then()
                .log().all()
                .statusCode(400); // Ingredient should not exist anymore
    }
}