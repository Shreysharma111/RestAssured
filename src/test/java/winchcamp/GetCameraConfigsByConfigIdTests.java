package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.winchcamp.GetCameraConfigsByConfigId.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class GetCameraConfigsByConfigIdTests {
    private int configIdValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        configIdValue = Integer.parseInt(getValue("winchcamp", "configId"));
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetCameraConfigsByConfigId_PositiveCase", "test positive flow" );
        Setup.extentTest.set(test);

        response = getCameraConfigsByConfigIdPositiveCase(configIdValue);

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetCameraConfigsByConfigId_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = getCameraConfigsByConfigIdPositiveCase(configIdValue);

        validateJsonSchema(response, "schema/Winchcamp/getCameraConfigsByConfigId.json");

    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetCameraConfigsByConfigId_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = getCameraConfigsByConfigIdHeaderCase(configIdValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetCameraConfigsByConfigId_CorrectAuth", "authorization token case : correct token");
        Setup.extentTest.set(test);
        response = getCameraConfigsByConfigIdHeaderCase(configIdValue,"Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);

    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetCameraConfigsByConfigId_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = getCameraConfigsByConfigIdWrongEndpointCase(configIdValue);

        assertStatusCode(response, 404);

    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetCameraConfigsByConfigId_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = getCameraConfigsByConfigIdMethodCase(configIdValue);

        assertStatusCode(response, 405);

    }
    @Test
    public void wrongGuidCase() {
        ExtentTest test = Setup.extentReports.createTest("GetCameraConfigsByConfigId_ZeroConfigId", "wrong guid as path param case");
        Setup.extentTest.set(test);
        response = getCameraConfigsByConfigIdZeroConfigIdCase();

        assertStatusCode(response, 404);
        assertKeyValue(response, "message", "No record found");

    }

    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }


}
