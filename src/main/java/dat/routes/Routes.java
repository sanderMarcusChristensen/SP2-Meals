package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

        // have your other routes like this
//    private final HotelRoutes hotelRoutes = new HotelRoutes();


    public EndpointGroup getApiRoutes() {

        return () -> {
                //get all the routes like this
//            path("/hotel", hotelRoutes.getHotelRoutes());


        };
    }
}
