package api.test;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class IncidenceDetailsTests {

    private int assetId;
    @BeforeClass
    public void setupData() {
        ResourceBundle incDet = ResourceBundle.getBundle("incidenceDetails");
        assetId = Integer.parseInt(incDet.getString("assetId"));

    }
    @Test(priority = 1)
    public void testIncidenceDetails() {
        int maxRetries = 1; // Set the maximum number of retries
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                Response response = UserEndPoints.incidenceDetails(assetId);

                System.out.println("Response time : " + response.getTime() + "ms");
                break; // Break out of the loop if the request is successful
            } catch (Exception e) {
                int statusCode = ((io.restassured.response.Response) e).getStatusCode();
                if (statusCode == 403) {
                    // Perform test cases of LoginTest.java test class
                    LoginTests loginInstance = new LoginTests();
                    loginInstance.testLoginIT();

                    // Retry the incidenceDetails method
                    retryCount++;
                } else if (statusCode == 500) {
                    System.err.println("A server error occurred: " + e.getMessage());
                    testIncidenceDetails();
                    // Retry the incidenceDetails method
                    retryCount++;
                } else if (statusCode == 404) {
                    System.err.println("Not found error occurred: " + e.getMessage());

                    // Retry the incidenceDetails method
                    retryCount++;
                } else if (statusCode == 200) {

                    // Verified successfully
                    retryCount++;
                } else {
                    throw e; // Re-throw the exception if it's not one of the expected cases
                }
            }
        }
    }
}
