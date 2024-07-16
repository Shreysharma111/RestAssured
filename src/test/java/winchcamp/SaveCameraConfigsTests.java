package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.SaveCameraConfigsDataBuilder;
import pojos.SaveCameraConfigsPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.Iterator;

import static endpoints.winchcamp.SaveCameraConfigs.*;
import static payloads.SaveCameraConfigsDataBuilder.saveCameraConfigsSingleData;
import static utilities.RestAssuredUtils.printResponseLogInReport;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.restAssuredFunctions.API.assertKeyValue;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class SaveCameraConfigsTests {
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(dataProviderClass = SaveCameraConfigsDataBuilder.class, dataProvider = "saveCameraConfigsSingleData")
    public void jsonSchemaValidationCase(SaveCameraConfigsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("SaveCameraConfigs_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = saveCameraConfigsPositiveCase(payload);

        validateJsonSchema(response, "schema/Winchcamp/saveCameraConfigs.json");

    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("SaveCameraConfigs_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        // Logic to use payload data from saveCameraConfigsPositiveData data provider
        Iterator<SaveCameraConfigsPojo> eventsIterator = saveCameraConfigsSingleData();
        while (eventsIterator.hasNext()) {
            SaveCameraConfigsPojo eventData = eventsIterator.next();

            response = saveCameraConfigsHeaderCase(eventData, key + ":" + "Bearer " + value);

            assertStatusCode(response, 401);
            assertKeyValue(response, "error", "Unauthorized");
        }
    }
    @Test(dataProviderClass = SaveCameraConfigsDataBuilder.class, dataProvider = "saveCameraConfigsSingleData")
    public void wrongEndPointCase(SaveCameraConfigsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("SaveCameraConfigs_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = saveCameraConfigsWrongEndpointCase(payload);

        assertStatusCode(response, 404);
        assertKeyValue(response, "error", "Not Found");
    }
    @Test(dataProviderClass = SaveCameraConfigsDataBuilder.class, dataProvider = "saveCameraConfigsSingleData")
    public void wrongMethodCase(SaveCameraConfigsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("SaveCameraConfigs_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = saveCameraConfigsMethodCase(payload);

        assertStatusCode(response, 405);
        assertKeyValue(response, "error", "Method Not Allowed");
    }
    @Test(dataProviderClass = SaveCameraConfigsDataBuilder.class, dataProvider = "saveCameraConfigsData")
    public void bodyCase(SaveCameraConfigsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest(payload.getScenerioId(), payload.getScenerioDesc());
        Setup.extentTest.set(test);

        response = saveCameraConfigsPositiveCase(payload);
        assertKeyValue(response, "message", payload.getExpectedMessage());

        if(payload.getExpectedStatusCode()!=200) {
            if(payload.getExpectedStatusCode()==400) {
                assertStatusCode(response,400);
            }
            else  {
                assertStatusCode(response,404);
            }
        }
        else {
            assertStatusCode(response,200);
        }
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}

