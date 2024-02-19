package api.endpoints;

import api.utilities.JwtTokenUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class OAuth2Authorization {

    // Method to obtain access token and update oAuthToken in routes.properties file
    public static String getAccessTokenAndUpdateToken() {
        // Perform OAuth 2.0 authorization and obtain access token
        String accessToken = obtainAccessToken();

        // Update oAuthToken in routes.properties file
        updateTokenInPropertiesFile(accessToken);

        // Save the generated token in oAuthToken variable of JwtTokenUtil class
        JwtTokenUtil.setToken(accessToken);

        return accessToken;
    }


    // Method to perform OAuth 2.0 authorization and obtain access token
    public static String obtainAccessToken() {
        // Ignore SSL certificate validation
        RestAssured.useRelaxedHTTPSValidation();
        // OAuth 2.0 authorization logic here
        // sending a request to the authorization server and extracting the access token
        Response response = RestAssured.given().log().all()
                .contentType("application/x-www-form-urlencoded")
                .auth().preemptive().basic("prm1", "prm1") // Basic Auth if required
                .formParam("grant_type", "password")
                .formParam("username", "jai.banipark1")
                .formParam("password", "Clove@123")
                .formParam("scope", "all")
                .when()
                .post("https://auth-qa.clovedental.in/oauth2/token").then().extract().response();

       //Extract access token from response
        String accessToken = response.jsonPath().getString("access_token");
        return accessToken;
    }
    // Method to update oAuthToken in routes.properties file
    private static void updateTokenInPropertiesFile(String accessToken) {
        Properties properties = new Properties();
        try {
            String propertiesFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\logintoken.properties";
            FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
            properties.load(fileInputStream);
            fileInputStream.close();

            // Update the oAuthToken property
            if (!properties.containsKey("oAuthToken")) {
                // If it doesn't, add a new token property with the value of the generated token
                properties.setProperty("oAuthToken", accessToken);
            } else {
                // If it does, update the token property value
                properties.setProperty("oAuthToken", accessToken);
            }

            FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
            properties.store(fileOutputStream, null);
            fileOutputStream.close();
        }
          catch (FileNotFoundException e) {
            System.err.println("Properties file not found: " + e.getMessage());
            // Handle the case where the properties file is not found
        } catch (IOException e) {
            System.err.println("Error reading/writing properties file: " + e.getMessage());
            // Handle other IO exceptions
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            // Handle any other unexpected exceptions
        }
    }

//    public static void main(String[] args) {
//        // Call the method to obtain access token and update oAuthToken
//        String accessToken = getAccessTokenAndUpdateToken();
//        System.out.println("Access Token: " + accessToken);
//    }
}
