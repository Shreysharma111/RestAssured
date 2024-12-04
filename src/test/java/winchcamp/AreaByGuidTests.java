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

import java.util.HashMap;
import java.util.Map;

import static endpoints.winchcamp.AreaByGuid.*;
import static utilities.AssertionUtils.assertExpectedValuesWithJsonPath;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class AreaByGuidTests {
    private static String guidForAreaByGuidValue;
    private int idValue;
    private String nameValue;
    private static Response response;
    private static final String NAME_WHEN_GUID_MATCHES_NO_CAMERA = "Area Not Found";
    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        guidForAreaByGuidValue = getValue("winchcamp", "guidForFCMToken");
        idValue = Integer.parseInt(getValue("winchcamp", "idInResponseForAreaByGuid"));
        nameValue = getValue("winchcamp", "nameInResponseForAreaByGuid");
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaByGuid_PositiveCase", "test positive flow" ).assignCategory("AreaByGuid");
        Setup.extentTest.set(test);

        response = areaByGuidPositiveCase(guidForAreaByGuidValue);

        Map<String, Object> expectedFieldValues = new HashMap<>();
        expectedFieldValues.put("data.id", idValue);
        expectedFieldValues.put("data.name", nameValue);

        assertStatusCode(response, 200);
        assertExpectedValuesWithJsonPath(response, expectedFieldValues);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaByGuid_SchemaValidation", "schema validation case").assignCategory("AreaByGuid");
        Setup.extentTest.set(test);
        response = areaByGuidPositiveCase(guidForAreaByGuidValue);

        validateJsonSchema(response, "schema/Winchcamp/areaByGuid.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("AreaByGuid_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("AreaByGuid");
        Setup.extentTest.set(test);
        response = areaByGuidHeaderCase(guidForAreaByGuidValue,key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaByGuid_WrongEndpoint", "wrong endpoint case").assignCategory("AreaByGuid");
        Setup.extentTest.set(test);
        response = areaByGuidWrongEndpointCase(guidForAreaByGuidValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("AreaByGuid_WrongMethod", "wrong request method case").assignCategory("AreaByGuid");
        Setup.extentTest.set(test);
        response = areaByGuidMethodCase(guidForAreaByGuidValue);

        assertStatusCode(response, 405);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "pathDataProviderForFCMTokenList")
    public void invalidGuidCase(String guidForFCMTokenValue) {
        ExtentTest test = Setup.extentReports.createTest("AreaByGuid_InvalidGuid", "invalid guid as path param case").assignCategory("AreaByGuid");
        Setup.extentTest.set(test);
        response = areaByGuidPositiveCase(guidForFCMTokenValue);

        assertStatusCode(response, 200);
        assertKeyValue(response, "data.name", NAME_WHEN_GUID_MATCHES_NO_CAMERA);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
