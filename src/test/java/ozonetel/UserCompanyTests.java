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

import static endpoints.ozonetel.UserCompany.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class UserCompanyTests {
    private String agentIdValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        agentIdValue = getValue("ozonetel", "agentId");
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("UserCompany_PositiveCase", "test positive flow" );
        Setup.extentTest.set(test);

         response = userCompanyPositiveCase("agentId"+":"+agentIdValue);

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertKeyValue(response, "status", "200");
        assertResponseTime(response);

    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("UserCompany_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
         response = userCompanyQueryParamCase("agentId"+":"+agentIdValue);

        validateJsonSchema(response, "schema/Ozonetel/userCompanySchema.json");

    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("UserCompany_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
         response = userCompanyHeaderCase(agentIdValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);

    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "queryDataProvider")
    public void emptyAndWrongParamCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("UserCompany_EmptyAndWrongParam", "query param case : empty param | wrong param");
        Setup.extentTest.set(test);
         response = userCompanyQueryParamCase(key+":"+value);

        assertStatusCode(response, 404);
        assertKeyValue(response, "message", "User not found!");
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("UserCompany_CorrectAuth", "authorization token case : correct token");
        Setup.extentTest.set(test);
         response = userCompanyHeaderCase(agentIdValue,"Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }

    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("UserCompany_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
         response = userCompanyWrongEndpointCase("agentId"+":"+agentIdValue);

        assertStatusCode(response, 404);
    }

    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("UserCompany_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
         response = userCompanyMethodCase("agentId"+":"+agentIdValue);

        assertStatusCode(response, 405);

    }
    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }
}
