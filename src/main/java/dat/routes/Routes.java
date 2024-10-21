package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final MealRoute mealRoutes = new MealRoute();

    public EndpointGroup getApiRoutes() {

        return () -> {
            path("/meal", mealRoutes.getMealRoutes());
        };
    }
}