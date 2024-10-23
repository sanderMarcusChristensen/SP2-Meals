package dat.routes;

import dat.PopulateIngredientsForTest;
import dat.config.AppConfig;
import dat.config.HibernateConfig;
import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientsRouteTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7007/api/ingredients";
    private static IngredientsDAO dao = new IngredientsDAO(emf);
    private static PopulateIngredientsForTest populateIngredientsForTest =new PopulateIngredientsForTest(dao,emf);

    private static IngredientsDTO i1,i2,i3,i4;
    private static List<IngredientsDTO> ingredientsDTOList;

    @BeforeAll
    static void init() {
        HibernateConfig.getEntityManagerFactoryForTest(); // Set the test environment
        AppConfig.startServer(); // Start the server using AppConfig
        HibernateConfig.setTest(true);
    }


    @BeforeEach
    void setUp() {
        ingredientsDTOList = populateIngredientsForTest.populateIngredientsInDatabase();
        i1 = ingredientsDTOList.get(0);
        i2 = ingredientsDTOList.get(1);
        i3 = ingredientsDTOList.get(2);
        i4 = ingredientsDTOList.get(3);
    }

    @AfterEach
    void tearDown() {
        populateIngredientsForTest.cleanUpIngredients();
    }

    @Test
    @DisplayName("Get all the ingredients in the database")
    void testGetAllTheIngredientsInTheDatabase() {
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

    @Test
    @DisplayName("Get one Ingredient by it's id")
    void testAnIngredientByTheId() {
        IngredientsDTO ingredientsDTO =
                given()
                        .when()
                        .get(BASE_URL + "/" + i1.getId()) // Fetch hotel by ID
                        .then()
                        .log().all()
                        .statusCode(200) // Expecting 200 OK
                        .extract()
                        .as(IngredientsDTO.class); // Extract response as HotelDTO

        assertThat(ingredientsDTO, equalTo(i1)); // Assert fetched hotel matches h1
    }

    //works
    @Test
    @DisplayName("Update an Ingredient in the database ")
    void testUpdateAnIngredient() {
        i1.setName("Updated Hotel 1"); // Modify hotel data

        IngredientsDTO updatedHotel =
                given()
                        .contentType("application/json")
                        .body(i1) // Send updated hotel data in request body
                        .when()
                        .put(BASE_URL + "/" + i1.getId()) // PUT to update hotel
                        .then()
                        .log().all()
                        .statusCode(201) // Expecting 200 OK
                        .extract()
                        .as(IngredientsDTO.class); // Extract response as HotelDTO

        assertThat(updatedHotel.getName(), equalTo("Updated Hotel 1")); // Assert updated hotel name
    }

    //works
    @Test
    @DisplayName("Creating a new Ingredient to the database")
    void testCreateIngredient() {
        IngredientsDTO ingredientsDTO = new IngredientsDTO(null,"Salt","1 tablespoon");

        IngredientsDTO created =
                given()
                        .contentType("application/json")
                        .body(ingredientsDTO) // Send new hotel data in request body
                        .when()
                        .post(BASE_URL) // POST to create a new hotel
                        .then()
                        .log().all()
                        .statusCode(201) // Expecting 201 Created
                        .extract()
                        .as(IngredientsDTO.class); // Extract response as HotelDTO

        assertThat(created.getId(), notNullValue()); // Ensure that the new hotel has a non-null ID
        assertThat(created.getName(), equalTo("Salt")); // Assert hotel name
    }


    @Test
    @DisplayName("Deleting a Ingredient from the database")
    void testDeleteHotel() {
        given()
                .when()
                .delete(BASE_URL + "/" + i1.getId())
                .then()
                .log().all()
                .statusCode(204);


        given()
                .when()
                .get(BASE_URL + "/" + i1.getId())
                .then()
                .log().all()
                .statusCode(404);
    }
}










