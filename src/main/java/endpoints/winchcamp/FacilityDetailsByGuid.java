package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class FacilityDetailsByGuid {
    private static Response response;
    private static String accessToken = getToken();
    public static String facilityDetailsByGuid_url = getUrl().getString("facilityDetailsByGuid_url");


    public static Response facilityDetailsByGuidPositiveCase(String guid) {
        String uri = String.format("%s/%s", "/facility/camera-config", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))
                .pathParam("guid", guid)// Set access token as Bearer token
                .when()
                .get(facilityDetailsByGuid_url);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response facilityDetailsByGuidWrongEndpointCase(String guid) {
        String uri = String.format("%s/%s", "/facility/camera-configgg", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/facility/camera-configgg"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response facilityDetailsByGuidWrongGuidCase(String guid) {
        String uri = String.format("%s/%s", "/facility/camera-config", guid.concat("1"));

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))
                .pathParam("guid",guid.concat("1"))// Set access token as Bearer token
                .when()
                .get(facilityDetailsByGuid_url);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response facilityDetailsByGuidMethodCase(String guid) {
        String uri = String.format("%s/%s", "/facility/camera-config", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))
                .pathParam("guid", guid)// Set access token as Bearer token
                .when()
                .post(facilityDetailsByGuid_url);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response facilityDetailsByGuidHeaderCase(String guid, String... headers) {
        String uri = String.format("%s/%s", "/facility/camera-config", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))
                .pathParam("guid", guid)// Don't set access token as Bearer token
                .when()
                .get(facilityDetailsByGuid_url);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
