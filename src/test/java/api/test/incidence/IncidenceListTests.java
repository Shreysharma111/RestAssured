package api.test.incidence;

import api.endpoints.UserEndPoints2;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class IncidenceListTests {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Test(dependsOnMethods = "testLoginDr", alwaysRun = true)
    public void testIncidenceList() {
        Response response = UserEndPoints2.incidenceList();

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
