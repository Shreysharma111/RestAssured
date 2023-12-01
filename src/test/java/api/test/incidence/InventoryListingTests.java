package api.test.incidence;

import api.endpoints.UserEndPoints2;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static api.utilities.reporting.Setup.logApiDetails;
import static api.utilities.reporting.Setup.logResultAndDetails;

public class InventoryListingTests {

    private final Logger logger = LogManager.getLogger(this.getClass());
    @Test(priority = 1)
    public void testInventoryListing() {
        Response response = UserEndPoints2.inventoryListing();
// Verify keys and log in the report
        logApiDetails(response);
        logResultAndDetails(response);
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}
