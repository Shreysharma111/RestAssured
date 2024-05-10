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

import static endpoints.winchcamp.AllCameraConfigs.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class AllCameraConfigsTests {
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_PositiveCase", "test positive flow" );
        Setup.extentTest.set(test);

        response = allCameraConfigsPositiveCase();

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertKeyValue(response, "status", "200");
        assertResponseTime(response);

    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = allCameraConfigsPositiveCase();

        validateJsonSchema(response, "schema/Winchcamp/allCameraConfigs.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = allCameraConfigsHeaderCase(key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_CorrectAuth", "authorization token case : correct token");
        Setup.extentTest.set(test);
        response = allCameraConfigsHeaderCase("Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = allCameraConfigsWrongEndpointCase();

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = allCameraConfigsMethodCase();

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
