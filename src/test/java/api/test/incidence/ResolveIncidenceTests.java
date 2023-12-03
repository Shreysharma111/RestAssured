package api.test.incidence;

import api.endpoints.UserEndPoints2;
import api.payloads.ResolveIncidenceDataBuilder;
import api.pojos.ResolveIncidence;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ResolveIncidenceTests {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Test(priority = 1)
    public void testIncidenceResolve() {
        ResolveIncidence resolvePayload = ResolveIncidenceDataBuilder.setupData();
        Response response = UserEndPoints2.resolveIncidence(resolvePayload);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}