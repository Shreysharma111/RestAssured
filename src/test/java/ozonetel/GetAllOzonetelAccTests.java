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

import static endpoints.OzonetelEndpoints.getAllOzontelAccount;

public class GetAllOzonetelAccTests {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(priority = 1)
    public void testGetAllOzonetelAcc() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetAllOzonetelAcc" );
        Setup.extentTest.set(test);

        Response response = getAllOzontelAccount();

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }
}
