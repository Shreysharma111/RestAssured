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

import java.util.HashMap;
import java.util.Map;

import static endpoints.winchcamp.GetFacilitiesByZoneAndRegion.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class GetFacilitiesByZoneAndRegionTests {
    private int regionIdValue;
    private int zoneIdValue;
    private static Response response;

    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        regionIdValue = Integer.parseInt(getValue("winchcamp", "regionIdGetFacilitiesByZoneAndRegion"));
        zoneIdValue = Integer.parseInt(getValue("winchcamp", "zoneIdGetFacilitiesByZoneAndRegion"));
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByZoneAndRegion_PositiveCase", "test positive flow" ).assignCategory("GetFacilitiesByZoneAndRegion");
        Setup.extentTest.set(test);

        response = getFacilitiesByZoneAndRegionPositiveCase(regionIdValue, zoneIdValue);

        Map<String, Object> expectedFieldValues = new HashMap<>();
        expectedFieldValues.put("regionId", regionIdValue);
        expectedFieldValues.put("zoneId", zoneIdValue);

        assertStatusCode(response, 200);
        assertFieldsWithExpectedValues(response, expectedFieldValues);
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByZoneAndRegion_SchemaValidation", "schema validation case").assignCategory("GetFacilitiesByZoneAndRegion");
        Setup.extentTest.set(test);
        response = getFacilitiesByZoneAndRegionPositiveCase(regionIdValue, zoneIdValue);

        validateJsonSchema(response, "schema/Winchcamp/getFacilitiesByZoneAndRegion.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByZoneAndRegion_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("GetFacilitiesByZoneAndRegion");
        Setup.extentTest.set(test);
        response = getFacilitiesByZoneAndRegionHeaderCase(regionIdValue, zoneIdValue,key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByZoneAndRegion_WrongEndpoint", "wrong endpoint case").assignCategory("GetFacilitiesByZoneAndRegion");
        Setup.extentTest.set(test);
        response = getFacilitiesByZoneAndRegionWrongEndpointCase(regionIdValue, zoneIdValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByZoneAndRegion_WrongMethod", "wrong request method case").assignCategory("GetFacilitiesByZoneAndRegion");
        Setup.extentTest.set(test);
        response = getFacilitiesByZoneAndRegionMethodCase(regionIdValue, zoneIdValue);

        assertStatusCode(response, 405);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "pathDataProviderForGetFacilitiesByZoneAndRegion")
    public void mismatchRegionIdZoneIdCase(int regionIdValue, int zoneIdValue) {
        ExtentTest test = Setup.extentReports.createTest("GetFacilitiesByZoneAndRegion_WrongParams", "wrong region Id and zone Id as path param case").assignCategory("GetFacilitiesByZoneAndRegion");
        Setup.extentTest.set(test);
        response = getFacilitiesByZoneAndRegionPositiveCase(regionIdValue, zoneIdValue);

        assertStatusCode(response, 200);
        assertArrayIsPresentAndEmpty(response, "data");
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
