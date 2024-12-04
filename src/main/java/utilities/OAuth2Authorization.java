package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static utilities.RestAssuredUtils.getToken;
import static utilities.RestAssuredUtils.getValue;

public class OAuth2Authorization {
    private static String clientID = getValue("users", "clientID");
    private static String clientSecret = getValue("users", "clientSecret");
    private static String grant_typeValue = getValue("users", "grant_type");
    private static String usernameValue = getValue("users", "username");
    private static String passwordValue = getValue("users", "password");
    private static String scopeValue = getValue("users", "scope");
    private static String auth_url = BaseSetup.getAuthUrl();

    // Method to obtain access token and update oAuthToken in routes.properties file
    public static String getAccessTokenAndUpdateToken() {
        String storedToken = getToken();
        long tokenLastUpdatedTime = getTokenLastUpdatedTime(); // Custom method to get token timestamp
        long currentTime = System.currentTimeMillis();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime - tokenLastUpdatedTime);

        if (diffInMinutes < 10 && storedToken != null) {
            return storedToken;
        } else {
            return obtainAndRetryAccessToken();
        }
    }

    public static String obtainAndRetryAccessToken() {
        int maxRetries = 3;
        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                // Obtain a new token
                String accessToken = obtainAccessToken();

                // Update the token in properties file
                updateTokenInPropertiesFile(accessToken);

                // Return the newly obtained token
                return accessToken;
            } catch (Exception e) {
                retryCount++;
                System.err.println("Token retrieval failed, retrying... (" + retryCount + "/" + maxRetries + ")");

                // Adding a short delay before retrying
                try {
                    Thread.sleep(2000); // 2 seconds delay between retries
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Token retrieval retries interrupted.", ie);
                }
            }
        }

        throw new RuntimeException("Failed to obtain and update the access token after " + maxRetries + " retries.");
    }

    public static long getTokenLastUpdatedTime() {
        Properties properties = new Properties();
        String propertiesFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\logintoken.properties";

        try (FileInputStream fileInputStream = new FileInputStream(propertiesFilePath)) {
            // Load the properties file
            properties.load(fileInputStream);

            // Get the tokenTimestamp value and convert it to long
            String tokenTimestamp = properties.getProperty("tokenTimestamp");
            if (tokenTimestamp != null) {
                return Long.parseLong(tokenTimestamp); // Convert string timestamp to long
            } else {
                throw new RuntimeException("tokenTimestamp key not found in properties file.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading token properties file.", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid tokenTimestamp format in properties file.", e);
        }
    }



    // Method to perform OAuth 2.0 authorization and obtain access token
    public static String obtainAccessToken() {
        // Ignore SSL certificate validation
        RestAssured.useRelaxedHTTPSValidation();
        // OAuth 2.0 authorization logic here
        // sending a request to the authorization server and extracting the access token
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .auth().preemptive().basic(clientID, clientSecret) // Basic Auth if required
                .formParam("grant_type", grant_typeValue)
                .formParam("username", usernameValue)
                .formParam("password", passwordValue)
                .formParam("scope", scopeValue)
                .when()
                .post(auth_url).then().extract().response();

        //Extract access token from response
        String accessToken = response.jsonPath().getString("access_token");
        System.out.println(response.asPrettyString());
        return accessToken;
    }
    // Method to update oAuthToken and token timestamp in properties file
    private static void updateTokenInPropertiesFile(String accessToken) {
        Properties properties = new Properties();
        String propertiesFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\logintoken.properties";

        try (FileInputStream fileInputStream = new FileInputStream(propertiesFilePath)) {
            // Load existing properties
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return; // If there's an error reading the file, don't proceed
        }

        // Modify properties in memory
        properties.setProperty("oAuthToken", accessToken);
        properties.setProperty("tokenTimestamp", String.valueOf(System.currentTimeMillis()));

        // Write updated properties back to file in one step
        try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath)) {
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

