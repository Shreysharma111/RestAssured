package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;
import static utilities.RestAssuredUtils.commonRequestSpecWithToken;

public class ManualOverride {
    private static Response response;
    private static String accessToken = getToken();

    public static Response manualOverridePositiveCase(String guid) {
        String uri = String.format("%s/%s", "/users/override/camera", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/users/override/camera"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response manualOverrideWrongEndpointCase(String guid) {
        String uri = String.format("%s/%s", "/users/override/cameraaa", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/users/override/cameraaa"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response manualOverrideWrongGuidCase(String guid) {
        String uri = String.format("%s/%s/%s", "/users/override/camera", guid,"shr");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/users/override/camera"+"/{guid}shr", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response manualOverrideMethodCase(String guid) {
        String uri = String.format("%s/%s", "/users/override/camera", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post("/v1/user/users/override/camera"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response manualOverrideHeaderCase(String guid, String... headers) {
        String uri = String.format("%s/%s", "/users/override/camera", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get("/v1/user/users/override/camera"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
