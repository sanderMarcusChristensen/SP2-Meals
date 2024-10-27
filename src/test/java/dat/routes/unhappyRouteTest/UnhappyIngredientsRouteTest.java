package dat.routes.unhappyRouteTest;

import dat.config.AppConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import dat.routes.ingredients.PopulateIngredientsForTest;
import dat.utils.ApiProperties;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UnhappyIngredientsRouteTest {

    private static Javalin app;
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7007/api/ingredients";
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
    @DisplayName("Fail to fetch an ingredient that does not exist")
    void failToFetchNonExistentIngredient() {
        int nonExistentId = 9999;

        given()
                .when()
                .get(BASE_URL + "/" + nonExistentId)
                .then()
                .log().all()
                .statusCode(400);
                //.body("error", containsString("Not a valid id"));
    }

    @Test
    @DisplayName("Fail to delete an ingredient that does not exist")
    void failToDeleteNonExistentIngredient() {
        int nonExistentId = 9999;

        given()
                .when()
                .delete(BASE_URL + "/" + nonExistentId)
                .then()
                .log().all()
                .statusCode(400);

    }


}
