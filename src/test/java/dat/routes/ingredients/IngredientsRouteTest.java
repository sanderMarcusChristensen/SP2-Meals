package dat.routes.ingredients;

import dat.config.AppConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IngredientsRouteTest {

    private static Javalin app;
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7007/api/ingredients";
    private static IngredientsDAO dao = IngredientsDAO.getInstance(emf);
    private static PopulateIngredientsForTest populateIngredientsForTest = new PopulateIngredientsForTest(dao,emf);

    private static IngredientsDTO i1,i2,i3,i4;
    private static List<IngredientsDTO> ingredientsDTOList;

    @BeforeAll
    static void init() {
        app = AppConfig.startServer(); // Start the server using AppConfig
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
    void GetAllTheIngredientsInTheDatabase() {
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

       assertThat(array,arrayContainingInAnyOrder(i1,i2,i3,i4));
    }
}