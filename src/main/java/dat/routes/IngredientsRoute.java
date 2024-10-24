package dat.routes;

import dat.config.HibernateConfig;
import dat.controller.impl.IngredientsController;
import dat.daos.impl.IngredientsDAO;
import dat.security.utils.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
