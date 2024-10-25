package dat.routes;


import dat.controller.impl.IngredientsController;
import dat.security.utils.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class IngredientsRoute {

    private final IngredientsController ingredientsController = new IngredientsController();

    protected EndpointGroup getIngredientsRoutes() {
        return () -> {

            post("/", ingredientsController::create, Role.ANYONE);
            get("/", ingredientsController::readAll, Role.ANYONE);
            get("/{id}", ingredientsController::read, Role.ANYONE);
            put("/{id}", ingredientsController::update, Role.ANYONE);
            delete("/{id}", ingredientsController::delete, Role.ANYONE);

        };
    }


}
