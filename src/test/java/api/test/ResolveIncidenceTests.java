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

import static api.utilities.reporting.Setup.logApiDetails;
import static api.utilities.reporting.Setup.logResultAndDetails;


public class ResolveIncidenceTests {
    ResolveIncidence resolvePayload;

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
        Response response = UserEndPoints.resolveIncidence(resolvePayload);
// Verify keys and log in the report
        logApiDetails(response);
        logResultAndDetails(response);;
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}