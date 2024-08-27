package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;
import static utilities.RestAssuredUtils.commonRequestSpecWithToken;

public class MonitoringAreasListByFacId {
    private static Response response;
    private static String accessToken = getToken();

    public static Response monitoringAreasListByFacIdPositiveCase(int facilityId) {
        String uri = String.format("%s/%s/%s", "/facility", facilityId, "areas");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/facility"+"/{facilityId}/areas", facilityId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response monitoringAreasListByFacIdWrongEndpointCase(int facilityId) {
        String uri = String.format("%s/%s/%s", "/facility", facilityId, "areaswrong");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/facility"+"/{facilityId}/areaswrong", facilityId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response monitoringAreasListByFacIdMethodCase(int facilityId) {
        String uri = String.format("%s/%s/%s", "/facility", facilityId, "areas");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post("/v1/user/facility"+"/{facilityId}/areas", facilityId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response monitoringAreasListByFacIdHeaderCase(int facilityId, String... headers) {
        String uri = String.format("%s/%s/%s", "/facility", facilityId, "areas");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get("/v1/user/facility"+"/{facilityId}/areas", facilityId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
