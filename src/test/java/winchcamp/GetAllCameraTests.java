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

import static endpoints.winchcamp.GetAllCamera.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class GetAllCameraTests {
    private String keywordValue;
    private int statusValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        keywordValue = getValue("winchcamp", "keyword");
        statusValue = Integer.parseInt(getValue("winchcamp", "status"));
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void noParamCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_NoParamCase", "test no param flow" );
        Setup.extentTest.set(test);

        response = getAllCameraPositiveCase();

        assertStatusCode(response, 400);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = getAllCameraQueryParamCase("keyword"+":"+keywordValue, "status"+":"+statusValue);

        validateJsonSchema(response, "schema/Winchcamp/getAllCamera.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = getAllCameraHeaderCase(keywordValue, statusValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "queryDataProviderForGetAllCamera")
    public void wrongParamCase(Object[][] queryParams) {
        // Convert Object[][] to String[] in the format of "key:value"
        String[] keyValuePairs = new String[queryParams.length];

        for (int i = 0; i < queryParams.length; i++) {
            keyValuePairs[i] = queryParams[i][0] + ":" + queryParams[i][1]; // "key:value"
        }
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_wrongParam", "wrong query param case : keyword param | status param");
        Setup.extentTest.set(test);
        response = getAllCameraQueryParamCase(keyValuePairs);

        assertStatusCode(response, 200);
        assertArrayIsPresentAndEmpty(response, "data.content");
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_CorrectAuth", "authorization token case : correct token");
        Setup.extentTest.set(test);
        response = getAllCameraHeaderCase(keywordValue, statusValue,"Authorization:"+"Bearer "+getToken());

        assertFieldIsPresentAndNotEmpty(response, "data.content");
        assertKeyValue(response, "status", "200");
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = getAllCameraWrongEndpointCase("keyword"+":"+keywordValue, "status"+":"+statusValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = getAllCameraMethodCase("keyword"+":"+keywordValue, "status"+":"+statusValue);

        assertStatusCode(response, 405);

    }
    @Test
    public void verifyKeywordParamCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_KeywordParam", "keyword param impact case");
        Setup.extentTest.set(test);
        response = getAllCameraPositiveCase("keyword"+":"+keywordValue, "status"+":"+"");
        assertKeyValue(response, "error", "Bad Request");
        assertStatusCode(response, 400);

    }

    @Test
    public void verifyStatusParamCase() {
        ExtentTest test = Setup.extentReports.createTest("GetAllCamera_StatusParam", "status param impact case");
        Setup.extentTest.set(test);
        response = getAllCameraPositiveCase("keyword"+":"+"", "status"+":"+statusValue);
        assertKeyValue(response, "error", "Bad Request");
        assertStatusCode(response, 400);

    }
    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }
}

