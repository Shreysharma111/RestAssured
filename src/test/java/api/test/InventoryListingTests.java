package api.test;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class InventoryListingTests {

    private Logger logger = LogManager.getLogger(this.getClass());
    @Test(priority = 1)
    public void testInventoryListing() {
        Response response = UserEndPoints.inventoryListing();
// Verify keys in the response body
        response.then()
                .assertThat()
                .statusCode(200);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}
