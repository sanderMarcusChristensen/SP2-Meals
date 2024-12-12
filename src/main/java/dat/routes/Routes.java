package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final MealRoute mealRoutes = new MealRoute();
    private final IngredientsRoute ingredientsRoutes = new IngredientsRoute();
    private final UserRoutes userRoutes = new UserRoutes();

    public EndpointGroup getApiRoutes() {

        return () -> {
            path("/meals", mealRoutes.getMealRoutes());
            path("/ingredients", ingredientsRoutes.getIngredientsRoutes());
            path("/users",userRoutes.getUserRoutes());
        };
    }
}