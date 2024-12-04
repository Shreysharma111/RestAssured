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

import static endpoints.winchcamp.GetZonesByRegion.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class GetZonesByRegionTests {
    private String regionIdValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        regionIdValue = getValue("winchcamp", "regionId");
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetZonesByRegion_PositiveCase", "test positive flow" ).assignCategory("GetZonesByRegion");
        Setup.extentTest.set(test);

        response = getZonesByRegionPositiveCase(regionIdValue);

        assertStatusCode(response, 200);
        assertFieldIsPresentAndNotEmpty(response, "data");
        assertKeyValue(response, "data[0].zone.id", regionIdValue);
        assertResponseTime(response);

    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetZonesByRegion_SchemaValidation", "schema validation case").assignCategory("GetZonesByRegion");
        Setup.extentTest.set(test);
        response = getZonesByRegionPositiveCase(regionIdValue);

        validateJsonSchema(response, "schema/Winchcamp/getZonesByRegion.json");
    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetZonesByRegion_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("GetZonesByRegion");
        Setup.extentTest.set(test);
        response = getZonesByRegionHeaderCase(regionIdValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("GetZonesByRegion_CorrectAuth", "authorization token case : correct token").assignCategory("GetZonesByRegion");
        Setup.extentTest.set(test);
        response = getZonesByRegionHeaderCase(regionIdValue,"Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetZonesByRegion_WrongEndpoint", "wrong endpoint case").assignCategory("GetZonesByRegion");
        Setup.extentTest.set(test);
        response = getZonesByRegionWrongEndpointCase(regionIdValue);

        assertStatusCode(response, 404);

    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetZonesByRegion_WrongMethod", "wrong request method case").assignCategory("GetZonesByRegion");
        Setup.extentTest.set(test);
        response = getZonesByRegionMethodCase(regionIdValue);

        assertStatusCode(response, 405);

    }
    @Test
    public void wrongRegionIdCase() {
        ExtentTest test = Setup.extentReports.createTest("GetZonesByRegion_WrongGuid", "wrong guid as path param case").assignCategory("GetZonesByRegion");
        Setup.extentTest.set(test);
        response = getZonesByRegionWrongRegionIdCase(regionIdValue);

        assertStatusCode(response, 200);
        assertArrayIsPresentAndEmpty(response, "data");
    }

    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
