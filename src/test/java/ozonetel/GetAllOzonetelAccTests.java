package ozonetel;

import Dataprovider.Dataprovider;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

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
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetAllOzonetelAcc", "happy flow");
        Setup.extentTest.set(test);

        Response response = getAllOzontelAccount();

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }

    @Test(dataProviderClass = Dataprovider.class, dataProvider = "headerDataProvider", description = "test for authorization header", priority = 2)
    public void testGetAllOzonetelAccHeaders(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetAllOzonetelAccHeaders" , "test for authorization header");
        Setup.extentTest.set(test);
        Setup.extentTest.get().log(Status.INFO,"API is starting");
        Response response = getAllOzontelAccount(key+":"+"Bearer "+value);

        Setup.extentTest.get().log(Status.INFO, "API is hit successfully");
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
