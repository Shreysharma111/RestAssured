package api.test;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ReporterResolverTests {
    private int incidenceId;
    private Logger logger = LogManager.getLogger(this.getClass());
    @BeforeClass
    public void setupData() {
        ResourceBundle incDet = ResourceBundle.getBundle("resolveIncidence");
        incidenceId = Integer.parseInt(incDet.getString("incidenceId"));

    }
    @Test(priority = 1)
    public void testReporterResolver() {
        Response response = UserEndPoints.reporterResolverDetails(incidenceId);
// Verify keys in the response body
        response.then()
                .assertThat()
                .statusCode(200);
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
