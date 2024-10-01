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

import static endpoints.winchcamp.ActivateCamera.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class ActivateCameraTests {
    private int idValue;
    private static Response response;

    public static final String MISSING_REQUEST_PARAM_ID_MSG = "Required request parameter 'id' for method parameter type Integer is not present";
    public static final String SUCCESS_REQUEST_MSG = "camera Activated successfully";

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        idValue = Integer.parseInt(getValue("winchcamp", "cameraIdForCamerasScreen"));
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void noParamCase() {
        ExtentTest test = Setup.extentReports.createTest("ActivateCamera_NoParamCase", "test no param flow" ).assignCategory("ActivateCamera");
        Setup.extentTest.set(test);

        response = activateCameraPositiveCase();

        assertStatusCode(response, 400);
        assertKeyValue(response, "message", MISSING_REQUEST_PARAM_ID_MSG);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("ActivateCamera_SchemaValidation", "schema validation case").assignCategory("ActivateCamera");
        Setup.extentTest.set(test);
        response = activateCameraQueryParamCase("id"+":"+idValue);

        validateJsonSchema(response, "schema/Winchcamp/activateCamera.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("ActivateCamera_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("ActivateCamera");
        Setup.extentTest.set(test);
        response = activateCameraHeaderCase(idValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "queryDataProviderForActivateCamera")
    public void wrongParamCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("ActivateCamera_wrongParam", "wrong query param case : zero param | not existing param").assignCategory("ActivateCamera");
        Setup.extentTest.set(test);
        response = activateCameraQueryParamCase(key+":"+value);

        assertStatusCode(response, 400);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("ActivateCamera_CorrectAuth", "authorization token case : correct token").assignCategory("ActivateCamera");
        Setup.extentTest.set(test);
        response = activateCameraHeaderCase(idValue,"Authorization:"+"Bearer "+getToken());

        assertKeyValue(response, "message", SUCCESS_REQUEST_MSG);
        assertKeyValue(response, "status", "200");
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("ActivateCamera_WrongEndpoint", "wrong endpoint case").assignCategory("ActivateCamera");
        Setup.extentTest.set(test);
        response = activateCameraWrongEndpointCase("id"+":"+idValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("ActivateCamera_WrongMethod", "wrong request method case").assignCategory("ActivateCamera");
        Setup.extentTest.set(test);
        response = activateCameraMethodCase("id"+":"+idValue);

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }
}
