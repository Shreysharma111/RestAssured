package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.winchcamp.GetFacilities.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.assertResponseTime;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class GetFacilitiesTests {
    private static Response response;

    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilities_PositiveCase", "test positive flow" ).assignCategory("GetFacilities");
        Setup.extentTest.set(test);

        response = getFacilitiesPositiveCase();

        assertStatusCode(response, 200);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilities_SchemaValidation", "schema validation case").assignCategory("GetFacilities");
        Setup.extentTest.set(test);
        response = getFacilitiesPositiveCase();

        validateJsonSchema(response, "schema/Winchcamp/getFacilities.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetFacilities_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("GetFacilities");
        Setup.extentTest.set(test);
        response = getFacilitiesHeaderCase(key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilities_CorrectAuth", "authorization token case : correct token").assignCategory("GetFacilities");
        Setup.extentTest.set(test);
        response = getFacilitiesHeaderCase("Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilities_WrongEndpoint", "wrong endpoint case").assignCategory("GetFacilities");
        Setup.extentTest.set(test);
        response = getFacilitiesWrongEndpointCase();

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilities_WrongMethod", "wrong request method case").assignCategory("GetFacilities");
        Setup.extentTest.set(test);
        response = getFacilitiesMethodCase();

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}

