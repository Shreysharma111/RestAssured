package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class GetAllTenantCameraConfig {
    private static Response response;
    private static String accessToken = getToken();
    public static String getAllTenantCameraConfig_url = getUrl().getString("getAllTenantCameraConfig_url");

    public static Response getAllTenantCameraConfigPositiveCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(getAllTenantCameraConfig_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getAllTenantCameraConfig_url, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getAllTenantCameraConfigWrongEndpointCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(getAllTenantCameraConfig_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(getAllTenantCameraConfig_url+"shr", "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getAllTenantCameraConfigMethodCase() {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post(getAllTenantCameraConfig_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getAllTenantCameraConfig_url, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response getAllTenantCameraConfigHeaderCase(String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get(getAllTenantCameraConfig_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getAllTenantCameraConfig_url, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
