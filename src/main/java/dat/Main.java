package dat;

import dat.config.AppConfig;
import dat.config.Populate;

public class Main {
    public static void main(String[] args) {
        AppConfig.startServer();
        Populate.main(args);
    }
}