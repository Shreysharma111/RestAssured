package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class GetFacilitiesByZoneAndRegion {

    private static Response response;
    public  static String getFacilitiesByZoneAndRegion_url = getUrl().getString("getFacilitiesByZoneAndRegion_url");

    public static Response getFacilitiesByZoneAndRegionPositiveCase(int... pathParams) {
        return positiveCase(HttpMethod.GET, getFacilitiesByZoneAndRegion_url, pathParams);
    }
    public static Response getFacilitiesByZoneAndRegionWrongEndpointCase(int... pathParams) {
        return positiveCase(HttpMethod.GET, getFacilitiesByZoneAndRegion_url+"shr", pathParams);
    }
    public static Response getFacilitiesByZoneAndRegionMethodCase(int... pathParams) {
        return positiveCase(HttpMethod.POST, getFacilitiesByZoneAndRegion_url, pathParams);
    }
    public static Response getFacilitiesByZoneAndRegionHeaderCase(int regionId, int zoneId, String... headers) {
        String uri = String.format("%s/%d/%d", "/v1/user/facility/facilities", regionId, zoneId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get("/v1/user/facility/facilities/{regionId}/{zoneId}", regionId, zoneId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

}
