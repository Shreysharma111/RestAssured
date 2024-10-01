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

import static endpoints.winchcamp.DeleteCameraConfigsByConfigId.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class DeleteCameraConfigsByConfigIdTests {
    private static int cameraConfigIdValue = Integer.parseInt(getValue("winchcamp", "cameraConfigId"));
    private static Response response;
    private static final String DELETE_SUCCESS_MSG = "Facility configuration with id : "+cameraConfigIdValue+"  deleted successfully";
    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("DeleteCameraConfigsByConfigId_PositiveCase", "test positive flow" ).assignCategory("DeleteCameraConfigsByConfigId");
        Setup.extentTest.set(test);

        response = deleteCameraConfigsByConfigIdPositiveCase(cameraConfigIdValue);

        assertStatusCode(response, 200);
        assertKeyValue(response, "message", DELETE_SUCCESS_MSG);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("DeleteCameraConfigsByConfigId_SchemaValidation", "schema validation case").assignCategory("DeleteCameraConfigsByConfigId");
        Setup.extentTest.set(test);
        response = deleteCameraConfigsByConfigIdPositiveCase(cameraConfigIdValue);

        validateJsonSchema(response, "schema/Winchcamp/deleteCameraConfigsByConfigId.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("DeleteCameraConfigsByConfigId_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("DeleteCameraConfigsByConfigId");
        Setup.extentTest.set(test);
        response = deleteCameraConfigsByConfigIdHeaderCase(cameraConfigIdValue,key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("DeleteCameraConfigsByConfigId_WrongEndpoint", "wrong endpoint case").assignCategory("DeleteCameraConfigsByConfigId");
        Setup.extentTest.set(test);
        response = deleteCameraConfigsByConfigIdWrongEndpointCase(cameraConfigIdValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("DeleteCameraConfigsByConfigId_WrongMethod", "wrong request method case").assignCategory("DeleteCameraConfigsByConfigId");
        Setup.extentTest.set(test);
        response = deleteCameraConfigsByConfigIdMethodCase(cameraConfigIdValue);

        assertStatusCode(response, 405);
    }
    @Test
    public void repeatCase() {
        ExtentTest test = Setup.extentReports.createTest("DeleteCameraConfigsByConfigId_RepeatCase", "test already deleted config" ).assignCategory("DeleteCameraConfigsByConfigId");
        Setup.extentTest.set(test);

        response = deleteCameraConfigsByConfigIdPositiveCase(cameraConfigIdValue);
        assertStatusCode(response, 200);
        response = deleteCameraConfigsByConfigIdPositiveCase(cameraConfigIdValue);
        assertStatusCode(response, 208);
    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "pathDataProviderForDeleteCameraConfigByConfigId")
    public void invalidCameraConfigIdCase(int cameraConfigIdValue) {
        ExtentTest test = Setup.extentReports.createTest("DeleteCameraConfigsByConfigId_WrongConfigId", "invalid camera config Id as path param case").assignCategory("DeleteCameraConfigsByConfigId");
        Setup.extentTest.set(test);
        response = deleteCameraConfigsByConfigIdPositiveCase(cameraConfigIdValue);

        assertStatusCode(response, 404);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
