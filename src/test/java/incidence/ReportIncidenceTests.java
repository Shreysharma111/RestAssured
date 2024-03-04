package incidence;

import endpoints.IncidenceEndPoints;
import payloads.ReportIncidenceDataBuilder;
import pojos.ReportIncidence;
import utilities.reporting.Setup;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ReportIncidenceTests {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Test
    public void testIncidenceReport() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testIncidenceReport" );
        Setup.extentTest.set(test);

        ReportIncidence reportPayload = ReportIncidenceDataBuilder.setupData();
        Response response = IncidenceEndPoints.reportIncidence(reportPayload);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}
