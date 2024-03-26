package ozonetel;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.ozonetel.GetAllOzonetelAccount.*;
import static utilities.RestAssuredUtils.getToken;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.reporting.Setup.extentTest;
import static utilities.restAssuredFunctions.API.assertFieldIsPresentAndNotEmpty;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class GetAllOzonetelAccTest {


    @BeforeClass
    public void before() {
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllOonetelAccTest", "positive case");
        Setup.extentTest.set(test);
        Response response = getAllOzontelAccountPositiveCase();

        assertStatusCode(response, 200);
        extentTest.get().log(Status.INFO, "Asserting response status code");

        assertFieldIsPresentAndNotEmpty(response, "data");
        extentTest.get().log(Status.INFO, "Asserting field data is present and not empty");

    }

    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllOonetelAccTest", "schema validation case");
        Setup.extentTest.set(test);
        Response response = getAllOzontelAccountPositiveCase();

        validateJsonSchema(response, "schema/Ozonetel/getAllOzonetelAccSchema.json");
        extentTest.get().log(Status.INFO, "Validating json schema");

    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetAllOonetelAccTest", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        Response response = getAllOzontelAccountHeaderCase(key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
        extentTest.get().log(Status.INFO, "Asserting response status code: "+response.getStatusCode());

    }

    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllOonetelAccTest", "authorization token case : correct token");
        Setup.extentTest.set(test);
        Response response = getAllOzontelAccountHeaderCase("Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
        extentTest.get().log(Status.INFO, "Asserting response status code: "+response.getStatusCode());

    }

    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllOonetelAccTest", "wrong endpoint case");
        Setup.extentTest.set(test);
        Response response = getAllOzontelAccountWrongEndpointCase();

        assertStatusCode(response, 404);
        extentTest.get().log(Status.INFO, "Asserting response status code: "+response.getStatusCode());

    }

    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllOonetelAccTest", "wrong request method case");
        Setup.extentTest.set(test);
        Response response = getAllOzontelAccountMethodCase();

        assertStatusCode(response, 405);
        extentTest.get().log(Status.INFO, "Asserting response status code: "+response.getStatusCode());

    }

}
