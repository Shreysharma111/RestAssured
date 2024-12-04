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

import static endpoints.winchcamp.ManualOverride.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class ManualOverrideTests {
    private String guidValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        guidValue = getValue("winchcamp", "guidForManualOverride");
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("ManualOverride_PositiveCase", "test positive flow" ).assignCategory("ManualOverride");
        Setup.extentTest.set(test);

        response = manualOverridePositiveCase(guidValue);

        assertStatusCode(response, 200);
        assertKeyValue(response, "message", "Override Successfully");
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("ManualOverride_SchemaValidation", "schema validation case").assignCategory("ManualOverride");
        Setup.extentTest.set(test);
        response = manualOverridePositiveCase(guidValue);

        validateJsonSchema(response, "schema/Winchcamp/manualOverride.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("ManualOverride_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("ManualOverride");
        Setup.extentTest.set(test);
        response = manualOverrideHeaderCase(guidValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("ManualOverride_CorrectAuth", "authorization token case : correct token").assignCategory("ManualOverride");
        Setup.extentTest.set(test);
        response = manualOverrideHeaderCase(guidValue,"Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);

    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("ManualOverride_WrongEndpoint", "wrong endpoint case").assignCategory("ManualOverride");
        Setup.extentTest.set(test);
        response = manualOverrideWrongEndpointCase(guidValue);

        assertStatusCode(response, 404);

    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("ManualOverride_WrongMethod", "wrong request method case").assignCategory("ManualOverride");
        Setup.extentTest.set(test);
        response = manualOverrideMethodCase(guidValue);

        assertStatusCode(response, 405);

    }
    @Test
    public void wrongGuidCase() {
        ExtentTest test = Setup.extentReports.createTest("ManualOverride_WrongGuid", "wrong guid as path param case").assignCategory("ManualOverride");
        Setup.extentTest.set(test);
        response = manualOverrideWrongGuidCase(guidValue);

        assertStatusCode(response, 404);
        assertKeyValue(response, "message", "Camera not found against guid");

    }
    @AfterMethod
    public static void afterMethodResLog() {
        printResponseLogInReport(response);
    }

}
