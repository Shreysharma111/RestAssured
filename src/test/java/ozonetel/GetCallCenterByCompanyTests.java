package ozonetel;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.ozonetel.GetCallCenterByCompany.getCallCenterByCompanyPositiveCase;
import static utilities.RestAssuredUtils.getValue;
import static utilities.restAssuredFunctions.API.*;

public class GetCallCenterByCompanyTests {
    private int companyIdValue;

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

        Response response = getCallCenterByCompanyPositiveCase(companyIdValue);

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertResponseTime(response);

    }
}
