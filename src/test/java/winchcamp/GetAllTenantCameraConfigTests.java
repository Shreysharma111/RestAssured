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

import static endpoints.winchcamp.GetAllTenantCameraConfig.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class GetAllTenantCameraConfigTests {
    private static Response response;

    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllTenantCameraConfig_PositiveCase", "test positive flow" );
        Setup.extentTest.set(test);

        response = getAllTenantCameraConfigPositiveCase();

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertKeyValue(response, "status", "200");
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllTenantCameraConfig_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = getAllTenantCameraConfigPositiveCase();

        validateJsonSchema(response, "schema/Winchcamp/getAllTenantCameraConfig.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetAllTenantCameraConfig_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = getAllTenantCameraConfigHeaderCase(key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllTenantCameraConfig_CorrectAuth", "authorization token case : correct token");
        Setup.extentTest.set(test);
        response = getAllTenantCameraConfigHeaderCase("Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllTenantCameraConfig_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = getAllTenantCameraConfigWrongEndpointCase();

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllTenantCameraConfig_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = getAllTenantCameraConfigMethodCase();

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
