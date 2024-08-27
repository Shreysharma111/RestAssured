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

import static endpoints.winchcamp.EditMasterCamera.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class EditMasterCameraTests {
    private String cameraNameValue;
    private int idValue;
    public static final String MISSING_REQUEST_PARAM_ID_MSG = "Required request parameter 'id' for method parameter type Integer is not present";
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        cameraNameValue = getValue("winchcamp", "cameraNameForEditMasterCamera");
        idValue = Integer.parseInt(getValue("winchcamp", "id"));
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void noParamCase() {
        ExtentTest test = Setup.extentReports.createTest("EditMasterCamera_NoParamCase", "test no param flow" );
        Setup.extentTest.set(test);

        response = editMasterCameraPositiveCase();

        assertStatusCode(response, 400);
        assertKeyValue(response, "message", MISSING_REQUEST_PARAM_ID_MSG);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("EditMasterCamera_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = editMasterCameraQueryParamCase("cameraName"+":"+cameraNameValue, "id"+":"+idValue);

        validateJsonSchema(response, "schema/Winchcamp/editMasterCamera.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("EditMasterCamera_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = editMasterCameraHeaderCase(cameraNameValue, idValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "queryDataProviderForEditMasterCamera")
    public void emptyParamCase(Object[][] queryParams) {
        // Convert Object[][] to String[] in the format of "key:value"
        String[] keyValuePairs = new String[queryParams.length];

        for (int i = 0; i < queryParams.length; i++) {
            keyValuePairs[i] = queryParams[i][0] + ":" + queryParams[i][1]; // "key:value"
        }
        ExtentTest test = Setup.extentReports.createTest("EditMasterCamera_wrongParam", "empty query param case : cameraName param | id param");
        Setup.extentTest.set(test);
        response = editMasterCameraQueryParamCase(keyValuePairs);

        assertStatusCode(response, 400);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("EditMasterCamera_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = editMasterCameraWrongEndpointCase("cameraName"+":"+cameraNameValue, "id"+":"+idValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("EditMasterCamera_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = editMasterCameraMethodCase("cameraName"+":"+cameraNameValue, "id"+":"+idValue);

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }
}
