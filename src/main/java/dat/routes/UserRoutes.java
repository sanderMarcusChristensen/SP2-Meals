package dat.routes;

import dat.controller.impl.UserController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserRoutes {

    private final UserController userController = new UserController();

    protected EndpointGroup getUserRoutes() {
        return () -> {


            get("/", userController::readAll, Role.ANYONE);

        };
    }
}
