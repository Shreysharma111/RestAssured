package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import payloads.SaveFCMTokenDataBuilder;
import pojos.SaveFCMTokenPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.Iterator;

import static endpoints.winchcamp.SaveFCMToken.*;
import static payloads.SaveFCMTokenDataBuilder.saveFCMTokenSingleData;
import static utilities.RestAssuredUtils.printResponseLogInReport;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.restAssuredFunctions.API.assertKeyValue;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class SaveFCMTokenTests {
    private static Response response;
    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(dataProviderClass = SaveFCMTokenDataBuilder.class, dataProvider = "saveFCMTokenSingleData")
    public void jsonSchemaValidationCase(SaveFCMTokenPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("SaveFCMToken_SchemaValidation", "schema validation case").assignCategory("SaveFCMToken");
        Setup.extentTest.set(test);
        response = saveFCMTokenPositiveCase(payload);

        validateJsonSchema(response, "schema/Winchcamp/saveFCMToken.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("SaveFCMToken_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("SaveFCMToken");
        Setup.extentTest.set(test);
        // Logic to use payload data from updateCameraConfigsSingleData data provider
        Iterator<SaveFCMTokenPojo> eventsIterator = saveFCMTokenSingleData();
        while (eventsIterator.hasNext()) {
            SaveFCMTokenPojo eventData = eventsIterator.next();

            response = saveFCMTokenHeaderCase(eventData, key + ":" + "Bearer " + value);

            assertStatusCode(response, 401);
            assertKeyValue(response, "error", "Unauthorized");
        }
    }
    @Test(dataProviderClass = SaveFCMTokenDataBuilder.class, dataProvider = "saveFCMTokenSingleData")
    public void wrongEndPointCase(SaveFCMTokenPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("SaveFCMToken_WrongEndpoint", "wrong endpoint case").assignCategory("SaveFCMToken");
        Setup.extentTest.set(test);
        response = saveFCMTokenWrongEndpointCase(payload);

        assertStatusCode(response, 404);
        assertKeyValue(response, "error", "Not Found");
    }
    @Test(dataProviderClass = SaveFCMTokenDataBuilder.class, dataProvider = "saveFCMTokenSingleData")
    public void wrongMethodCase(SaveFCMTokenPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("SaveFCMToken_WrongMethod", "wrong request method case").assignCategory("SaveFCMToken");
        Setup.extentTest.set(test);
        response = saveFCMTokenMethodCase(payload);

        assertStatusCode(response, 405);
        assertKeyValue(response, "error", "Method Not Allowed");
    }
    @Test(dataProviderClass = SaveFCMTokenDataBuilder.class, dataProvider = "saveFCMTokenData")
    public void bodyCase(SaveFCMTokenPojo payload) {
        ExtentTest test = Setup.extentReports.createTest(payload.getScenerioId(), payload.getScenerioDesc()).assignCategory("SaveFCMToken");
        Setup.extentTest.set(test);

        response = saveFCMTokenPositiveCase(payload);
        assertKeyValue(response, "message", payload.getExpectedMessage());
        assertStatusCode(response, payload.getExpectedStatusCode());

    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}


