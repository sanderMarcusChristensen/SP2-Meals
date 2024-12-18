package dat.routes;

import dat.controller.impl.MealController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class MealRoute {

    private final MealController mealController = new MealController();

    protected EndpointGroup getMealRoutes() {
        return () -> {
            //CRUD
            post("/", mealController::create, Role.ANYONE);
            get("/", mealController::readAll, Role.ANYONE);
            get("/{id}", mealController::read, Role.ANYONE);
            put("/{id}", mealController::update, Role.ANYONE);
            delete("/{id}", mealController::delete, Role.ANYONE);

            //Custom
            get("/prepTime/{time}", mealController::maxPrepTime, Role.ANYONE);
        };
    }
}