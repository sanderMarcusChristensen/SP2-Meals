package dat.routes.meals;

import dat.config.AppConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.MealDAO;
import dat.dtos.IngredientsDTO;
import dat.dtos.MealDTO;
import dat.entities.Meal;
import dat.utils.ApiProperties;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.hamcrest.CoreMatchers.equalTo;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MealRouteTest {
    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static MealDAO mealDAO = MealDAO.getInstance(emf);
    private static Populator populator = new Populator(emf, mealDAO);
    private static String BASE_URL = "http://localhost:7007/api";

    private static Meal m1, m2, m3;
    private static List<Meal> meals;

    private String jwtTokenUser;
    private String jwtTokenAdmin;

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
    @DisplayName("Test get all meals")
    void getAllMeals() {
        Meal[] mealsRequest =
                given()
                        .when()
                        .get(BASE_URL + "/meals")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(Meal[].class);
        assertThat(mealsRequest, arrayContainingInAnyOrder(m1, m2, m3));
    }

    @Test
    @DisplayName("Test get single meal")
    void getMeal() {
        Meal meal =
                given()
                        .when()
                        .get(BASE_URL + "/meals/" + m1.getMealId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(Meal.class);
        assertThat(m1, samePropertyValuesAs(meal));
    }

    @Test
    @DisplayName("Test create meal")
    void createMeal() {
        MealDTO mealDTO = new MealDTO("Sushi", "A delicious sushi", "Eat it", 30, 4.8);
        mealDTO.setIngredients(List.of(new IngredientsDTO("Rice", "200g"), new IngredientsDTO("Salmon", "100g")));
        Meal meal =
                given()
                        .header("Authorization", "Bearer " + jwtTokenAdmin)
                        .contentType("application/json")
                        .body(mealDTO)
                        .when()
                        .post(BASE_URL + "/meals")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .as(Meal.class);
        assertThat(meal.getMealName(), equalTo(mealDTO.getMealName()));
    }

    @Test
    @DisplayName("Test update meal")
    void updateMeal() {
        assertThat(m1.getMealName(), equalTo("Burger"));
        MealDTO mealDTO = new MealDTO("Sushi", "A delicious sushi", "Eat it", 30, 4.8);
        mealDTO.setIngredients(List.of(new IngredientsDTO("Rice", "200g"), new IngredientsDTO("Salmon", "100g")));
        Meal meal =
                given()
                        .header("Authorization", "Bearer " + jwtTokenAdmin)
                        .contentType("application/json")
                        .body(mealDTO)
                        .when()
                        .put(BASE_URL + "/meals/" + m1.getMealId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(Meal.class);
        assertThat(meal.getMealName(), equalTo(mealDTO.getMealName()));
    }

    @Test
    @DisplayName("Test get max prep time")
    void getMaxPrepTime() {
        Meal[] mealsRequest =
                given()
                        .when()
                        .get(BASE_URL + "/meals/prepTime/15")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(Meal[].class);
        assertThat(mealsRequest, arrayWithSize(2));
        assertThat(mealsRequest, arrayContainingInAnyOrder(m1, m2));
        for (Meal meal : mealsRequest) {
            assertThat(meal.getMealPrepTime(), lessThanOrEqualTo(15.0));
        }
    }

    @Test
    @DisplayName("Test delete meal")
    void deleteMeal() {
        given()
                .header("Authorization", "Bearer " + jwtTokenAdmin)
                .when()
                .get(BASE_URL + "/meals/" + m1.getMealId())
                .then()
                .log().all()
                .statusCode(200);
        assertThat(meals, hasItem(m1));

        //First deletion succeed
        given()
                .when()
                .delete(BASE_URL + "/meals/" + m1.getMealId())
                .then()
                .statusCode(204);

        //Try to get the deleted meal but fails with error 400 because it does not exist
        given()
                .when()
                .get(BASE_URL + "/meals/" + m1.getMealId())
                .then()
                .log().all()
                .statusCode(400);
    }
}