package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.UpdateCameraConfigsDataBuilder;
import pojos.UpdateCameraConfigsPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.Iterator;

import static endpoints.winchcamp.UpdateCameraConfigs.*;
import static payloads.UpdateCameraConfigsDataBuilder.updateCameraConfigsSingleData;
import static utilities.RestAssuredUtils.printResponseLogInReport;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.restAssuredFunctions.API.assertKeyValue;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class UpdateCameraConfigsTests {
    private static Response response;
    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(dataProviderClass = UpdateCameraConfigsDataBuilder.class, dataProvider = "updateCameraConfigsSingleData")
    public void jsonSchemaValidationCase(UpdateCameraConfigsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("UpdateCameraConfigs_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = updateCameraConfigsPositiveCase(payload);

        validateJsonSchema(response, "schema/Winchcamp/updateCameraConfigs.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("UpdateCameraConfigs_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        // Logic to use payload data from updateCameraConfigsSingleData data provider
        Iterator<UpdateCameraConfigsPojo> eventsIterator = updateCameraConfigsSingleData();
        while (eventsIterator.hasNext()) {
            UpdateCameraConfigsPojo eventData = eventsIterator.next();

            response = updateCameraConfigsHeaderCase(eventData, key + ":" + "Bearer " + value);

            assertStatusCode(response, 401);
            assertKeyValue(response, "error", "Unauthorized");
        }
    }
    @Test(dataProviderClass = UpdateCameraConfigsDataBuilder.class, dataProvider = "updateCameraConfigsSingleData")
    public void wrongEndPointCase(UpdateCameraConfigsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("UpdateCameraConfigs_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = updateCameraConfigsWrongEndpointCase(payload);

        assertStatusCode(response, 404);
        assertKeyValue(response, "error", "Not Found");
    }
    @Test(dataProviderClass = UpdateCameraConfigsDataBuilder.class, dataProvider = "updateCameraConfigsSingleData")
    public void wrongMethodCase(UpdateCameraConfigsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("UpdateCameraConfigs_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = updateCameraConfigsMethodCase(payload);

        assertStatusCode(response, 405);
        assertKeyValue(response, "error", "Method Not Allowed");
    }
    @Test(dataProviderClass = UpdateCameraConfigsDataBuilder.class, dataProvider = "updateCameraConfigsData")
    public void bodyCase(UpdateCameraConfigsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest(payload.getScenerioId(), payload.getScenerioDesc());
        Setup.extentTest.set(test);

        response = updateCameraConfigsPositiveCase(payload);
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


