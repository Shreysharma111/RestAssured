package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;
import static utilities.RestAssuredUtils.commonRequestSpecWithToken;

public class CameraDetailsByGuid {
    private static Response response;
    private static String accessToken = getToken();

    public static Response cameraDetailsByGuidPositiveCase(String guid) {
        String uri = String.format("%s/%s", "/facility/camera/guid", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/facility/camera/guid/"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response cameraDetailsByGuidWrongEndpointCase(String guid) {
        String uri = String.format("%s/%s", "/facility/camera/guiddd", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/facility/camera/guiddd"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response cameraDetailsByGuidWrongGuidCase(String guid) {
        String uri = String.format("%s/%s/%s", "/facility/camera/guid", guid,"shr");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/facility/camera/guid"+"/{guid}shr", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response cameraDetailsByGuidMethodCase(String guid) {
        String uri = String.format("%s/%s", "/facility/camera/guid", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post("/facility/camera/guid"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response cameraDetailsByGuidHeaderCase(String guid, String... headers) {
        String uri = String.format("%s/%s", "/facility/camera/guid", guid);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get("/facility/camera/guid"+"/{guid}", guid);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
