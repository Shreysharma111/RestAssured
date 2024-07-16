package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.SaveCameraConfigsPojo;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class SaveCameraConfigs {
    private static Response response;
    private static String accessToken = getToken();
    public static String saveCameraConfigs_url = getUrl().getString("saveCameraConfigs_url");

    public static Response saveCameraConfigsPositiveCase(SaveCameraConfigsPojo payload) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .post(saveCameraConfigs_url);

        //log details and verify status code in extent report
        printRequestLogInReport(saveCameraConfigs_url, "POST", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response saveCameraConfigsWrongEndpointCase(SaveCameraConfigsPojo payload) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .post(saveCameraConfigs_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(saveCameraConfigs_url+"shr", "POST", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response saveCameraConfigsMethodCase(SaveCameraConfigsPojo payload) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .get(saveCameraConfigs_url);

        //log details and verify status code in extent report
        printRequestLogInReport(saveCameraConfigs_url, "GET", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response saveCameraConfigsHeaderCase(SaveCameraConfigsPojo payload, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(payload, headers))// Don't set access token as Bearer token
                .when()
                .post(saveCameraConfigs_url);

        //log details and verify status code in extent report
        printRequestLogInReport(saveCameraConfigs_url, "POST", commonRequestSpecPost(payload, headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
