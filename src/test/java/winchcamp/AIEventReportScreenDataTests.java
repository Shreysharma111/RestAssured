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

import static endpoints.winchcamp.AIEventReportScreenData.*;
import static utilities.RestAssuredUtils.printResponseLogInReport;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.restAssuredFunctions.API.assertKeyValue;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class AIEventReportScreenDataTests {
    private static Response response;
    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("AIEventReportScreenData_SchemaValidation", "schema validation case").assignCategory("AIEventReportScreenData");
        Setup.extentTest.set(test);
        response = aiEventReportScreenDataPositiveCase();

        validateJsonSchema(response, "schema/Winchcamp/aiEventReportScreenData.json");
        assertStatusCode(response, 200);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("AIEventReportScreenData_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("AIEventReportScreenData");
        Setup.extentTest.set(test);

            response = aiEventReportScreenDataHeaderCase( key + ":" + "Bearer " + value);

            assertStatusCode(response, 401);
            assertKeyValue(response, "error", "Unauthorized");
        }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("AIEventReportScreenData_WrongEndpoint", "wrong endpoint case").assignCategory("AIEventReportScreenData");
        Setup.extentTest.set(test);
        response = aiEventReportScreenDataWrongEndpointCase();

        assertStatusCode(response, 404);
        assertKeyValue(response, "error", "Not Found");
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("AIEventReportScreenData_WrongMethod", "wrong request method case").assignCategory("AIEventReportScreenData");
        Setup.extentTest.set(test);
        response = aiEventReportScreenDataMethodCase();

        assertStatusCode(response, 405);
        assertKeyValue(response, "error", "Method Not Allowed");
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
