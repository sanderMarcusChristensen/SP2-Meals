package dat;

import dat.config.AppConfig;
import dat.config.Populate;
import dat.utils.ApiProperties;

public class Main {
    public static void main(String[] args) {
        AppConfig.startServer(ApiProperties.PORT);
        Populate.main(args);
        //Test6
    }
}