package ozonetel;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import endpoints.OzonetelEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.concurrent.TimeUnit;

import static endpoints.OzonetelEndpoints.getAllOzontelAccount;
import static utilities.RestAssuredUtils.*;

public class GetAllOzonetelAccTests {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(priority = 1)
    public void testGetAllOzonetelAccWrongUrl() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetAllOzonetelAccWithAuth", "happy flow");
        Setup.extentTest.set(test);

        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+getToken())
                .when()
                .get(OzonetelEndpoints.getAllOzontelAccount_url+"wrong")
                .then().extract().response();

        System.out.println(response.asPrettyString());
        // Assert the status code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404, "Status code is not as expected");

        // Extract the value of the "message" field from the response
        String message = response.jsonPath().getString("error");

// Assert the value of the "message" field
        Assert.assertEquals(message, "Not Found", "Message field is not as expected");

//        printRequestLogInReport();
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }

    @Test(priority = 2)
    public void testGetAllOzonetelAccWithoutToken() {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetAllOzonetelAccWithoutToken", "happy flow");
        Setup.extentTest.set(test);
        Response response = getAllOzontelAccount("withoutAuth");

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");

    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider", description = "test for authorization header", priority = 3)
    public void testGetAllOzonetelAccHeaders(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("Test Name - testGetAllOzonetelAccHeaders" , "test for authorization header");
        Setup.extentTest.set(test);

        Response response = getAllOzontelAccount(key+":"+"Bearer "+value);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
