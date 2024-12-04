package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.winchcamp.MonitoringAreasListByFacId.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class MonitoringAreasListByFacIdTests {
    private int facilityIdValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        facilityIdValue = Integer.parseInt(getValue("winchcamp", "facilityId"));
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("MonitoringAreasListByFacId_PositiveCase", "test positive flow" ).assignCategory("MonitoringAreasListByFacId");
        Setup.extentTest.set(test);

        response = monitoringAreasListByFacIdPositiveCase(facilityIdValue);

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("MonitoringAreasListByFacId_SchemaValidation", "schema validation case").assignCategory("MonitoringAreasListByFacId");
        Setup.extentTest.set(test);
        response = monitoringAreasListByFacIdPositiveCase(facilityIdValue);

        validateJsonSchema(response, "schema/Winchcamp/monitoringAreasListByFacId.json");

    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("MonitoringAreasListByFacId_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("MonitoringAreasListByFacId");
        Setup.extentTest.set(test);
        response = monitoringAreasListByFacIdHeaderCase(facilityIdValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("MonitoringAreasListByFacId_CorrectAuth", "authorization token case : correct token").assignCategory("MonitoringAreasListByFacId");
        Setup.extentTest.set(test);
        response = monitoringAreasListByFacIdHeaderCase(facilityIdValue,"Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);

    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("MonitoringAreasListByFacId_WrongEndpoint", "wrong endpoint case").assignCategory("MonitoringAreasListByFacId");
        Setup.extentTest.set(test);
        response = monitoringAreasListByFacIdWrongEndpointCase(facilityIdValue);

        assertStatusCode(response, 404);

    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("MonitoringAreasListByFacId_WrongMethod", "wrong request method case").assignCategory("MonitoringAreasListByFacId");
        Setup.extentTest.set(test);
        response = monitoringAreasListByFacIdMethodCase(facilityIdValue);

        assertStatusCode(response, 405);

    }

    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }


}
