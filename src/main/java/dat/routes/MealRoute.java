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
            post("/", mealController::create, Role.ADMIN);
            get("/", mealController::readAll, Role.ANYONE);
            get("/{id}", mealController::read, Role.ANYONE);
            put("/{id}", mealController::update, Role.ADMIN);
            delete("/{id}", mealController::delete, Role.ADMIN);

            //Custom
            get("/prepTime/{time}", mealController::maxPrepTime, Role.ANYONE);
        };
    }
}