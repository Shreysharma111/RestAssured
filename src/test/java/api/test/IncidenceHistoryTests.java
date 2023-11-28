package api.test;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class IncidenceHistoryTests {

    private int assetId;
    private Logger logger = LogManager.getLogger(this.getClass());
    @BeforeClass
    public void setupData() {
        ResourceBundle incDet = ResourceBundle.getBundle("incidenceHistory");
        assetId = Integer.parseInt(incDet.getString("assetId"));

    }
    @Test(priority = 1)
    public void testIncidenceHistory() {
        Response response = UserEndPoints.incidenceHistory(assetId);
// Verify keys in the response body
        response.then()
                .assertThat()
                .statusCode(200);
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
