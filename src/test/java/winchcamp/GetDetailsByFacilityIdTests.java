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

import static endpoints.winchcamp.GetDetailsByFacilityId.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class GetDetailsByFacilityIdTests {
    private static int facilityIdValue;
    public static String INVALID_FACILITY_ID_MSG;
    private static Response response;
    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        facilityIdValue = Integer.parseInt(getValue("winchcamp", "facilityId"));
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetDetailsByFacilityId_PositiveCase", "test positive flow" ).assignCategory("GetDetailsByFacilityId");
        Setup.extentTest.set(test);

        response = getDetailsByFacilityIdPositiveCase(facilityIdValue);
        assertStatusCode(response, 200);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetDetailsByFacilityId_SchemaValidation", "schema validation case").assignCategory("GetDetailsByFacilityId");
        Setup.extentTest.set(test);
        response = getDetailsByFacilityIdPositiveCase(facilityIdValue);

        validateJsonSchema(response, "schema/Winchcamp/getDetailsByFacilityId.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetDetailsByFacilityId_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("GetDetailsByFacilityId");
        Setup.extentTest.set(test);
        response = getDetailsByFacilityIdHeaderCase(facilityIdValue,key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetDetailsByFacilityId_WrongEndpoint", "wrong endpoint case").assignCategory("GetDetailsByFacilityId");
        Setup.extentTest.set(test);
        response = getDetailsByFacilityIdWrongEndpointCase(facilityIdValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetDetailsByFacilityId_WrongMethod", "wrong request method case").assignCategory("GetDetailsByFacilityId");
        Setup.extentTest.set(test);
        response = getDetailsByFacilityIdMethodCase(facilityIdValue);

        assertStatusCode(response, 405);
    }
    //used this dataprovider as we need invalid int values like 0 and 99999. In this case, this dataprovider best offers the usecase
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "pathDataProviderForDeleteCameraConfigByConfigId")
    public void invalidFacilityIdCase(int facilityIdValue) {
        ExtentTest test = Setup.extentReports.createTest("GetDetailsByFacilityId_InvalidFacilityId", "invalid facility id as path param case").assignCategory("GetDetailsByFacilityId");
        Setup.extentTest.set(test);
        response = getDetailsByFacilityIdPositiveCase(facilityIdValue);

        assertStatusCode(response, 404);
        INVALID_FACILITY_ID_MSG = "No facility found in database for facilityId("+facilityIdValue+")!";

        assertKeyValue(response, "message", INVALID_FACILITY_ID_MSG);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
