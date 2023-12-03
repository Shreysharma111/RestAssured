package api.test.incidence;

import api.endpoints.UserEndPoints2;
import api.payloads.ReportIncidenceDataBuilder;
import api.pojos.ReportIncidence;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ReportIncidenceTests {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Test(priority = 1)
    public void testIncidenceReport() {
        ReportIncidence reportPayload = ReportIncidenceDataBuilder.setupData();
        Response response = UserEndPoints2.reportIncidence(reportPayload);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}
