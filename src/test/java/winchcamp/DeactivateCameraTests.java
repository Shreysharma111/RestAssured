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

import static endpoints.winchcamp.DeactivateCamera.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class DeactivateCameraTests {
    private int idValue;
    private static Response response;

    public static final String MISSING_REQUEST_PARAM_ID_MSG = "Required request parameter 'id' for method parameter type Integer is not present";
    public static final String SUCCESS_REQUEST_MSG = "camera deActivated successfully";
    public static final String CAMERA_NOT_FOUND_MSG = "Camera Not Found";

    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        idValue = Integer.parseInt(getValue("winchcamp", "id"));
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void noParamCase() {
        ExtentTest test = Setup.extentReports.createTest("deActivateCamera_NoParamCase", "test no param flow" );
        Setup.extentTest.set(test);

        response = deactivateCameraPositiveCase();

        assertStatusCode(response, 400);
        assertKeyValue(response, "message", MISSING_REQUEST_PARAM_ID_MSG);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("deActivateCamera_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = deactivateCameraQueryParamCase("id"+":"+idValue);

        validateJsonSchema(response, "schema/Winchcamp/deactivateCamera.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("deActivateCamera_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = deactivateCameraHeaderCase(idValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "queryDataProviderForActivateCamera")
    public void wrongParamCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("deActivateCamera_wrongParam", "wrong query param case : zero param | not existing param");
        Setup.extentTest.set(test);
        response = deactivateCameraQueryParamCase(key+":"+value);

        assertKeyValue(response, "message", CAMERA_NOT_FOUND_MSG);
        assertStatusCode(response, 400);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("deActivateCamera_CorrectAuth", "authorization token case : correct token");
        Setup.extentTest.set(test);
        response = deactivateCameraHeaderCase(idValue,"Authorization:"+"Bearer "+getToken());

        assertKeyValue(response, "message", SUCCESS_REQUEST_MSG);
        assertKeyValue(response, "status", "200");
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("deActivateCamera_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = deactivateCameraWrongEndpointCase("id"+":"+idValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("deActivateCamera_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = deactivateCameraMethodCase("id"+":"+idValue);

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }
}

