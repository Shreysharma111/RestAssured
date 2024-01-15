package api.test.incidence;

import api.endpoints.IncidenceEndPoints;
import api.utilities.reporting.Setup;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class IncidenceListTests {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Test
    public void testIncidenceList() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testIncidenceList" );
        Setup.extentTest.set(test);

        Response response = IncidenceEndPoints.incidenceList();

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
