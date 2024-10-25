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
import dat.utils.ApiProperties;
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
    }

    @BeforeEach
    void setUp() {
        populator.populateDatabase();
        meals = Meal.toMealList(mealDAO.readAll());
        m1 = meals.get(0);
        m2 = meals.get(1);
        m3 = meals.get(2);

//        List<Role> roles = populator.createRoles();
//        populator.persist(roles);
//
//        List<User> users = populator.createUsers(roles);
//        populator.persist(users);
    }

    @AfterEach
    void tearDown() {
        populator.clearDatabase();
    }

    @AfterAll
    void afterAll() {
        AppConfig.stopServer(app);
    }

    @Test
    @DisplayName("Test endpoint /auth/test")
    void test() {
        given()
                .when()
                .get("/auth/test")
                .then()
                .statusCode(200)
                .body("message", equalTo("Hello from Open Deployment"));
    }

    @Test
    void login() {
        jwtToken =
                given()
                        .contentType("application/json")
                        .body("{\"username\": \"userTest\", \"password\": \"userTest\"}")
                        .post(BASE_URL + "/auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");
    }
}
