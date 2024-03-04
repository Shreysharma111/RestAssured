package ozonetel;

import utilities.OAuth2Authorization;
import utilities.reporting.Setup;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static utilities.RestAssuredUtils.getValue;
import static endpoints.OzonetelEndpoints.getOzonetelCampaigns;

public class GetOzonetelCampaignsTests {
    private int ozonetelAccId;
    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        ozonetelAccId = Integer.parseInt(getValue("ozonetel", "ozonetelId"));
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test(priority = 1)
    public void testGetOzonetelCampaigns() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetOzonetelCampaigns" );
        Setup.extentTest.set(test);

        Response response = getOzonetelCampaigns(ozonetelAccId);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }

}
