package ozonetel;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.ozonetel.GetOzonetelCampaigns.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.assertFieldIsPresentAndNotEmpty;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class GetOzonetelCampaignsTests {
    private int ozonetelAccId;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        ozonetelAccId = Integer.parseInt(getValue("ozonetel", "ozonetelId"));
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelCampaignsTests", "test positive flow" );
        Setup.extentTest.set(test);

        Response response = getOzonetelCampaignsPositiveCase(ozonetelAccId);

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");

    }

    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelCampaignsTests", "schema validation case");
        Setup.extentTest.set(test);
        Response response = getOzonetelCampaignsPositiveCase(ozonetelAccId);

        validateJsonSchema(response, "schema/Ozonetel/getOzonetelCampaignsSchema.json");

    }


    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelCampaignsTests", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        Response response = getOzonetelCampaignsHeaderCase(ozonetelAccId, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }

    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelCampaignsTests", "authorization token case : correct token");
        Setup.extentTest.set(test);
        Response response = getOzonetelCampaignsHeaderCase(ozonetelAccId,"Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);

    }

    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelCampaignsTests", "wrong endpoint case");
        Setup.extentTest.set(test);
        Response response = getOzonetelCampaignsWrongEndpointCase(ozonetelAccId);

        assertStatusCode(response, 404);

    }

    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetOzonetelCampaignsTests", "wrong request method case");
        Setup.extentTest.set(test);
        Response response = getOzonetelCampaignsMethodCase(ozonetelAccId);

        assertStatusCode(response, 405);

    }


}
