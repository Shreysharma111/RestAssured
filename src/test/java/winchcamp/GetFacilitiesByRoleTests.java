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

import static endpoints.winchcamp.GetFacilitiesByRole.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class GetFacilitiesByRoleTests {
    private static Response response;

    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByRole_PositiveCase", "test positive flow" ).assignCategory("GetFacilitiesByRole");
        Setup.extentTest.set(test);

        response = getFacilitiesByRolePositiveCase();

        assertStatusCode(response, 200);
        assertKeyValue(response, "status", "200");
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByRole_SchemaValidation", "schema validation case").assignCategory("GetFacilitiesByRole");
        Setup.extentTest.set(test);
        response = getFacilitiesByRolePositiveCase();

        validateJsonSchema(response, "schema/Winchcamp/getFacilitiesByRole.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByRole_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("GetFacilitiesByRole");
        Setup.extentTest.set(test);
        response = getFacilitiesByRoleHeaderCase(key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByRole_CorrectAuth", "authorization token case : correct token").assignCategory("GetFacilitiesByRole");
        Setup.extentTest.set(test);
        response = getFacilitiesByRoleHeaderCase("Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByRole_WrongEndpoint", "wrong endpoint case").assignCategory("GetFacilitiesByRole");
        Setup.extentTest.set(test);
        response = getFacilitiesByRoleWrongEndpointCase();

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByRole_WrongMethod", "wrong request method case").assignCategory("GetFacilitiesByRole");
        Setup.extentTest.set(test);
        response = getFacilitiesByRoleMethodCase();

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}

