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

import static endpoints.winchcamp.FCMTokenList.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class FCMTokenListTests {
    private static String guidForFCMTokenValue;
    private static Response response;
    private static final String NO_TOKEN_MSG = "No tokens available";
    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        guidForFCMTokenValue = getValue("winchcamp", "guidForFCMToken");
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("FCMTokenList_PositiveCase", "test positive flow" );
        Setup.extentTest.set(test);

        response = fcmTokenListPositiveCase(guidForFCMTokenValue);

        assertStatusCode(response, 200);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("FCMTokenList_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = fcmTokenListPositiveCase(guidForFCMTokenValue);

        validateJsonSchema(response, "schema/Winchcamp/fcmTokenList.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("FCMTokenList_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = fcmTokenListHeaderCase(guidForFCMTokenValue,key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("FCMTokenList_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = fcmTokenListWrongEndpointCase(guidForFCMTokenValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("FCMTokenList_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = fcmTokenListMethodCase(guidForFCMTokenValue);

        assertStatusCode(response, 405);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "pathDataProviderForFCMTokenList")
    public void invalidGuidCase(String guidForFCMTokenValue) {
        ExtentTest test = Setup.extentReports.createTest("FCMTokenList_InvalidGuid", "invalid guid as path param case");
        Setup.extentTest.set(test);
        response = fcmTokenListPositiveCase(guidForFCMTokenValue);

        assertStatusCode(response, 404);
        assertKeyValue(response, "message", NO_TOKEN_MSG);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
