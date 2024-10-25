package dat.routes.security;

import dat.config.AppConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.MealDAO;
import dat.dtos.IngredientsDTO;
import dat.dtos.MealDTO;
import dat.entities.Meal;
import dat.routes.meals.Populator;
import dat.security.daos.SecurityDAO;
import dat.security.entities.Role;
import dat.security.entities.User;
import dat.security.exceptions.ValidationException;
import dat.utils.ApiProperties;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SecurityRouteTest {

    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static MealDAO mealDAO = MealDAO.getInstance(emf);
    private static Populator populator = new Populator(emf, mealDAO);
    private static String BASE_URL = "http://localhost:7007/api";

    private static Meal m1, m2, m3;
    private static List<Meal> meals;

    private String jwtToken;

    @BeforeAll
    void beforeAll() {
        app = AppConfig.startServer(ApiProperties.PORT);
        try {
            populator.createTestUser();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        populator.populateDatabase();
        meals = Meal.toMealList(mealDAO.readAll());
        m1 = meals.get(0);
        m2 = meals.get(1);
        m3 = meals.get(2);
    }

    @AfterEach
    void tearDown() {
        populator.clearDatabase();
    }

    @AfterAll
    void afterAll() {
        AppConfig.stopServer(app);
    }

    //TODO Fix all security related methods, so the tests in securityRouteTest can be run

    @Test
    @DisplayName("Test endpoint /auth/test")
    void test() {
        given()
                .when()
                .get(BASE_URL + "/auth/test")
                .then()
                .statusCode(200)
                .body("msg", equalTo("Hello from Open Deployment"));
    }

    @Test
    void login() {
        UserDTO userDTO = new UserDTO("userTest", "1234");

        given()
                .body(userDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("username", equalTo(userDTO.getUsername()));
    }
}