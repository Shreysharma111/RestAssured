package api.test.incidence;

import api.endpoints.IncidenceEndPoints;
import api.utilities.reporting.Setup;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class IncidenceDetailsTests {

    private int assetId, incidenceId;
    private final Logger logger = LogManager.getLogger(this.getClass());
    @BeforeClass
    public void setupData() {
        ResourceBundle incDet = ResourceBundle.getBundle("incidenceDetails");
        assetId = Integer.parseInt(incDet.getString("assetId"));
        incidenceId = Integer.parseInt(incDet.getString("incidenceId"));

    }
    @Test(priority = 1)
    public void testIncidenceDetails() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testIncidenceDetails" );
        Setup.extentTest.set(test);

        Response response = IncidenceEndPoints.incidenceDetails(assetId, incidenceId);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}
