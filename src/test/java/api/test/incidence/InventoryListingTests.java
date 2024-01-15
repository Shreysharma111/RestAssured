package api.test.incidence;

import api.endpoints.IncidenceEndPoints;
import api.utilities.reporting.Setup;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class InventoryListingTests {

    private final Logger logger = LogManager.getLogger(this.getClass());
    @Test(priority = 1)
    public void testInventoryListing() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testInventoryListing" );
        Setup.extentTest.set(test);

        Response response = IncidenceEndPoints.inventoryListing();

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}
