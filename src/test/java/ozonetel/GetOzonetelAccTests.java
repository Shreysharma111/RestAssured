package ozonetel;

import Dataproviders.Dataproviders;
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

import static endpoints.ozonetel.GetOzonetelAccounts.*;
import static utilities.RestAssuredUtils.getToken;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.reporting.Setup.extentTest;
import static utilities.restAssuredFunctions.API.assertFieldIsPresentAndNotEmpty;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class GetOzonetelAccTests {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelAccTests", "positive case");
        Setup.extentTest.set(test);
        Response response = getOzontelAccountsPositiveCase();

        assertStatusCode(response, 200);
        extentTest.get().log(Status.INFO, "Asserting response status code");

        assertFieldIsPresentAndNotEmpty(response, "data");
        extentTest.get().log(Status.INFO, "Asserting field data is present and not empty");

    }

    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelAccTests", "schema validation case");
        Setup.extentTest.set(test);
        Response response = getOzontelAccountsPositiveCase();

        validateJsonSchema(response, "schema/Ozonetel/getAllOzonetelAccSchema.json");
        extentTest.get().log(Status.INFO, "Validating json schema");

    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelAccTests", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        Response response = getOzontelAccountHeaderCase(key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
        extentTest.get().log(Status.INFO, "Asserting response status code: "+response.getStatusCode());

    }

    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelAccTests", "authorization token case : correct token");
        Setup.extentTest.set(test);
        Response response = getOzontelAccountHeaderCase("Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
        extentTest.get().log(Status.INFO, "Asserting response status code: "+response.getStatusCode());

    }

    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelAccTests", "wrong endpoint case");
        Setup.extentTest.set(test);
        Response response = getOzontelAccountWrongEndpointCase();

        assertStatusCode(response, 404);
        extentTest.get().log(Status.INFO, "Asserting response status code: "+response.getStatusCode());

    }

    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelAccTests", "wrong request method case");
        Setup.extentTest.set(test);
        Response response = getOzontelAccountMethodCase();

        assertStatusCode(response, 405);
        extentTest.get().log(Status.INFO, "Asserting response status code: "+response.getStatusCode());

    }

}
