package winchcamp;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.SaveEventsDataBuilder;
import pojos.SaveEventsPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.winchcamp.SaveEvents.saveEventsPositiveCase;
import static utilities.RestAssuredUtils.validateJsonSchema;

public class SaveCameraConfigsTests {
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(dataProviderClass = SaveEventsDataBuilder.class, dataProvider = "saveEventsSingleData")
    public void jsonSchemaValidationCase(SaveEventsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("SaveEvents_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = saveEventsPositiveCase(payload);

        validateJsonSchema(response, "schema/Winchcamp/saveEvents.json");

    }
}
