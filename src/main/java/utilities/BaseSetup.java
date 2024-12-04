package utilities;

import lombok.Getter;
import org.testng.annotations.BeforeSuite;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseSetup {

    // Optionally, provide getter methods if you want to access them from non-child classes
    @Getter
    public static String baseUrl;
    @Getter
    public static String authUrl;
    private static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\users.properties";

    @BeforeSuite
    public static void setup() throws IOException {
        // Load properties file
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH);
        properties.load(input);
        input.close();

        // Get the environment from the properties file
        String env = properties.getProperty("environment");

        // Set URLs based on the environment
        switch (env.toLowerCase()) {
            case "qa":
                baseUrl = "https://api-qa.clovedental.in";
                authUrl = "https://auth-qa.clovedental.in/oauth2/token";
                break;
            case "uat":
                baseUrl = "https://api-uat.clovedental.in";
                authUrl = "https://auth-uat.clovedental.in/oauth2/token";
                break;
            case "prod":
                baseUrl = "https://apis.clovedental.in";
                authUrl = "https://auth.clovedental.in/oauth2/token";
                break;
            default:
                throw new IllegalArgumentException("Invalid environment: " + env);
        }

        System.out.println("Environment: " + env);
        System.out.println("Base URL: " + baseUrl);
        System.out.println("Auth URL: " + authUrl);
    }

}
