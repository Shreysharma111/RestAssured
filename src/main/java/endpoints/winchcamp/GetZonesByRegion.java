package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;
import static utilities.RestAssuredUtils.commonRequestSpecWithToken;

public class GetZonesByRegion {
    private static Response response;
    private static String accessToken = getToken();

    public static Response getZonesByRegionPositiveCase(String regionId) {
        String uri = String.format("%s/%s", "/facility/zones", regionId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/facility/zones"+"/{regionId}", regionId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getZonesByRegionWrongEndpointCase(String regionId) {
        String uri = String.format("%s/%s", "/facility/zonesss", regionId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/facility/zonesss"+"/{regionId}", regionId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getZonesByRegionWrongRegionIdCase(String regionId) {
        String uri = String.format("%s/%s%s", "/facility/zones", regionId,"99");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/facility/zones"+"/{regionId}99", regionId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getZonesByRegionMethodCase(String regionId) {
        String uri = String.format("%s/%s", "/facility/zones", regionId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post("/facility/zones"+"/{regionId}", regionId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response getZonesByRegionHeaderCase(String regionId, String... headers) {
        String uri = String.format("%s/%s", "/facility/zones", regionId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get("/facility/zones"+"/{regionId}", regionId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }


}
