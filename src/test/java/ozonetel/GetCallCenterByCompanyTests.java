package ozonetel;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.ozonetel.GetCallCenterByCompany.getCallCenterByCompanyHeaderCase;
import static endpoints.ozonetel.GetCallCenterByCompany.getCallCenterByCompanyPositiveCase;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class GetCallCenterByCompanyTests {
    private int companyIdValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        companyIdValue = Integer.parseInt(getValue("ozonetel", "ozonetelId"));
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetCallCenterByCompany_PositiveCase", "test positive flow" );
        Setup.extentTest.set(test);

        response = getCallCenterByCompanyPositiveCase(companyIdValue);

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertResponseTime(response);

    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetCallCenterByCompany_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = getCallCenterByCompanyPositiveCase(companyIdValue);

        validateJsonSchema(response, "schema/Ozonetel/getOzonetelCampaignsSchema.json");

    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetCallCenterByCompany_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = getCallCenterByCompanyHeaderCase(companyIdValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }

    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }
}
