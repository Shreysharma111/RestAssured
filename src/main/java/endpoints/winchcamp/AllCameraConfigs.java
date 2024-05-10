package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class AllCameraConfigs {
    private static Response response;
    private static String accessToken = getToken();
    public static String allCameraConfigs_url = getUrl().getString("allCameraConfigs_url");

    public static Response allCameraConfigsPositiveCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(allCameraConfigs_url);

        //log details and verify status code in extent report
        printRequestLogInReport(allCameraConfigs_url, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response allCameraConfigsWrongEndpointCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(allCameraConfigs_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(allCameraConfigs_url, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response allCameraConfigsMethodCase() {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post(allCameraConfigs_url);

        //log details and verify status code in extent report
        printRequestLogInReport(allCameraConfigs_url, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response allCameraConfigsHeaderCase(String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get(allCameraConfigs_url);

        //log details and verify status code in extent report
        printRequestLogInReport(allCameraConfigs_url, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }


}
