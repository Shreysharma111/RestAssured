package winchcamp;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.SaveEventsDataBuilder;
import pojos.SaveEventsPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static utilities.ApiValidationHelper.validatePayloadForAllFields;
import static utilities.RestAssuredUtils.getUrl;

public class PayloadValidationTests {
    private static final String URL =getUrl("saveEvents_url");

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(dataProviderClass = SaveEventsDataBuilder.class, dataProvider = "saveEventsSingleData")
    public void positiveCase(SaveEventsPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("PayloadValidation", "test positive flow" ).assignCategory("PayloadValidation");
        Setup.extentTest.set(test);

        validatePayloadForAllFields(payload, URL);
        // Ensure the test does not stop on assertion failures
        Setup.extentTest.get().log(Status.INFO, "Validation complete, check logs for details.");
        }

}
