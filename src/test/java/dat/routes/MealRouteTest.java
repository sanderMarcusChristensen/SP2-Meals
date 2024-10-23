package dat.routes;

import dat.config.AppConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.MealDAO;
import dat.dtos.MealDTO;
import dat.entities.Meal;
import dat.util.ApiProperties;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MealRouteTest {
    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7007/api";
    private static MealDAO mealDAO = MealDAO.getInstance(emf);
    private static Populator populator = new Populator(emf, mealDAO);

    private static Meal m1, m2, m3;
    private static List<Meal> meals;

    @BeforeAll
    void beforeAll() {
        app = AppConfig.startServer();
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

    @Test
    @DisplayName("Test get single meal")
    void getMeal() {
        Meal meal =
                given()
                        .when()
                        .get(BASE_URL + "/meals/" + m1.getMealId())
                        .then()
                        .log.all()
                        .statusCode(200)
                        .extract()
                        .as(Meal.class);
        assertThat(m1.getMealName(), equalTo(meal.getMealName()));
    }
}