package ozonetel;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.concurrent.TimeUnit;

import static endpoints.OzonetelEndpoints.getOzonetelCampaigns;
import static utilities.RestAssuredUtils.getToken;
import static utilities.RestAssuredUtils.getValue;

public class GetOzonetelCampaignsTests {
    private int ozonetelAccId;
    static String accessToken = getToken();
    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        ozonetelAccId = Integer.parseInt(getValue("ozonetel", "ozonetelId"));
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test(priority = 1)
    public void testGetOzonetelCampaigns() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetOzonetelCampaigns", "test positive flow" );
        Setup.extentTest.set(test);

        Response response = getOzonetelCampaigns(ozonetelAccId);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider", description = "test for authorization header", priority = 2)
    public void testGetOzonetelCampaignsWithoutToken(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetOzonetelCampaigns" , "test headers");
        Setup.extentTest.set(test);

        Response response = getOzonetelCampaigns(ozonetelAccId,key+":"+"Bearer "+value);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }

}
