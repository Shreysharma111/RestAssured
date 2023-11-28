package api.test;

import api.endpoints.UserEndPoints;
import api.payload.Accessories;
import api.payload.Images;
import api.payload.ResolveIncidence;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class ResolveIncidenceTests {
    ResolveIncidence resolvePayload;
    private ExtentTest test;

    private int incidenceId;
    private String resolutionStatus;
    private String isAvailable;
    private String resolverRemark;
    private String accessoryId1;
    private String accessoryName1;
    private String resolvedQuantity1;
    private String accessoryId2;
    private String accessoryName2;
    private String resolvedQuantity2;
    private String status;
    private String image1;
    private String image2;
    int statusCode;

    private Logger logger = LogManager.getLogger(this.getClass());

    @BeforeClass
    public void setupData() {
        resolvePayload = new ResolveIncidence();
        ResourceBundle resolveBundle = ResourceBundle.getBundle("resolveIncidence");
        incidenceId = Integer.parseInt(resolveBundle.getString("incidenceId"));
        resolutionStatus = resolveBundle.getString("resolutionStatus");
        isAvailable = resolveBundle.getString("isAvailable");
        resolverRemark = resolveBundle.getString("resolverRemark");
        accessoryId1 = resolveBundle.getString("accessoryId1");
        accessoryName1 = resolveBundle.getString("accessoryName1");
        resolvedQuantity1 = resolveBundle.getString("resolvedQuantity1");
        accessoryId2 = resolveBundle.getString("accessoryId2");
        accessoryName2 = resolveBundle.getString("accessoryName2");
        resolvedQuantity2 = resolveBundle.getString("resolvedQuantity2");
        status = resolveBundle.getString("status");
        image1 = resolveBundle.getString("image1");
        image2 = resolveBundle.getString("image2");

        // Set values for accessories
        Accessories laptopAccessory = new Accessories();
        laptopAccessory.setId(Integer.parseInt(accessoryId1));
        laptopAccessory.setName(accessoryName1);
        laptopAccessory.setResolvedQuantity(resolvedQuantity1);
        laptopAccessory.setStatus(status);

        Accessories chargerAccessory = new Accessories();
        chargerAccessory.setId(Integer.parseInt(accessoryId2));
        chargerAccessory.setName(accessoryName2);
        chargerAccessory.setResolvedQuantity(resolvedQuantity2);
        chargerAccessory.setStatus(status);

        resolvePayload.setAccessories(List.of(laptopAccessory, chargerAccessory));

        // Set values for images
        Images image = new Images();
        image.setThumbUrl(image1);
        image.setImageUrl(image2);

        resolvePayload.setImages(List.of(image));

        resolvePayload.setIncidenceId(incidenceId);
        resolvePayload.setResolutionStatus(resolutionStatus);
        resolvePayload.setIsAvailable(isAvailable);
        resolvePayload.setResolverRemark(resolverRemark);
    }

    @Test(priority = 1)
    public void testIncidenceResolve() {
        int maxRetries = 1; // Set the maximum number of retries
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                Response response = UserEndPoints.resolveIncidence(resolvePayload);
                statusCode = response.getStatusCode();
                logger.info("Status code : "+response.getStatusCode());
                logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
                break;
            } catch (Exception e) {

                if (statusCode == 403) {
                    // Perform test cases of LoginTest.java test class
                    LoginTests loginInstance = new LoginTests();
                    loginInstance.testLoginIT();

                    // Retry the incidenceDetails method
                    retryCount++;
                } else if (statusCode == 500) {
                    System.out.println("A server error occurred: " + e.getMessage());
                    testIncidenceResolve();
                    // Retry the incidenceResolve method
                    retryCount++;
                } else if (statusCode == 404) {
                    System.out.println("Not found error occurred: " + e.getMessage());

                    // Retry the incidenceResolve method
                    retryCount++;
                } else if (statusCode == 200) {
                    System.out.println("Verified successfully");
                    // Verified successfully
                    retryCount++;
                } else {
                    throw e; // Re-throw the exception if it's not one of the expected cases
                }
            }

        }
    }
}