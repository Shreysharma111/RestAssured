package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;
import static utilities.RestAssuredUtils.commonRequestSpecWithToken;

public class GetCameraConfigsByConfigId {
    private static Response response;
    private static String accessToken = getToken();

    public static Response getCameraConfigsByConfigIdPositiveCase(int configId) {
        String uri = String.format("%s/%s", "/facility/details/camera/config", configId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/facility/details/camera/config"+"/{configId}", configId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getCameraConfigsByConfigIdWrongEndpointCase(int configId) {
        String uri = String.format("%s/%s", "/facility/details/camera/configwrong", configId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/facility/details/camera/configwrong"+"/{configId}", configId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getCameraConfigsByConfigIdMethodCase(int configId) {
        String uri = String.format("%s/%s", "/facility/details/camera/config", configId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post("/v1/user/facility/details/camera/config"+"/{configId}", configId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response getCameraConfigsByConfigIdHeaderCase(int configId, String... headers) {
        String uri = String.format("%s/%s", "/facility/details/camera/config", configId);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get("/v1/user/facility/details/camera/config"+"/{configId}", configId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getCameraConfigsByConfigIdZeroConfigIdCase() {
        String uri = String.format("%s/%s", "/facility/details/camera/config", 0);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/facility/details/camera/config"+"/{configId}", 0);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

}
