package dat.security.routes;


import com.fasterxml.jackson.databind.ObjectMapper;
import dat.security.controller.SecurityController;
import dat.security.utils.Utils;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;


import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public class SecurityRoutes {

    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();


    // TODO : SOLVE PROBLEM WITH INJECTING EntityManagerFactory
    private static SecurityController securityController = SecurityController.getInstance();

    public static EndpointGroup getSecurityRoutes() {
        return () -> {
            path("/auth", () -> {
                get("/test", ctx -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from Open")), Role.ANYONE);
                post("/login", securityController.login(), Role.ANYONE);
                post("/register", securityController.register(), Role.ANYONE);
//                post("/authenticate", securityController.authenticate());
//                get("/logout", securityController.logout());
            });
        };
    }

    public static EndpointGroup getSecuredRoutes() {
        return () -> {
            path("/protected", () -> {
                before(securityController.authenticate()); // check if there is a valid token in the header
                get("/user_demo", (ctx) -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from USER Protected")), Role.USER);
                get("/admin_demo", (ctx) -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from ADMIN Protected")), Role.ADMIN);
            });
        };
    }

    public enum Role implements RouteRole {ANYONE, USER, ADMIN}
}
