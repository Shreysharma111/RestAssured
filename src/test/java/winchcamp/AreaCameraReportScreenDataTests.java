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

import static endpoints.winchcamp.AreaCameraReportScreenData.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.assertResponseTime;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class AreaCameraReportScreenDataTests {
    private static Response response;
    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaCameraReportScreenData_PositiveCase", "test positive flow" ).assignCategory("AreaCameraReportScreenData");
        Setup.extentTest.set(test);
        response = areaCameraReportScreenDataPositiveCase();

        assertStatusCode(response, 200);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaCameraReportScreenData_SchemaValidation", "schema validation case").assignCategory("AreaCameraReportScreenData");
        Setup.extentTest.set(test);
        response = areaCameraReportScreenDataPositiveCase();

        validateJsonSchema(response, "schema/Winchcamp/areaCameraReportScreenData.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("AreaCameraReportScreenData_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("AreaCameraReportScreenData");
        Setup.extentTest.set(test);
        response = areaCameraReportScreenDataHeaderCase(key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaCameraReportScreenData_CorrectAuth", "authorization token case : correct token").assignCategory("AreaCameraReportScreenData");
        Setup.extentTest.set(test);
        response = areaCameraReportScreenDataHeaderCase("Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaCameraReportScreenData_WrongEndpoint", "wrong endpoint case").assignCategory("AreaCameraReportScreenData");
        Setup.extentTest.set(test);
        response = areaCameraReportScreenDataWrongEndpointCase();

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaCameraReportScreenData_WrongMethod", "wrong request method case").assignCategory("AreaCameraReportScreenData");
        Setup.extentTest.set(test);
        response = areaCameraReportScreenDataMethodCase();

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}

