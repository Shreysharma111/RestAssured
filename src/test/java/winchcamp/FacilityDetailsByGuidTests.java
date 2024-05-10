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

import static endpoints.winchcamp.FacilityDetailsByGuid.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class FacilityDetailsByGuidTests {
    private String guidValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        guidValue = getValue("winchcamp", "guid");
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("FacilityDetailsByGuid_PositiveCase", "test positive flow" );
        Setup.extentTest.set(test);

        response = facilityDetailsByGuidPositiveCase(guidValue);

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertKeyValue(response, "data.name", getValue("winchcamp", "facilityName"));
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("FacilityDetailsByGuid_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = facilityDetailsByGuidPositiveCase(guidValue);

        validateJsonSchema(response, "schema/Winchcamp/facilityDetailsByGuid.json");

    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("FacilityDetailsByGuid_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = facilityDetailsByGuidHeaderCase(guidValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("FacilityDetailsByGuid_CorrectAuth", "authorization token case : correct token");
        Setup.extentTest.set(test);
        response = facilityDetailsByGuidHeaderCase(guidValue,"Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);

    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("FacilityDetailsByGuid_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = facilityDetailsByGuidWrongEndpointCase(guidValue);

        assertStatusCode(response, 404);

    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("FacilityDetailsByGuid_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = facilityDetailsByGuidMethodCase(guidValue);

        assertStatusCode(response, 405);

    }
    @Test
    public void wrongGuidCase() {
        ExtentTest test = Setup.extentReports.createTest("FacilityDetailsByGuid_WrongGuid", "wrong guid as path param case");
        Setup.extentTest.set(test);
        response = facilityDetailsByGuidWrongGuidCase(guidValue);

        assertStatusCode(response, 404);
        assertKeyValue(response, "message", "Camera not found against guid");

    }

    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }

}
